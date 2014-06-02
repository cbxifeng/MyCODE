package com.duihao.promotion.javavbean.android;

public class PicTitle {

	String name;// 标题
	String description;// 内容
	Integer mytypeid;// 分类id
	
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

	public Integer getMytypeid() {
		return mytypeid;
	}

	public void setMytypeid(Integer mytypeid) {
		this.mytypeid = mytypeid;
	}

	
	public PicTitle() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PicTitle(String name,String description,
			Integer mytypeid) {
		super();
		this.name = name;
		this.description = description;
		this.mytypeid = mytypeid;
	}

	
}
