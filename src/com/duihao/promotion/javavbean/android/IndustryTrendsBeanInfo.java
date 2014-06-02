package com.duihao.promotion.javavbean.android;

//产品动态详细

public class IndustryTrendsBeanInfo {
	
	Integer id;//企业动态id 
	String	title;//标题
	String	thumb;//缩略图
	String	Content;//内容
	String url;
	
	
	public IndustryTrendsBeanInfo(Integer id, String title, String thumb,
			String content, String url) {
		super();
		this.id = id;
		this.title = title;
		this.thumb = thumb;
		Content = content;
		this.url = url;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public IndustryTrendsBeanInfo() {
		super();
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
	public String getThumb() {
		return thumb;
	}
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	
	
}
