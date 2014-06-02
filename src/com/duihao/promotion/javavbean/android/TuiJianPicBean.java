package com.duihao.promotion.javavbean.android;

import android.os.Parcel;
import android.os.Parcelable;

public class TuiJianPicBean implements Parcelable {
	public TuiJianPicBean() {

	}

	public TuiJianPicBean(Parcel in) {

		id = in.readInt();
		title = in.readString();
		description = in.readString();
		thumb = in.readString();
		catid = in.readInt();
		addtime = in.readString();
		edittime = in.readString();
	}

	Integer id;// 内容id
	String title;// 内容标题
	String description;// 简介
	String thumb;// 缩略图路径
	Integer catid;// 所属分类id
	String addtime;// 添加时间
	String edittime;// 更新时间
	Integer parentid;// title 的 catid

	public Integer getParentid() {
		return parentid;
	}

	public void setParentid(Integer parentid) {
		this.parentid = parentid;
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

	public Integer getCatid() {
		return catid;
	}

	public void setCatid(Integer catid) {
		this.catid = catid;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getEdittime() {
		return edittime;
	}

	public void setEdittime(String edittime) {
		this.edittime = edittime;
	}

	public TuiJianPicBean(Integer id, String title, String description,
			String thumb, Integer catid, String addtime, String edittime) {

		this.id = id;
		this.title = title;
		this.description = description;
		this.thumb = thumb;
		this.catid = catid;
		this.addtime = addtime;
		this.edittime = edittime;

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
		dest.writeInt(catid);
		dest.writeString(addtime);
		dest.writeString(edittime);

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
