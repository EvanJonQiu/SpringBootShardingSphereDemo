package com.example.demo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

@Entity
@Table(name = "test_data")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class TestData implements Serializable {

	private static final long serialVersionUID = 836594014000989324L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "data")
	@Type(type = "com.vladmihalcea.hibernate.type.json.JsonBinaryType")
	private List<CustomData> data;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_time", updatable = false)
	@JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Date dateTime;
	
	public TestData() {}
	
	public TestData(String name, List<CustomData> data, Date dateTime) {
		this.name = name;
		this.data = data;
		this.dateTime = dateTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CustomData> getData() {
		return data;
	}

	public void setData(List<CustomData> data) {
		this.data = data;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
}
