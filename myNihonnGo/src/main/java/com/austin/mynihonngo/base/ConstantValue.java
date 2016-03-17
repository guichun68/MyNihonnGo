package com.austin.mynihonngo.base;


public interface ConstantValue {
	/**
	 * 服务器地址
	 */
//	String MYWEB_URI = "http://tfgyc60474.bj.jspee.com";
//	String MYWEB_URI = "http://192.168.6.103";
//	String MYWEB_URI = "http://192.168.0.1";
//	public final static String BASE_URL = "http://192.168.6.103";
	/**
	 * 登陆类型:本地自动登陆
	 */
	public static final String LOGIN_TYPE_LOCAL = "LOCAL";
	public static final String LOGIN_TYPE_REMOTE = "REMOTE";
	/**
	 * Fragment tags
	 */
	public static final String FRAG_TAG_HOME = "HOME_FRAG";
	public static final String FRAG_TAG_WORD_DETAIL = "WORD_DETAIL";
	public static final String FRAG_TAG_MENU = "MENU";
	public static final String FRAG_TAG_LOGIN = "LOGIN_FRAG";
	/**
	 * 返回值 1 标识访问成功并信息验证通过
	 */
	public static final Integer ACCCESS_SUCCESS = 1;
	/**
	 * 网络访问成功，但信息验证未通过
	 */
	public static final Integer ACCCESS_REFUSE = 400;
	
	
	/**
	 * 名词分类之 动物篇
	 */
	public static final int NOUN_TYPE_ANIMAL = 1;
	public static final int LEVEL1 = 1;
	public static final int LEVEL2 = 2;
	public static final int LEVEL3 = 3;
	public static final int LEVEL4 = 4;
	public static final int LEVEL5 = 5;
	public static final int LEVEL6 = 6;
	public static final int LEVEL7 = 7;
	public static final int LEVEL8 = 8;
	public static final int LEVEL9 = 9;
	public static final int LEVEL10 = 10;
	public static final int LEVEL11 = 11;
	public static final int LEVEL12 = 12;
	
	/**
	 * 名词分类之 植物篇
	 */
	public static final int NOUN_TYPE_PLANT = 2;
	/**
	 * 名词分类之 交通工具篇
	 */
	public static final int NOUN_TYPE_VEHICLE = 3;
	/**
	 * 名词分类之 交通工具篇
	 */
	public static final int NOUN_TYPE_OTHER = 10;
	/**
	 * 动词分类之 1类
	 */
	public static final int VERB_TYPE_ONE = 1;
	
	/**
	 * 动词分类之 2类
	 */
	public static final int VERB_TYPE_TWO = 2;
	
	/**
	 * 动词分类之 3类
	 */
	public static final int VERB_TYPE_THREE = 3;
	
	/**
	 * 1类形容词 い型
	 */
	public static final int ADJ_TYPE_ONE = 1;
	/**
	 * 2类形容词 な型
	 */
	public static final int ADJ_TYPE_TWO = 2;
	/**
	 * 其他类型所有标识
	 */
	public static final int OTHER_TYPE_ALL = 1;
	/**
	 * 接头词
	 */
	public static final int OTHER_TYPE_ONE = 2;
	/**
	 * 接尾词
	 */
	public static final int OTHER_TYPE_TWO = 3;
	
	public static final String FRAG_TAG_SENTENCE_DETAIL = "sakura_sentence_frg";
	
	/**
	 * 保存有wordCenter选项卡中侧边栏条目的集合的key（存储在SharedPreference）
	 */
	public static final String SP_SLIDINGM_LISTSTR_WORD_CENTER="SP_SLIDINGM_LISTSTR_WORD_CENTER";
	/**
	 * 保存有樱花JP选项卡中侧边栏条目的集合的key（存储在SharedPreference）
	 */
	public static final String SP_SLIDINGM_LISTSTR_SKR="SP_SLIDINGM_LISTSTR_skr";
	/**
	 * 保存有wordCenter选项卡中侧边栏条目的当前选中角标的key（存储在SharedPreference）
	 */
	public static final String SP_SLIDINGM_CURRINDEX_WORD_CENTER = "sp_slidingM_liststr";
	/**
	 * 保存有樱花JP选项卡中侧边栏条目的当前选中条目的角标的key（存储在SharedPreference）
	 */
	public static final String SP_SLIDINGM_CURRINDEX_SAKURA = "sp_slidingM_currIndex_skr_liststr";
	
//--------------环信常量start---------------------------------
 	 String NEW_FRIENDS_USERNAME = "item_new_friends";
 	 String GROUP_USERNAME = "item_groups";
     String CHAT_ROOM = "item_chatroom";
	 String ACCOUNT_REMOVED = "account_removed";
	 String ACCOUNT_CONFLICT = "conflict";
	 String CHAT_ROBOT = "item_robots";
	 String MESSAGE_ATTR_ROBOT_MSGTYPE = "msgtype";
	 String ACTION_GROUP_CHANAGED = "action_group_changed";
	 String ACTION_CONTACT_CHANAGED = "action_contact_changed";
//--------------------------end-----------------------------
}
