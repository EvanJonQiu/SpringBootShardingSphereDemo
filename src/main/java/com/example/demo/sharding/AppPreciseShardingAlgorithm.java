package com.example.demo.sharding;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Collection;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

public class AppPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Date> {

	@Override
	public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> shardingValue) {
		
		Date createTime = shardingValue.getValue();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		
		String timeValue = sdf.format(createTime);
		
		String columnName = shardingValue.getColumnName();
		String table = shardingValue.getLogicTableName();
		
		 if (timeValue.isEmpty()) {
            throw new UnsupportedOperationException(columnName + ":列，分表精确分片值为NULL;");
        }
        for (String each : availableTargetNames) {
            if (each.startsWith(table)) {
                return table + "_" + timeValue;
            }
        }
        return table;
	}


}

