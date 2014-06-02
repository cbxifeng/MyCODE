package com.duihao.promotion.javavbean.android;
public class NewsDetailInfo {

	public NewsDetailInfo() {
		
	}
	Integer id;//内容id 
	String title;//标题
	String content;//简介
	String thumb;//缩略图路径
	String url;
	String price; //价格
	String pics;//多张图片的路径
	String unit;//单位
	String sellcount;//数量
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getThumb() {
		return thumb;
	}
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPics() {
		return pics;
	}
	public void setPics(String pics) {
		this.pics = pics;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getSellcount() {
		return sellcount;
	}
	public void setSellcount(String sellcount) {
		this.sellcount = sellcount;
	}
	
	public NewsDetailInfo(Integer id, String title, String content,
			String thumb, String url, String price, String pics, String unit,
			String sellcount) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.thumb = thumb;
		this.url = url;
		this.price = price;
		this.pics = pics;
		this.unit = unit;
		this.sellcount = sellcount;
	}	

}
