package com.austin.mynihonngo.base;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.austin.mynihonngo.MainActivity;
import com.austin.mynihonngo.R;
import com.austin.mynihonngo.engine.IFragmentListener;
import com.austin.mynihonngo.fragment.SakuraSentceDetailFrg;
import com.austin.mynihonngo.fragment.WordDetailFragment;
import com.austin.mynihonngo.utils.SharedPreferencesUtils;
import com.austin.mynihonngo.utils.StringUtil;
import com.austin.mynihonngo.utils.UIManager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public abstract class BasePager implements IFragmentListener{
	public Context context;
	public View view;
	private Button btn_left;
	private ImageButton imgbtn_left;
	public SlidingMenu slidingMenu;
	public TextView txt_title;
	private ImageButton imgbtn_right;
	private ImageButton btn_right;
	private FragmentManager mFrgManager;
	private String TAG = BasePager.class.getSimpleName();
	public int layoutResourceId;
	public boolean isFirstComeInThisPager = true;
	public int slidingMenuIndex;
	
	public boolean isFirstComeInThisPager() {
		return isFirstComeInThisPager;
	}

	public void setFirstComeInThisPager(boolean isFirstComeInThisPager) {
		this.isFirstComeInThisPager = isFirstComeInThisPager;
	}

	public BasePager(Context context){
		this.context = context;
		view = initView();
		mFrgManager = ((MainActivity)context).getSupportFragmentManager();
		slidingMenu = ((MainActivity)context).getSlidingMenu();
	}
	
	//初始化view操作
	public BasePager(Context context,int layoutResourceId) {
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		view = initView();
		mFrgManager = ((MainActivity)context).getSupportFragmentManager();
		slidingMenu = ((MainActivity)context).getSlidingMenu();
	}
	public void initTitleBar(){
		btn_left = (Button) view.findViewById(R.id.btn_left);
		btn_left.setVisibility(View.GONE);
		
		imgbtn_left = (ImageButton) view.findViewById(R.id.imgbtn_left);
		imgbtn_left.setImageResource(R.mipmap.img_menu);
		
		//点击按钮->侧边栏展开
		imgbtn_left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//拉伸出左侧侧拉栏目
				slidingMenu.toggle();
			}
		});
		
		txt_title = (TextView) view.findViewById(R.id.txt_title);
	
		imgbtn_right = (ImageButton) view.findViewById(R.id.imgbtn_right);
		imgbtn_right.setVisibility(View.GONE);
		
		btn_right = (ImageButton) view.findViewById(R.id.btn_right);
		btn_right.setVisibility(View.GONE);
		
		
	}
	//获取根节点
	public View getRootView(){
		return view;
	}
	//所有子类实现当前方法
	public abstract View initView();
	//填充数据方法
	public abstract void initData();
	
	public void getData(HttpMethod httpMethod,String url,RequestParams params,RequestCallBack<String> callBack){
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(httpMethod, url, params,callBack);
	}
	
	@Override
	public void openFragment(String fragTag, Integer... param){
		
	}
	
	@Override
	public void openFragment(Fragment hidTag,String fragTag,Bundle bundle){
		
	}
	
	@Override
	public <T>void  openFragment(Fragment hidFrg,String fragTag,ArrayList<T> list){
		
	}

	@Override
	public void openFragment(String fragTag, Bundle bundle){
		Log.e(TAG, "There's nothing to do.");
	}
	
	@Override
	public void openFragment(BasePager pager, String targetFrag, Bundle bundle) {
		Fragment target = mFrgManager.findFragmentByTag(targetFrag);
		if(target == null){
			if (ConstantValue.FRAG_TAG_WORD_DETAIL.equals(targetFrag)) {
				target = new WordDetailFragment();
			}else if(ConstantValue.FRAG_TAG_SENTENCE_DETAIL.equals(targetFrag)){
				target = new SakuraSentceDetailFrg();
			}else{
				throw new RuntimeException("没有定义该fragmentTag--》error from BasePager");
			}
		}
		UIManager.getInstance().changeFragmentAndSaveViews(((MainActivity)context).getHomeFragment(),target, false, true, bundle, targetFrag);
	}

	/**初始化侧边栏 字符串集合
	 * @param tabIndex 当前tab的角标，如 单词中心 为0 ，樱花JP为1
	 */
	public void intSlidingMenuList(int tabIndex) {
		int slidingIndex = 0;
		
		String slidingStr = SharedPreferencesUtils.getString(ConstantValue.SP_SLIDINGM_LISTSTR_WORD_CENTER, "");
		String slidingStrSkr = SharedPreferencesUtils.getString(ConstantValue.SP_SLIDINGM_LISTSTR_SKR, "");
		if(tabIndex == 0){
			if(!StringUtil.isEmpty(slidingStr)){
				String[] split = slidingStr.split("-");
				ArrayList<String> titleList = new ArrayList<String>();
				for(int i=0;i<split.length;i++){
					titleList.add(split[i]);
				}
				((MainActivity) context).getMenuFragment().initMenu(titleList);
			}
			slidingIndex = SharedPreferencesUtils.getInt(ConstantValue.SP_SLIDINGM_CURRINDEX_WORD_CENTER, 0);
		}else if(tabIndex == 1){
			if(!StringUtil.isEmpty(slidingStrSkr)){
				String[] split = slidingStrSkr.split("-");
				ArrayList<String> titleList = new ArrayList<String>();
				for(int i=0;i<split.length;i++){
					titleList.add(split[i]);
				}
				((MainActivity) context).getMenuFragment().initMenu(titleList);
			}
			slidingIndex = SharedPreferencesUtils.getInt(ConstantValue.SP_SLIDINGM_CURRINDEX_SAKURA, 0);
		}
		((MainActivity) context).getMenuFragment().setCurrentPosition(slidingIndex);
	}
}
	