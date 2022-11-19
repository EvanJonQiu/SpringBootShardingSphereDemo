package com.example.demo.service;

import java.util.Collection;
import java.util.Date;

import com.example.demo.model.AutoCreateTable;
import com.example.demo.model.FixedDateTable;
import com.example.demo.model.Order;

public interface OrderService {
	public Collection<Order> getAll();
	public void addOrder(int orderId, String msg);
	
	public Collection<FixedDateTable> getAllFIxedData();
	public void addFixedDateTable(int orderId, String msg, Date createTime);
	
	public Collection<AutoCreateTable> getAllAutoCreateData();
	public void addAutoCreateData(int orderId, String msg, Date createTime);
	public Collection<AutoCreateTable> getAllAutoCreateDataByDate(Date start, Date end);
}
