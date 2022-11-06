package com.example.demo.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderRepository orderRepository;

	@Override
	public Collection<Order> getAll() {
		return this.orderRepository.findAll();
	}

	@Override
	public void addOrder(int orderId, String msg) {
		Order order = new Order();
		order.setOrderMessage(msg);
		order.setOrderId(orderId);
		this.orderRepository.save(order);
	}

}
