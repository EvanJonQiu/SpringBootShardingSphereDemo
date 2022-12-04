# Spring boot + ShardingSphere + JPA DEMO


public class InitActualDataNodesExample implements ApplicationRunner {
    private final DataSource dataSource;
```java
    @Override
    public void run(ApplicationArguments args)  {
        initActualDataNodes((ShardingSphereDataSource) dataSource);
    }

    private void initActualDataNodes(ShardingSphereDataSource dataSource) {


        // 更新 context
        ContextManager contextManager = getContextManager(dataSource);
        Collection<RuleConfiguration> addRuleConfigs = new LinkedList<>();
        Collection<RuleConfiguration> configurations = contextManager.getMetaDataContexts().getMetaData().getDatabases()
                .get("logic_db").getRuleMetaData().getConfigurations();

        for (RuleConfiguration configuration : configurations) {
            if(configuration instanceof ShardingRuleConfiguration){
                ShardingRuleConfiguration algorithmProvidedShardingRuleConfiguration = (ShardingRuleConfiguration) configuration;


                ShardingRuleConfiguration addRuleConfiguration = new ShardingRuleConfiguration();
                Collection<ShardingTableRuleConfiguration> addTableConfigurations = new LinkedList<>();
                for (ShardingTableRuleConfiguration shardingTableRuleConfiguration : algorithmProvidedShardingRuleConfiguration.getTables()) {

                    if ("T_EVENT".equals(shardingTableRuleConfiguration.getLogicTable())) {
                        String actualDataNodes2 = "ds$->{0..1}.T_EVENT_$->{20221111..20221121}";
                        ShardingTableRuleConfiguration addTableConfiguration = new ShardingTableRuleConfiguration(shardingTableRuleConfiguration.getLogicTable(), actualDataNodes2);
                        addTableConfiguration.setTableShardingStrategy(shardingTableRuleConfiguration.getTableShardingStrategy());
                        addTableConfiguration.setDatabaseShardingStrategy(shardingTableRuleConfiguration.getDatabaseShardingStrategy());
                        addTableConfiguration.setKeyGenerateStrategy(shardingTableRuleConfiguration.getKeyGenerateStrategy());
                        addTableConfigurations.add(addTableConfiguration);
                    } else {
                        addTableConfigurations.add(shardingTableRuleConfiguration);
                    }
                }

                addRuleConfiguration.setTables(addTableConfigurations);
                addRuleConfiguration.setAutoTables(algorithmProvidedShardingRuleConfiguration.getAutoTables());
                addRuleConfiguration.setBindingTableGroups(algorithmProvidedShardingRuleConfiguration.getBindingTableGroups());
                addRuleConfiguration.setBroadcastTables(algorithmProvidedShardingRuleConfiguration.getBroadcastTables());
                addRuleConfiguration.setDefaultDatabaseShardingStrategy(algorithmProvidedShardingRuleConfiguration.getDefaultDatabaseShardingStrategy());
                addRuleConfiguration.setDefaultTableShardingStrategy(algorithmProvidedShardingRuleConfiguration.getDefaultTableShardingStrategy());
                addRuleConfiguration.setDefaultKeyGenerateStrategy(algorithmProvidedShardingRuleConfiguration.getDefaultKeyGenerateStrategy());
                addRuleConfiguration.setDefaultShardingColumn(algorithmProvidedShardingRuleConfiguration.getDefaultShardingColumn());
                addRuleConfiguration.setShardingAlgorithms(algorithmProvidedShardingRuleConfiguration.getShardingAlgorithms());
                addRuleConfiguration.setKeyGenerators(algorithmProvidedShardingRuleConfiguration.getKeyGenerators());
                addRuleConfigs.add(addRuleConfiguration);
            }else{
                addRuleConfigs.add(configuration);
            }

        }
        contextManager.alterRuleConfiguration("logic_db", addRuleConfigs);
    }

    private ContextManager getContextManager(ShardingSphereDataSource dataSource) {
        try {
            Field contextManagerField = dataSource.getClass().getDeclaredField("contextManager");
            contextManagerField.setAccessible(true);
            return (ContextManager) contextManagerField.get(dataSource);
        }
        catch (Exception e) {
            throw new RuntimeException("系统异常");
        }

    }
}
```

## 问题
1. 使用自定义分表算法，导致主键生成算法失效。
2. 插入PostgreSQL的JSONB字段，参见TestDataServiceImpl.class