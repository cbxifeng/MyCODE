package com.dodola.model;

public class DuitangInfo {

	private int height;
	private String albid = "";//分类id
	private String msg = "";//图片名字
	private String isrc = "";//图片地址

	public int getWidth(){
		return 200;
	}
	public String getAlbid() {
		return albid;
	}

	public void setAlbid(String albid) {
		this.albid = albid;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getIsrc() {
		return isrc;
	}

	public void setIsrc(String isrc) {
		this.isrc = isrc;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
