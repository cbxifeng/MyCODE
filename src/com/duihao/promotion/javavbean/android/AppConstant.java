package com.duihao.promotion.javavbean.android;

public interface AppConstant {
	// ===========��ʼ��ʱ���������=====================
	int INIT_REQUEST_MAX_ROWS = 10;

	// ============һ��������������====================
	int REQUEST_MAX_ROWS = 5;
	int WEIBO_REQUEST_MAX_ROWS = 10;
	int USERID = 14;

	// ���ǰ��ַ
	String PUBLIC_URL = "http://b2b.duihao.net/";
	//
	int SAME_TAG = 14;
    
	// ��ĿURL
	String PRODUCT_TITLE_URL = PUBLIC_URL
			+ "json/com.php?userid=%s&f=com.mytype&model=product";

	// ������������URL
	String DISCUSS_NEW_URL = "http://b2b.duihao.net/json/?f=commentsave";

	// �����б�
	String DISCUSS_URL = PUBLIC_URL
			+ "json/?f=comment&model=content&catid=%s&id=%s";

	// ��Ʒ�б�URl
	String PRODUCKT_LIST_URL = PUBLIC_URL
			+ "json/com.php?f=com.list&model=product&pagesize=%s&userid=%s&mytypeid=%s";

	// ��Ʒ��ϸURl

	String PRODUCKTINFO_URL = PUBLIC_URL
			+ "json/?userid=%s&f=product.show&id=%s";

	// ��̬����
	String PRODUCT_DONG_TITLE_URL = PUBLIC_URL
			+ "json/com.php?userid=%s&f=com.mytype&model=comnews";
	// ��ҵ��̬�б�
	String BUSINESS_URL = PUBLIC_URL
			+ "json/com.php?userid=%s&f=com.list&model=comnews&mytypeid=%s&pagesize=%s";
	// ��̬��ϸ
	String BUSINESS_INFO_URL = PUBLIC_URL
			+ "json/?f=comnews.show&userid=%s&id=%s";

	// ������
	String PROCUCT_PIC = PUBLIC_URL + "json/com.php?userid=%s&f=com.mytype&model=album";
	
	// ��ҵ�����ϸ
	String PRODUCT_PIC_URL = PUBLIC_URL
			+ "json/com.php?f=com.list&userid=%s&model=album&mytypeid=%s";

//	// �����ϸ
//	String PIC_INFO_URL = PUBLIC_URL + "json/?f=album.show&id=%s";
	
	//��ѯ
	String ZIXUN_URL="http://yida.duihao.com/kf.php?mod=clientm&cid=duihao&wid=1&uid=1929140975";
	
	
	// ��ҵ��Ա
	String ABOUT_URL = PUBLIC_URL + "json/com.php?userid=%s&f=com.about";
	
	// Ȧ���ύ����
	String CIRCLE_COMMIT = PUBLIC_URL + "json?f=community";
	
	
	// Ȧ�ӻ����б�
	String CIRCLE_LIST = PUBLIC_URL + "json/?f=community.list&pagesize=%s";
	
    //����
	String CIRCLE_DIS=PUBLIC_URL + "json/?f=community.comment";
	
	
	String CIRCLE_PINLU=PUBLIC_URL + "m/com/?f=community.show&id=%s&userid=%s";

	// ע��ӿ�
	String M_REGISTER = "http://ts.cdn.duihao.net/m/user/register.php";
	
}
