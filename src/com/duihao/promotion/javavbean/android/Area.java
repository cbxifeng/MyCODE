package com.duihao.promotion.javavbean.android;

public class Area {

	int areaid;
	String name;

	public Area(int areaid, String name) {
		super();
		this.areaid = areaid;
		this.name = name;
	}

	public int getAreaid() {
		return areaid;
	}

	public void setAreaid(int areaid) {
		this.areaid = areaid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
