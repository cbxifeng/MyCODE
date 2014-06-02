package com.duihao.promotion.javavbean.android;

public class Circle_List {
	int Id;
	String Title;
	String Username;// 发起人
	String Pics;
	int Gentie;// 跟帖数

	/**
	 * @return the id
	 */

	public int getId() {
		return Id;
	}

	/**
	 * @param id
	 * @param title
	 * @param username
	 * @param pics
	 * @param gentie
	 */
	public Circle_List(int id, String title, String username, String pics,
			int gentie) {
		super();
		Id = id;
		Title = title;
		Username = username;
		Pics = pics;
		Gentie = gentie;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		Id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return Title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		Title = title;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return Username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		Username = username;
	}

	/**
	 * @return the pics
	 */
	public String getPics() {
		return Pics;
	}

	/**
	 * @param pics
	 *            the pics to set
	 */
	public void setPics(String pics) {
		Pics = pics;
	}

	/**
	 * @return the gentie
	 */
	public int getGentie() {
		return Gentie;
	}

	/**
	 * @param gentie
	 *            the gentie to set
	 */
	public void setGentie(int gentie) {
		Gentie = gentie;
	}

}
