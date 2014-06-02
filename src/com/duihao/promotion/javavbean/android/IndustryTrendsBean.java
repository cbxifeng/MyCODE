package com.duihao.promotion.javavbean.android;

//产品动态列表
public class IndustryTrendsBean {

	String addtime;//
	Integer id;// 企业动态id
	String title;// 标题
	String thumb;// 缩略图
	String content;// 内容
	String description;
	String url;
	String yuding;

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	public String getYuding() {
		return yuding;
	}

	public void setYuding(String yuding) {
		this.yuding = yuding;
	}

	public IndustryTrendsBean() {
		super();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
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
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public IndustryTrendsBean(Integer id, String title, String thumb,
			String description, String addtime, String url) {
		super();
		this.id = id;
		this.title = title;
		this.thumb = thumb;
		this.description = description;
		this.addtime = addtime;
		this.url = url;

	}

	public IndustryTrendsBean(Integer id, String title, String thumb,
			String description, String addtime, String url, String yuding) {
		super();
		this.id = id;
		this.title = title;
		this.thumb = thumb;
		this.description = description;
		this.addtime = addtime;
		this.url = url;
		this.yuding = yuding;

	}

}
