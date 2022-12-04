package com.example.demo.controller;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.AutoCreateTable;
import com.example.demo.model.FixedDateTable;
import com.example.demo.model.Order;
import com.example.demo.model.TestData;
import com.example.demo.service.OrderService;
import com.example.demo.service.TestDataService;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private TestDataService testDataService;

	@GetMapping("/getAll")
	public Collection<Order> getAll() {
		return orderService.getAll();
	}
	
	@PostMapping("/add")
	public void addOrder(int orderId, String msg) {
		this.orderService.addOrder(orderId, msg);
	}
	
	@GetMapping("/getAllFixedData")
	public Collection<FixedDateTable> getAllFixedData() {
		return orderService.getAllFIxedData();
	}
	
	@PostMapping("/addFixedData")
	public void addFixedData(int orderId, String msg, @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date createTime) {
		this.orderService.addFixedDateTable(orderId, msg, createTime);
	}
	
	@GetMapping("/getAllAutoCreateData")
	public Collection<AutoCreateTable> getAllAutoCreateData() {
		return this.orderService.getAllAutoCreateData();
	}
	
	@PostMapping("/addAutoCreateData")
	public void addAutoCreateData(int orderId, String msg, @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date createTime) {
		this.orderService.addAutoCreateData(orderId, msg, createTime);
	}
	
	@GetMapping("/getAllAutoCreateDataByDate")
	public Collection<AutoCreateTable> getAllAutoCreateDataByDate(@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date start, @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date end) {
		return this.orderService.getAllAutoCreateDataByDate(start, end);
	}
	
	@GetMapping("/addTest")
	public void addTest() {
		this.testDataService.addTest();
	}
	
	@GetMapping("/getList")
	public Collection<TestData> getList() {
		return this.testDataService.getList();
	}
}
