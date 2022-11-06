package com.example.demo.service;

import java.util.Collection;
import java.util.Date;

import com.example.demo.model.FixedDateTable;
import com.example.demo.model.Order;

public interface OrderService {
	public Collection<Order> getAll();
	public void addOrder(int orderId, String msg);
	
	public Collection<FixedDateTable> getAllFIxedData();
	public void addFixedDateTable(int orderId, String msg, Date createTime);
}
