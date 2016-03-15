package com.austin.mynihonngo.pager;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.austin.mynihonngo.MainActivity;
import com.austin.mynihonngo.R;
import com.austin.mynihonngo.base.BasePager;
import com.austin.mynihonngo.base.ConstantValue;
import com.austin.mynihonngo.base.GlobalParams;
import com.austin.mynihonngo.bean.WordCenter;
import com.austin.mynihonngo.engine.IWordCenterEngine;
import com.austin.mynihonngo.engine.OnResultListener;
import com.austin.mynihonngo.utils.BeanFactoryUtil;
import com.austin.mynihonngo.utils.GsonUtil;
import com.austin.mynihonngo.utils.SharedPreferencesUtils;
import com.austin.mynihonngo.utils.UIUtil;

/**单词中心页面
 * @author Austin
 *
 */
public class WordCenterPager extends BasePager {

	private String TAG = WordCenterPager.class.getSimpleName();
	/**
	 * 名词、动词、形容词
	 */
	private RelativeLayout rlNoun,rlVerb,rlAdj;
	private FrameLayout frameLayout;
	
	/**
	 * 侧边栏 条目
	 */
	private List<String> titleList = new ArrayList<String>();
	//左侧侧拉栏目关联指定类的一个集合
	private List<BaseWordPager> pagers = new ArrayList<BaseWordPager>();
	
	public WordCenterPager(Context context) {
		super(context);
	}

	@Override
	public View initView() {
		//当前newCenterPager的布局加载进来
		view = View.inflate(context, R.layout.frame_of_pager, null);
		//初始化title操作
		initTitleBar();
		//-----FindView------------
		frameLayout = (FrameLayout) view.findViewById(R.id.fl_of_pager);
		//------------------------
		return view;
	}
	
	/**  -------------
	 * @return
	 */
	@Override
	public void initData() {
		//从本地缓存拿数据
/*		String result = SharedPreferencesUtils.getStringData(context, GlobalParams.URL_SLIDING_MENU, "");
		if(!TextUtils.isEmpty(result)){
			processData(result);
		}else
		{
			getHomeData();
		}*/
		getHomeData();
		isFirstComeInThisPager = false;
	}

	private void processData(String result) {
		WordCenter wordCenter = GsonUtil.jsonToBean(result, WordCenter.class);
		if(wordCenter == null || !wordCenter.getCode().equals(ConstantValue.ACCCESS_SUCCESS)){
			UIUtil.showToastSafe(UIUtil.getString(R.string.get_data_error));
			return;
		}
		titleList.clear();
		pagers.clear();
		
		//侧边栏
		for(int i=0;i<wordCenter.getWordTypes().size();i++){
			String nameEn = wordCenter.getWordTypes().get(i).getTypeNameEn();
			if("Verb".equals(nameEn)){
				titleList.add(context.getResources().getString(R.string.verb));
			}else if("Noun".equals(nameEn)){
				titleList.add(context.getResources().getString(R.string.noun));
			}else if("Adjective".equals(nameEn)){
				titleList.add(context.getResources().getString(R.string.adj));
			}else if("Others".equals(nameEn)){
				titleList.add(context.getResources().getString(R.string.others));
			}
		}
		//将当前集合传送到左侧侧拉栏目显示
		//1,获取MainActivity对象
		//2,获取MenuFragment对象（通过MainActivity中Tag去获取）
		//3，调用initMenu方法，将数据传递至MenuFragment中去，填充UI
//		MainActivity main = (MainActivity) context;
//		MenuFragment menuFragment =main.getMenuFragment();
//		menuFragment.initMenu(titleList);
		((MainActivity) context).getMenuFragment().initMenu(titleList);
		pagers.add(new WordPagerNoun(context,wordCenter.getWordTypes().get(0).getChildren(),R.layout.frag_pager));
		pagers.add(new WordPagerVerb(context,wordCenter.getWordTypes().get(1).getChildren(),R.layout.frag_pager));
		pagers.add(new WordPagerAdj(context,wordCenter.getWordTypes().get(2).getChildren(),R.layout.frag_pager));
		pagers.add(new WordPagerOther(context,wordCenter.getWordTypes().get(3).getChildren(),R.layout.frag_pager));
		
		//----------将titleList内容存到sp
		StringBuilder sb = new StringBuilder();
		for(int j=0;j<titleList.size();j++){
			sb.append(titleList.get(j)).append("-");
		}
		String sub = sb.toString().substring(0,sb.toString().lastIndexOf("-"));
		SharedPreferencesUtils.putString(ConstantValue.SP_SLIDINGM_LISTSTR_WORD_CENTER, sub);
		txt_title.addTextChangedListener(((MainActivity)context).getMenuFragment());
		//----------
		switchPager(0);
	}



	public void switchPager(int i) {
		//设置当前页title
		txt_title.setText(titleList.get(i));

		frameLayout.removeAllViews();
		
		//设置不同页面下的标题数量
		switch (i) {
		case 0://名词页4 （动物、植物、交通工具、其他）
			pagers.get(i).indicator.setVisibleTabCount(4);
			break;
		case 1://动词页3 （一类 、二类、三类）
			pagers.get(i).indicator.setVisibleTabCount(3);
			break;
		case 2://形容词页2（一类、二类）
			pagers.get(i).indicator.setVisibleTabCount(2);
			break;
		case 3://其他页3（接头词、接尾词、全部）
			pagers.get(i).indicator.setVisibleTabCount(3);
			break;
		default:
			pagers.get(i).indicator.setVisibleTabCount(3);
			break;
		}
		//填充指定页面
		frameLayout.addView(pagers.get(i).getRootView());
		BasePager basePager = pagers.get(i);
		basePager.initData();
	}

	
	/**
	 * 从网络获取JSON数据，并将数据缓存到SP----------NEW
	 */
	private void getHomeData() {
		IWordCenterEngine engine = BeanFactoryUtil.getImpl(IWordCenterEngine.class);
		engine.getWordCenterData(new OnResultListener() {
			
			@Override
			public void onGetData(Object obj, int what) {
				//获取数据操作，缓存信息，1数据库,2文件,3内存,4share
				Log.i(TAG, obj.toString());
				SharedPreferencesUtils.saveStringData(context, GlobalParams.URL_SLIDING_MENU_WORD_CENTER, (String)obj);
				processData((String)obj);
			}
			
			@Override
			public void onFailure(String msg) {
				UIUtil.showToastSafe(msg);
			}
		}, false);
	}
}
