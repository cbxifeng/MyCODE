package com.duihao.promotion.javavbean.android;

public class DiscussMath {

	//��������
	String model;  //ģ�ͷ���
	Integer	Id;//����id 
	Integer	catid;//����
	Integer	commentid;//����id 
	String	title;//����
	String	comment;//��ϸ��������
	Integer	userid;//���۷�����
	String 	addtime;//���ʱ��ʱ���
	String	ip;//������IP
	Integer	cid;//�������۵�ID/��һ��ID
	Integer	states;//����״̬
	Integer	posts;//������
	String	username;//�������û���
	Integer	modelid;//ģ��ID
	
	
	
	
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Integer getCatid() {
		return catid;
	}

	public void setCatid(Integer catid) {
		this.catid = catid;
	}

	public Integer getCommentid() {
		return commentid;
	}

	public void setCommentid(Integer commentid) {
		this.commentid = commentid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Integer getStates() {
		return states;
	}

	public void setStates(Integer states) {
		this.states = states;
	}

	public Integer getPosts() {
		return posts;
	}

	public void setPosts(Integer posts) {
		this.posts = posts;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getModelid() {
		return modelid;
	}

	public void setModelid(Integer modelid) {
		this.modelid = modelid;
	}

	public DiscussMath() {
		
		// TODO Auto-generated constructor stub
	}

	public DiscussMath(String model, Integer id, Integer catid,
			Integer commentid, String title, String comment, Integer userid,
			String addtime, String ip, Integer cid, Integer states,
			Integer posts, String username, Integer modelid) {
		
		this.model = model;
		Id = id;
		this.catid = catid;
		this.commentid = commentid;
		this.title = title;
		this.comment = comment;
		this.userid = userid;
		this.addtime = addtime;
		this.ip = ip;
		this.cid = cid;
		this.states = states;
		this.posts = posts;
		this.username = username;
		this.modelid = modelid;
	}

	
	
}
