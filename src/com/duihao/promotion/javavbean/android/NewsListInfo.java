package com.duihao.promotion.javavbean.android;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsListInfo implements Parcelable {

	public NewsListInfo() {

	}

	public NewsListInfo(Parcel in) {

		id = in.readInt();
		title = in.readString();
		description = in.readString();
		thumb = in.readString();
		mytypeid = in.readString();
		addtime = in.readString();
		endtime = in.readString();
		status = in.readString();
		price_old=in.readString();
	}

	Integer id;// 内容id
	String title;// 内容标题
	String description;// 简介
	String thumb;// 缩略图路径
	String mytypeid;// 分类id
	String addtime;// 添加时间
	String endtime;// 结束时间
	String price;// 价格
	String price_old;// 价格
	String status;// 产品状态
	String url;
	String yuding;

	public NewsListInfo(Integer id, String title, String thumb) {
		super();
		this.id = id;
		this.title = title;
		this.thumb = thumb;
	}

	public NewsListInfo(Integer id, String title, String description,
			String thumb, String mytypeid, String addtime, String endtime,
			String price, String status, String url, String yuding,String price_old) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.thumb = thumb;
		this.mytypeid = mytypeid;
		this.addtime = addtime;
		this.endtime = endtime;
		this.price = price;
		this.status = status;
		this.url = url;
		this.yuding = yuding;
		this.price_old=price_old;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getYuding() {
		return yuding;
	}

	public void setYuding(String yuding) {
		this.yuding = yuding;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public String getMytypeid() {
		return mytypeid;
	}

	public void setMytypeid(String mytypeid) {
		this.mytypeid = mytypeid;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getPrice_old() {
		return price_old;
	}

	public void setPrice_old(String price_old) {
		this.price_old = price_old;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return CONTENTS_FILE_DESCRIPTOR;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(id);
		dest.writeString(title);
		dest.writeString(description);
		dest.writeString(thumb);
		dest.writeString(mytypeid);
		dest.writeString(addtime);
		dest.writeString(endtime);
		dest.writeString(status);
		dest.writeString(price_old);
		// dest.writeInt(parentid);
	}

	public static final Parcelable.Creator<NewsListInfo> CREATOR = new Parcelable.Creator<NewsListInfo>() {
		public NewsListInfo createFromParcel(Parcel in) {
			return new NewsListInfo(in);
		}

		public NewsListInfo[] newArray(int size) {
			return new NewsListInfo[size];
		}
	};

	public static Parcelable.Creator<NewsListInfo> getCreator() {
		return CREATOR;
	}

}
