package com.example.demo.service;

import java.util.Collection;

import com.example.demo.model.Order;

public interface OrderService {
	public Collection<Order> getAll();
	public void addOrder(int orderId, String msg);
}
