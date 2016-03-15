package com.austin.mynihonngo.engine;



/**樱花JP 网络引擎
 * @author Austin
 *
 */
public interface ISakuraEngine {
	/**获得樱花 JP的侧边栏数据（学习级别）
	 * @param listener
	 * @param flag
	 */
	void getSakuraData(OnResultListener listener, Boolean flag);
	
	
	/**得到词汇中心 --> 名词 大类--> 每个小分类的页面(ViewPager的一个Item页)的数据
	 * @param listener
	 * @param flag
	 */
/*	void getNounClassifyItemData(RequestParams param,OnResultListener listener, Boolean flag);

	void getVerbClassifyItemData(RequestParams param,
			OnResultListener listener, Boolean flag);
*/
	/**抽出的通用方法，获得 词汇中心--》某一个大类下(如形容词)--》某一个单独类别的数据（如一类形容词）
	 * @param wordFlag 值取自GlobalParams.URL_LOGIN_...用来告诉接口你要获得那种词性的单词
	 * @param param
	 * @param onResultListener
	 * @param b
	 */
	/*void getClassifyItemData(String wordFlag,RequestParams param,
			OnResultListener onResultListener, boolean b);*/
}
