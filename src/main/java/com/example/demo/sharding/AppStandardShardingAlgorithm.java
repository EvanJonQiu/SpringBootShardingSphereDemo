package com.example.demo.sharding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Properties;

import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import com.google.common.collect.Range;

public class AppStandardShardingAlgorithm implements StandardShardingAlgorithm<Date> {

	@Override
	public Properties getProps() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(Properties props) {
		// TODO Auto-generated method stub
		
	}

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

	@Override
	public Collection<String> doSharding(Collection<String> availableTargetNames,
			RangeShardingValue<Date> shardingValue) {
		Range<Date> ranges = shardingValue.getValueRange();
		
		Calendar start = Calendar.getInstance();
		start.setTime(ranges.lowerEndpoint());
		
		Calendar end = Calendar.getInstance();
		end.setTime(ranges.upperEndpoint());
		
		int startYear = start.get(Calendar.YEAR);
		int endYear = end.get(Calendar.YEAR);
		
		int startMonth = start.get(Calendar.MONTH) + 1;
		int endMonth = end.get(Calendar.MONTH) + 1;
		
		Collection<String> tables = new LinkedHashSet<>();
		if (start.before(end)) {
			for (String c : availableTargetNames) {
				int cMonth = Integer.parseInt(c.substring(c.length() - 6));
				if (cMonth >= Integer.parseInt("" + startYear + startMonth) && cMonth <= Integer.parseInt("" + endYear + endMonth)) {
					tables.add(c);
				}
			}
		}
		
		return tables;
	}

}
