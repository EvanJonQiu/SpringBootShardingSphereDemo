package com.example.demo.sharding;

public class AppRangeShardingAlgorithm {}
/*
public class AppRangeShardingAlgorithm implements RangeShardingAlgorithm<Date> {

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
*/
