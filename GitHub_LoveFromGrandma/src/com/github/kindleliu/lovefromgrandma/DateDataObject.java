package com.github.kindleliu.lovefromgrandma;

import java.util.Date;

public class DateDataObject {

	private Date date;
	private String name;
	
	public DateDataObject(String name, Date date) {
		this.name = name;
		this.date = date;
	}
	
	public Date getDate() {
		return date;
	}
	
	public String getName() {
		return name;
	}
}
