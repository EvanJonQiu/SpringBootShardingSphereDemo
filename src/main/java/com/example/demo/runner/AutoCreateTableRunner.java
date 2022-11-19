package com.example.demo.runner;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.shardingsphere.driver.jdbc.core.datasource.ShardingSphereDataSource;
import org.apache.shardingsphere.infra.config.algorithm.AlgorithmConfiguration;
import org.apache.shardingsphere.infra.config.rule.RuleConfiguration;
import org.apache.shardingsphere.infra.metadata.ShardingSphereMetaData;
import org.apache.shardingsphere.infra.metadata.database.ShardingSphereDatabase;
import org.apache.shardingsphere.mode.manager.ContextManager;
import org.apache.shardingsphere.mode.metadata.MetaDataContexts;
import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.rule.ShardingTableRuleConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.demo.sharding.AutoCreateTableShardingAlgorithm;

@Component
public class AutoCreateTableRunner implements ApplicationRunner {
	
	private static final Logger logger = LoggerFactory.getLogger(AutoCreateTableShardingAlgorithm.class);
	
	private static final String DATABASE_NAME = "logic_db";
	
	private final String logic_table_name = "t_auto_create_table";
	
	private final String algorithm_name = "auto_create_table_algorithm";
	
	@Resource
	private ShardingSphereDataSource shardingSphereDataSource;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		logger.debug("in " + this.getClass().getName() + "::run()");
		
		// get context manager from datasource
		Field contextManagerField = this.shardingSphereDataSource.getClass().getDeclaredField("contextManager");
		contextManagerField.setAccessible(true);
		ContextManager contextManager = (ContextManager)contextManagerField.get(shardingSphereDataSource);
		
		Collection<RuleConfiguration> newRuleConfigurations = new LinkedList<>();
		
		Collection<RuleConfiguration> oldRuleConfigurations = contextManager.getMetaDataContexts().getMetaData().getDatabases().get("logic_db").getRuleMetaData().getConfigurations();
		
		for (RuleConfiguration config: oldRuleConfigurations) {
			if (config instanceof ShardingRuleConfiguration) {
				ShardingRuleConfiguration newShardingRuleConfig = new ShardingRuleConfiguration();
				
				ShardingRuleConfiguration oldShardingRuleConfig = (ShardingRuleConfiguration)config;
				
				// 根据分片算法名称获取分片算法配置
				Map<String, AlgorithmConfiguration> oldAlgorithmConfigs = oldShardingRuleConfig.getShardingAlgorithms();
				AlgorithmConfiguration algorithmConfiguration = oldAlgorithmConfigs.get(algorithm_name);
				
				Collection<ShardingTableRuleConfiguration> oldShardingTableRuleConfigs = oldShardingRuleConfig.getTables();
				Collection<ShardingTableRuleConfiguration> newShardingTableRuleConfigs = new LinkedList<>();
				
				oldShardingTableRuleConfigs.forEach(shardingTableRuleConfigItem -> {
					if (logic_table_name.equals(shardingTableRuleConfigItem.getLogicTable())) {
						
						String algorithmClassName = algorithmConfiguration.getProps().getProperty("algorithmClassName");
						String actualDataNodes = "";
						
						try {
							Class<?> aClass = Class.forName(algorithmClassName);
							Object o = aClass.getDeclaredConstructor().newInstance();
							
							Method buildNodes = aClass.getMethod("buildNodes", String.class, Integer.class);
							
							actualDataNodes = (String) buildNodes.invoke(o, logic_table_name, 5);
							logger.debug("actual data node: {}", actualDataNodes);
							
							Method createTables = aClass.getMethod("createTables", ShardingSphereDataSource.class, String.class,
									Integer.class);
							
							createTables.invoke(o, shardingSphereDataSource, logic_table_name, 5);
							
							ShardingTableRuleConfiguration newShardingTableRuleConfiguration = new ShardingTableRuleConfiguration(logic_table_name, actualDataNodes);
							newShardingTableRuleConfiguration.setAuditStrategy(shardingTableRuleConfigItem.getAuditStrategy());
							newShardingTableRuleConfiguration.setTableShardingStrategy(shardingTableRuleConfigItem.getTableShardingStrategy());
							newShardingTableRuleConfiguration.setDatabaseShardingStrategy(shardingTableRuleConfigItem.getDatabaseShardingStrategy());
							newShardingTableRuleConfiguration.setKeyGenerateStrategy(shardingTableRuleConfigItem.getKeyGenerateStrategy());
							
							newShardingTableRuleConfigs.add(newShardingTableRuleConfiguration);
						} catch (Exception e) {
							throw new RuntimeException();
						}
					} else {
						newShardingTableRuleConfigs.add(shardingTableRuleConfigItem);
					}
				});
				newShardingRuleConfig.setTables(newShardingTableRuleConfigs);
				newShardingRuleConfig.setAuditors(oldShardingRuleConfig.getAuditors());
				newShardingRuleConfig.setAutoTables(oldShardingRuleConfig.getAutoTables());
				newShardingRuleConfig.setBindingTableGroups(oldShardingRuleConfig.getBindingTableGroups());
				newShardingRuleConfig.setBroadcastTables(oldShardingRuleConfig.getBroadcastTables());
				newShardingRuleConfig.setKeyGenerators(oldShardingRuleConfig.getKeyGenerators());
				newShardingRuleConfig.setShardingAlgorithms(oldAlgorithmConfigs);
				
				newShardingRuleConfig.setDefaultAuditStrategy(oldShardingRuleConfig.getDefaultAuditStrategy());
				newShardingRuleConfig.setDefaultDatabaseShardingStrategy(oldShardingRuleConfig.getDefaultDatabaseShardingStrategy());
				newShardingRuleConfig.setDefaultKeyGenerateStrategy(oldShardingRuleConfig.getDefaultKeyGenerateStrategy());
				newShardingRuleConfig.setDefaultShardingColumn(oldShardingRuleConfig.getDefaultShardingColumn());
				newShardingRuleConfig.setDefaultTableShardingStrategy(oldShardingRuleConfig.getDefaultTableShardingStrategy());
				
				newRuleConfigurations.add(newShardingRuleConfig);
			} else {
				newRuleConfigurations.add(config);
			}
		}
		
		contextManager.alterRuleConfiguration(DATABASE_NAME, newRuleConfigurations);
	}

}
