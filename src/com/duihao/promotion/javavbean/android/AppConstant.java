package com.duihao.promotion.javavbean.android;

public interface AppConstant {
	// ===========初始化时请求的条数=====================
	int INIT_REQUEST_MAX_ROWS = 10;

	// ============一次请求的最大行数====================
	int REQUEST_MAX_ROWS = 5;
	int WEIBO_REQUEST_MAX_ROWS = 10;
	int USERID = 14;

	// 入口前网址
	String PUBLIC_URL = "http://b2b.duihao.net/";
	//
	int SAME_TAG = 14;
    
	// 栏目URL
	String PRODUCT_TITLE_URL = PUBLIC_URL
			+ "json/com.php?userid=%s&f=com.mytype&model=product";

	// 评论新闻请求URL
	String DISCUSS_NEW_URL = "http://b2b.duihao.net/json/?f=commentsave";

	// 评论列表
	String DISCUSS_URL = PUBLIC_URL
			+ "json/?f=comment&model=content&catid=%s&id=%s";

	// 产品列表URl
	String PRODUCKT_LIST_URL = PUBLIC_URL
			+ "json/com.php?f=com.list&model=product&pagesize=%s&userid=%s&mytypeid=%s";

	// 产品详细URl

	String PRODUCKTINFO_URL = PUBLIC_URL
			+ "json/?userid=%s&f=product.show&id=%s";

	// 动态分类
	String PRODUCT_DONG_TITLE_URL = PUBLIC_URL
			+ "json/com.php?userid=%s&f=com.mytype&model=comnews";
	// 企业动态列表
	String BUSINESS_URL = PUBLIC_URL
			+ "json/com.php?userid=%s&f=com.list&model=comnews&mytypeid=%s&pagesize=%s";
	// 动态详细
	String BUSINESS_INFO_URL = PUBLIC_URL
			+ "json/?f=comnews.show&userid=%s&id=%s";

	// 相册分类
	String PROCUCT_PIC = PUBLIC_URL + "json/com.php?userid=%s&f=com.mytype&model=album";
	
	// 企业相册详细
	String PRODUCT_PIC_URL = PUBLIC_URL
			+ "json/com.php?f=com.list&userid=%s&model=album&mytypeid=%s";

//	// 相册详细
//	String PIC_INFO_URL = PUBLIC_URL + "json/?f=album.show&id=%s";
	
	//咨询
	String ZIXUN_URL="http://yida.duihao.com/kf.php?mod=clientm&cid=duihao&wid=1&uid=1929140975";
	
	
	// 企业会员
	String ABOUT_URL = PUBLIC_URL + "json/com.php?userid=%s&f=com.about";
	
	// 圈子提交话题
	String CIRCLE_COMMIT = PUBLIC_URL + "json?f=community";
	
	
	// 圈子话题列表
	String CIRCLE_LIST = PUBLIC_URL + "json/?f=community.list&pagesize=%s";
	
    //评论
	String CIRCLE_DIS=PUBLIC_URL + "json/?f=community.comment";
	
	
	String CIRCLE_PINLU=PUBLIC_URL + "m/com/?f=community.show&id=%s&userid=%s";

	// 注册接口
	String M_REGISTER = "http://ts.cdn.duihao.net/m/user/register.php";
	
}
