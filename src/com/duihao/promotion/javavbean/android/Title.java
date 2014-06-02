package com.duihao.promotion.javavbean.android;

//对象序列化
public class Title {

	public Title() {
		// TODO Auto-generated constructor stub
	}

	int mytypeid;
	String name;
	String model;

	/**
	 * @return the mytypeid
	 */
	public int getMytypeid() {
		return mytypeid;
	}

	/**
	 * @param mytypeid
	 *            the mytypeid to set
	 */
	public void setMytypeid(int mytypeid) {
		this.mytypeid = mytypeid;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @param mytypeid
	 * @param name
	 * @param model
	 */
	public Title(int mytypeid, String name, String model) {
		super();
		this.mytypeid = mytypeid;
		this.name = name;
		this.model = model;
	}

}
