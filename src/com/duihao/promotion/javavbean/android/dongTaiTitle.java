package com.duihao.promotion.javavbean.android;

public class dongTaiTitle {
	String name;// 标题
	String description;// 内容
	int mytypeid;// 分类id

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getMytypeid() {
		return mytypeid;
	}

	public void setMytypeid(int mytypeid) {
		this.mytypeid = mytypeid;
	}

	public dongTaiTitle() {
		super();
		// TODO Auto-generated constructor stub
	}

	public dongTaiTitle(String name, String description, Integer mytypeid) {
		super();
		this.name = name;
		this.description = description;
		this.mytypeid = mytypeid;
	}

}
