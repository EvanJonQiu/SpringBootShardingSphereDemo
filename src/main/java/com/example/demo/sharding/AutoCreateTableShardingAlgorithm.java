package com.example.demo.sharding;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Properties;

import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Range;

public class AutoCreateTableShardingAlgorithm implements StandardShardingAlgorithm<Date>, CreateTimeShardingAlgorithm {
	
	private static final Logger logger = LoggerFactory.getLogger(AutoCreateTableShardingAlgorithm.class);
	
	private static final String FORMAT_LINK_DAY = "yyyyMMdd";

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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
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
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		String strStart = sdf.format(start.getTime());
		String strEnd = sdf.format(end.getTime());
		
		Collection<String> tables = new LinkedHashSet<>();
		if (start.before(end)) {
			for (String c : availableTargetNames) {
				int cMonth = Integer.parseInt(c.substring(c.length() - 8));
				if (cMonth >= Integer.parseInt(strStart) && cMonth <= Integer.parseInt(strEnd)) {
					tables.add(c);
				}
			}
		}
		
		return tables;
	}

	@Override
	public String buildNodesSuffix(LocalDate date) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(FORMAT_LINK_DAY);
		return date.format(dateFormatter);
	}

	@Override
	public LocalDate buildNodesAfterDate(LocalDate date) {
		return date.plusDays(1);
	}

	@Override
	public LocalDate buildNodesBeforeDate(LocalDate date) {
		return date.minusDays(1);
	}

}
