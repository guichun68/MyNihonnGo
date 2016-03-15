package com.austin.mynihonngo.base;

import com.austin.mynihonngo.engine.TabChangedListener;
import com.austin.mynihonngo.utils.BeanFactoryUtil;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class GlobalParams {
	public static String BASE_URL ;
	static{
		BASE_URL = BeanFactoryUtil.properties.getProperty("BaseURL_fjjsp");
	}
	/**
	 * 获取名词 的RootUrl
	 */
	public static String URL_NOUNS = GlobalParams.BASE_URL+"/word/getNounServlet";
	public static String URL_SAKURA_CLASS_ITEM = GlobalParams.BASE_URL+"/sakura/getSakuraServlet";
	public static String URL_VERBS = GlobalParams.BASE_URL+"/word/getVerbServlet";
	public static String URL_ADJS = GlobalParams.BASE_URL+"/word/getAdjServlet";
	public static String URL_OTHER = GlobalParams.BASE_URL+"/word/getOtherServlet";
	
	public static String URL_LOGIN = GlobalParams.BASE_URL+"/user/LoginServletApp";
	public static String URL_REGIST = GlobalParams.BASE_URL+"/user/RegistServletApp";
	public static String URL_NULL = GlobalParams.BASE_URL+"";
	/**
	 * 单词中心 侧边栏请求 url
	 */
	public static String URL_SLIDING_MENU_WORD_CENTER = GlobalParams.BASE_URL+"/word/wordCenterServletApp";
	public static String URL_SLIDING_MENU_SAKURA = GlobalParams.BASE_URL+"/sakura/getLevels";
	
	public static void refreshIP(){
		URL_SLIDING_MENU_WORD_CENTER = GlobalParams.BASE_URL+"/word/wordCenterServletApp";
		URL_SLIDING_MENU_SAKURA = GlobalParams.BASE_URL+"/sakura/getLevels";
		URL_SAKURA_CLASS_ITEM = GlobalParams.BASE_URL+"/sakura/getSakuraServlet";
		URL_NOUNS = GlobalParams.BASE_URL+"/word/getNounServlet";
		URL_VERBS = GlobalParams.BASE_URL+"/word/getVerbServlet";
		URL_ADJS = GlobalParams.BASE_URL+"/word/getAdjServlet";
		URL_OTHER = GlobalParams.BASE_URL+"/word/getOtherServlet";
		
		URL_LOGIN = GlobalParams.BASE_URL+"/user/LoginServletApp";
		URL_REGIST = GlobalParams.BASE_URL+"/user/RegistServletApp";
		URL_NULL = GlobalParams.BASE_URL+"";		
	}
	
	
	
	public static boolean isLoggedIn;
	public static SlidingFragmentActivity MAIN;
	/**
	 * 代理ip
	 */
	public static String PROXY_IP = "";
	/**
	 * 代理端口
	 */
	public static Integer PROXY_PORT = 0;
	public static int default_layout_id = 0;
	/**
	 * 当前选中的选项卡 角标（比如App打开时默认0,即表示打开了单词中心）
	 */
	public static int CURR_TAB_INDEX;
	
	public static TabChangedListener tabChangedListener;
}
