package com.example.demo.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.TestData;

public interface TestDataRepository extends JpaRepository<TestData, Integer> {
	
	@Modifying 
	@Query(value = "INSERT INTO test_data(name, data, date_time) VALUES(?, cast(? as JSONB), ?)", nativeQuery = true)
	public void addData(String name, String data, Date dateTime);
}
