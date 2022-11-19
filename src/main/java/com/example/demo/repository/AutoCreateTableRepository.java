package com.example.demo.repository;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.AutoCreateTable;

public interface AutoCreateTableRepository extends JpaRepository<AutoCreateTable, Integer> {
	
	@Query("SELECT a FROM AutoCreateTable a WHERE a.createTime BETWEEN ?1 and ?2")
	public Collection<AutoCreateTable> getDataByDate(Date start, Date end);
}
