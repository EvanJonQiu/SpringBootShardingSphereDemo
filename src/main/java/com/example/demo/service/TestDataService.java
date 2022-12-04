package com.example.demo.service;

import java.util.Collection;

import com.example.demo.model.TestData;

public interface TestDataService {
	public void addTest();
	public Collection<TestData> getList();
}
