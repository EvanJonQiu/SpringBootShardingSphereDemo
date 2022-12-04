package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson2.JSON;
import com.example.demo.model.CustomData;
import com.example.demo.model.TestData;
import com.example.demo.repository.TestDataRepository;
import com.example.demo.service.TestDataService;

@Service
public class TestDataServiceImpl implements TestDataService {
	
	private static final Logger logger = LoggerFactory.getLogger(TestDataServiceImpl.class);
	
	@Autowired
	private TestDataRepository testDataRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void addTest() {
		
		TestData testData = new TestData();
		testData.setName("hello");
		testData.setDateTime(new Date());
		
		List<CustomData> list = new ArrayList<CustomData>();
		CustomData data = new CustomData("c1", 1.0, 0.1);
		list.add(data);
		list.add(data);
		
		testData.setData(list);
		
		logger.info(JSON.toJSONString(list));
		
		this.testDataRepository.addData(testData.getName(), JSON.toJSONString(list), new Date());
	}

	@Override
	public Collection<TestData> getList() {
		
		TestData testData = new TestData();
		testData.setName("hello");
		testData.setDateTime(new Date());
		
		List<CustomData> list = new ArrayList<CustomData>();
		CustomData data = new CustomData("c1", 1.0, 0.1);
		list.add(data);	
		list.add(data);
		
		testData.setData(list);
		
		String a = JSON.toJSONString(list);
		
		logger.debug(a);
		
		return this.testDataRepository.findAll();
	}

}
