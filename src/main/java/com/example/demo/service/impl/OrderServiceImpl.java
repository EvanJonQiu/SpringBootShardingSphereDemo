package com.example.demo.service.impl;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.AutoCreateTable;
import com.example.demo.model.FixedDateTable;
import com.example.demo.model.Order;
import com.example.demo.repository.AutoCreateTableRepository;
import com.example.demo.repository.FixedDateTableRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private FixedDateTableRepository fixedDateTableRepository;
	
	@Autowired
	private AutoCreateTableRepository autoCreateTableRepository;

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

	@Override
	public Collection<FixedDateTable> getAllFIxedData() {
		return this.fixedDateTableRepository.findAll();
	}

	@Override
	public void addFixedDateTable(int orderId, String msg, Date createTime) {
		FixedDateTable fixedDateTable = new FixedDateTable();
		fixedDateTable.setOrderId(orderId);
		fixedDateTable.setOrderMessage(msg);
		fixedDateTable.setCreateTime(createTime);
		this.fixedDateTableRepository.save(fixedDateTable);
	}

	@Override
	public Collection<AutoCreateTable> getAllAutoCreateData() {
		return this.autoCreateTableRepository.findAll();
	}

	@Override
	public void addAutoCreateData(int orderId, String msg, Date createTime) {
		AutoCreateTable autoCreateTable = new AutoCreateTable();
		autoCreateTable.setOrderId(orderId);
		autoCreateTable.setOrderMessage(msg);
		autoCreateTable.setCreateTime(createTime);
		this.autoCreateTableRepository.save(autoCreateTable);
	}

	@Override
	public Collection<AutoCreateTable> getAllAutoCreateDataByDate(Date start, Date end) {
		return this.autoCreateTableRepository.getDataByDate(start, end);
	}

}
