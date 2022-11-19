package com.example.demo.sharding;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.driver.jdbc.core.datasource.ShardingSphereDataSource;

public interface CreateTimeShardingAlgorithm {
	
	String buildNodesSuffix(LocalDate date);
	
	LocalDate buildNodesAfterDate(LocalDate date);
	
	LocalDate buildNodesBeforeDate(LocalDate date);

	default String buildNodes(String tableName, Integer count) {
		List<String> tableNameList = new ArrayList<>();
		
		LocalDate today = LocalDate.now();
		
		for (int i = 0; i < count; i++) {
			tableNameList.add("ds0." + tableName + "_${'" + buildNodesSuffix(today) + "'}");
			today = buildNodesBeforeDate(today);
		}
		
		return StringUtils.join(tableNameList, ",");
	}
	
	default void createTables(ShardingSphereDataSource dataSource, String tableName, Integer count) {
		
		try {
			Connection connection = dataSource.getConnection();
			Statement statement = connection.createStatement();
			
			LocalDate today = LocalDate.now();
			String oldTableName = "";
			String newTableName = "";
			for (int i = 0; i < count; i++) {
				oldTableName = tableName + "_" + buildNodesSuffix(today);
				today = buildNodesAfterDate(today);
				newTableName = tableName + "_" + buildNodesSuffix(today);
				
				String sql = "CREATE TABLE " + newTableName + " ( "
						+ "    id BIGSERIAL PRIMARY KEY,"
						+ "    order_id INTEGER NULL,"
						+ "    order_message VARCHAR(255) NULL,"
						+ "    create_time TIMESTAMP NULL"
						+ ");";
				statement.execute(sql);

			}
		} catch (SQLException throwables) {
			throw new RuntimeException("建表失败");
		}
	}
}
