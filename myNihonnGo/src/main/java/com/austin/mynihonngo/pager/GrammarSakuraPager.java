package com.austin.mynihonngo.pager;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.austin.mynihonngo.MainActivity;
import com.austin.mynihonngo.R;
import com.austin.mynihonngo.base.BasePager;
import com.austin.mynihonngo.base.ConstantValue;
import com.austin.mynihonngo.base.GlobalParams;
import com.austin.mynihonngo.bean.SakuraResult;
import com.austin.mynihonngo.bean.WordCenter;
import com.austin.mynihonngo.bean.WordType;
import com.austin.mynihonngo.engine.ISakuraEngine;
import com.austin.mynihonngo.engine.IWordCenterEngine;
import com.austin.mynihonngo.engine.OnResultListener;
import com.austin.mynihonngo.utils.BeanFactoryUtil;
import com.austin.mynihonngo.utils.GsonUtil;
import com.austin.mynihonngo.utils.SharedPreferencesUtils;
import com.austin.mynihonngo.utils.UIUtil;
import com.google.gson.Gson;

/**
 * 语法页面(樱花JP版)
 * 
 * @author Austin
 * 
 */
public class GrammarSakuraPager extends BasePager {

	private FrameLayout frameLayout;// 随着顶部标签改变而改变的底部页面
	private String TAG = GrammarSakuraPager.class.getSimpleName();
	private WordCenter wordCenter;

	/**
	 * 侧边栏 条目
	 */
	private List<String> titleList = new ArrayList<String>();
	// 左侧侧拉栏目关联指定类的一个集合
	private List<BaseWordPager> pagers = new ArrayList<BaseWordPager>();

	public GrammarSakuraPager(Context context) {
		super(context);
	}

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.frame_of_pager, null);
		frameLayout = (FrameLayout) view.findViewById(R.id.fl_of_pager);
		initTitleBar();// 初始化标题栏
		return view;
	}

	@Override
	public void initData() {
		getHomeData();
		isFirstComeInThisPager = false;
	}

	/**
	 * 从网络获取JSON数据，并将数据缓存到SP-
	 */
	private void getHomeData() {
		ISakuraEngine engine = BeanFactoryUtil.getImpl(ISakuraEngine.class);
		engine.getSakuraData(new OnResultListener() {

			@Override
			public void onGetData(Object obj, int what) {
				// 获取数据操作，缓存信息，1数据库,2文件,3内存,4share
				Log.i(TAG, obj.toString());
				SharedPreferencesUtils
						.saveStringData(context,
								GlobalParams.URL_SLIDING_MENU_WORD_CENTER,
								(String) obj);
				processData((String) obj);
			}

			@Override
			public void onFailure(String msg) {
				UIUtil.showToastSafe(msg);
			}
		}, false);
	}

	private void processData(String result) {
		wordCenter = GsonUtil.jsonToBean(result, WordCenter.class);
		// JSONArray array = JSON.parseArray(result);
		// array.get
		if (wordCenter == null
				|| !wordCenter.getCode().equals(ConstantValue.ACCCESS_SUCCESS)) {
			UIUtil.showToastSafe(UIUtil.getString(R.string.get_data_error));
			return;
		}
		titleList.clear();
		pagers.clear();

		// 侧边栏
		for (int i = 0; i < wordCenter.getWordTypes().size(); i++) {
			String typeNameEn = wordCenter.getWordTypes().get(i)
					.getTypeNameCn();
			if (typeNameEn.contains("一级")) {
				titleList.add(UIUtil.getString(R.string.level1));
			}else if (typeNameEn.contains("二级")) {
				titleList.add(UIUtil.getString(R.string.level2));
			} else if (typeNameEn.contains("三级")) {
				titleList.add(UIUtil.getString(R.string.level3));
			} else if (typeNameEn.contains("四级")) {
				titleList.add(UIUtil.getString(R.string.level4));
			} else if (typeNameEn.contains("五级")) {
				titleList.add(UIUtil.getString(R.string.level5));
			} else if (typeNameEn.contains("六级")) {
				titleList.add(UIUtil.getString(R.string.level6));
			} else if (typeNameEn.contains("七级")) {
				titleList.add(UIUtil.getString(R.string.level7));
			} else if (typeNameEn.contains("八级")) {
				titleList.add(UIUtil.getString(R.string.level8));
			} else if (typeNameEn.contains("九级")) {
				titleList.add(UIUtil.getString(R.string.level9));
			} else if (typeNameEn.contains("十级")) {
				titleList.add(UIUtil.getString(R.string.level10));
			} else if (typeNameEn.contains("十一级")) {
				titleList.add(UIUtil.getString(R.string.level11));
			} else if (typeNameEn.contains("十二级")) {
				titleList.add(UIUtil.getString(R.string.level12));
			}
		}
		((MainActivity) context).getMenuFragment().initMenu(titleList);

		pagers.add(new SakuraPager(context, ConstantValue.LEVEL2,wordCenter
				.getWordTypes().get(0).getChildren(), R.layout.frag_pager));
		pagers.add(new SakuraPager(context, ConstantValue.LEVEL3, wordCenter
				.getWordTypes().get(0).getChildren(), R.layout.frag_pager));
		pagers.add(new SakuraPager(context, ConstantValue.LEVEL4, wordCenter
				.getWordTypes().get(0).getChildren(), R.layout.frag_pager));
		pagers.add(new SakuraPager(context, ConstantValue.LEVEL5, wordCenter
				.getWordTypes().get(0).getChildren(), R.layout.frag_pager));
		pagers.add(new SakuraPager(context, ConstantValue.LEVEL6,wordCenter
				.getWordTypes().get(0).getChildren(), R.layout.frag_pager));
		/*
		 * pagers.add(new
		 * WordPagerVerb(context,wordCenter.getWordTypes().get(1).
		 * getChildren(),R.layout.frag_pager)); pagers.add(new
		 * WordPagerAdj(context
		 * ,wordCenter.getWordTypes().get(2).getChildren(),R.
		 * layout.frag_pager)); pagers.add(new
		 * WordPagerOther(context,wordCenter.
		 * getWordTypes().get(3).getChildren(),R.layout.frag_pager));
		 */
		//----------将titleList内容存到sp
		StringBuilder sb = new StringBuilder();
		for(int j=0;j<titleList.size();j++){
			sb.append(titleList.get(j)).append("-");
		}
		String sub = sb.toString().substring(0,sb.toString().lastIndexOf("-"));
		SharedPreferencesUtils.putString(ConstantValue.SP_SLIDINGM_LISTSTR_SKR, sub);
		txt_title.addTextChangedListener(((MainActivity)context).getMenuFragment());
		//----------
		switchPager(0);
	}

	public void switchPager(int i) {
		// 设置当前页title
		txt_title.setText(titleList.get(i));
		((MainActivity) context).getMenuFragment().setMenuName(titleList.get(i));
		frameLayout.removeAllViews();

		// 设置不同页面下的标题数量
		switch (i) {
		case 0:// level2
			pagers.get(i).indicator.setVisibleTabCount(5);
			break;
		case 1:// level3
			pagers.get(i).indicator.setVisibleTabCount(5);
			break;
		case 2:// level4
			pagers.get(i).indicator.setVisibleTabCount(5);
			break;
		case 3:// level5
			pagers.get(i).indicator.setVisibleTabCount(5);
			break;
		default:
			pagers.get(i).indicator.setVisibleTabCount(5);
			break;
		}
		// 填充指定页面
		frameLayout.addView(pagers.get(i).getRootView());

		BasePager basePager = pagers.get(i);
		basePager.initData();
	}

}
