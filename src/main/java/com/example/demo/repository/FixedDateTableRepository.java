package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.FixedDateTable;

public interface FixedDateTableRepository extends JpaRepository<FixedDateTable, Integer> {

}
