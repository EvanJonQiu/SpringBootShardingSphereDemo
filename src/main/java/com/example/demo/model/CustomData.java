package com.example.demo.model;

import java.io.Serializable;

public class CustomData implements Serializable {
	
	private static final long serialVersionUID = 2415918155184143055L;
	
	private String name;
	
	private double gdop;
	
	private double pdop;
	
	public CustomData() {}
	
	public CustomData(String name, double gdop, double pdop) {
		this.name = name;
		this.gdop = gdop;
		this.pdop = pdop;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public double getGdop() {
		return gdop;
	}
	
	public void setGdop(double gdop) {
		this.gdop = gdop;
	}
	
	public double getPdop() {
		return pdop;
	}
	
	public void setPdop(double pdop) {
		this.pdop = pdop;
	}
}
