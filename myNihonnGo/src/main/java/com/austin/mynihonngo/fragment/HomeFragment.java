package com.austin.mynihonngo.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.austin.mynihonngo.MainActivity;
import com.austin.mynihonngo.R;
import com.austin.mynihonngo.base.BaseFragment;
import com.austin.mynihonngo.base.BasePager;
import com.austin.mynihonngo.base.GlobalParams;
import com.austin.mynihonngo.engine.IFragmentListener;
import com.austin.mynihonngo.engine.TabChangedListener;
import com.austin.mynihonngo.pager.GovAffairsPager;
import com.austin.mynihonngo.pager.GrammarBiaoriPager;
import com.austin.mynihonngo.pager.GrammarSakuraPager;
import com.austin.mynihonngo.pager.SettingPager;
import com.austin.mynihonngo.pager.WordCenterPager;
import com.austin.mynihonngo.view.LazyViewPager.OnPageChangeListener;
import com.austin.mynihonngo.view.MyViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class HomeFragment extends BaseFragment {
	public static final String tag = HomeFragment.class.getSimpleName();
	public TabChangedListener mTabChangedListener;
	private View view;

	@ViewInject(R.id.layout_content)
	private MyViewPager layout_content;
	
	@ViewInject(R.id.main_radio)
	private RadioGroup main_radio;
	private List<BasePager> pagers;
	
	@Override
	public void onAttach(Activity activity) {
		try {
			mTabChangedListener = (TabChangedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement TabChangedListener");
		}
		super.onAttach(activity);
	}
	
	class MyPagerAdapter extends PagerAdapter{
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			Log.i(tag, "instantiateItem position = "+position);
			((MyViewPager)container).addView(pagers.get(position).getRootView());
			return pagers.get(position).getRootView();
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			Log.i(tag, "destroyItem position = "+position);
			((MyViewPager)container).removeView((View)object);
		}
		
		@Override
		public int getCount() {
			return pagers.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
	}

	@Override
	public View initView(LayoutInflater inflater) {
		view = inflater.inflate(R.layout.frag_home, null);
		mTabChangedListener.setSlidingMenuEnable(true);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		pagers = new ArrayList<BasePager>();
		//创建5个类去继承BasePager
		pagers.add(new WordCenterPager(getActivity()));
		pagers.add(new GrammarSakuraPager(getActivity()));
		pagers.add(new GrammarBiaoriPager(getActivity()));
		pagers.add(new GovAffairsPager(getActivity()));
		pagers.add(new SettingPager(getActivity()));
		
		layout_content.setAdapter(new MyPagerAdapter());
//		layout_content.set
		
		main_radio.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				
				switch (checkedId) {
				case R.id.rb_word_center://单词中心
					layout_content.setCurrentItem(0);
					GlobalParams.CURR_TAB_INDEX = 0;
					mTabChangedListener.changedListener(0);
					break;
				case R.id.rb_grammar_sakura://樱花日语版 语法
					layout_content.setCurrentItem(1);
					GlobalParams.CURR_TAB_INDEX = 1;
					mTabChangedListener.changedListener(1);
					break;
				case R.id.rb_grammar_biaori://标日版 语法
					layout_content.setCurrentItem(2);
					GlobalParams.CURR_TAB_INDEX = 2;
					mTabChangedListener.changedListener(2);
					break;
				case R.id.rb_gov_affairs://政务信息
					layout_content.setCurrentItem(3);
					GlobalParams.CURR_TAB_INDEX = 3;
					mTabChangedListener.changedListener(3);
					break;
				case R.id.rb_setting://设置
					layout_content.setCurrentItem(4);
					GlobalParams.CURR_TAB_INDEX = 4;
					mTabChangedListener.changedListener(4);
					break;
				}
			}
		});
		main_radio.check(R.id.rb_word_center);
		
		layout_content.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				//选中某页时去加载数据
				BasePager basePager = pagers.get(position);
				if(basePager.isFirstComeInThisPager)
				{
					basePager.initData();
				}else{
					basePager.intSlidingMenuList(position);
				}
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				
			}
		});
		BasePager basePager1 = pagers.get(0);
		basePager1.initData();
	}
	
	public WordCenterPager getNewCenterPager(){
		return (WordCenterPager) pagers.get(0);
	}
	public GrammarSakuraPager getSakuraPager(){
		return (GrammarSakuraPager) pagers.get(1);
	}
	
	/*@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if(isVisibleToUser){
			((MainActivity)getActivity()).getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		}else{
			((MainActivity)getActivity()).getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
	}*/

	@Override
	public void onHiddenChanged(boolean hidden) {
		if(hidden){
			((MainActivity)getActivity()).getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}else{
			((MainActivity)getActivity()).getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		}
	}
	
}
