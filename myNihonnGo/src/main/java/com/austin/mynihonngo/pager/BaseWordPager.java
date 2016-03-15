package com.austin.mynihonngo.pager;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.austin.mynihonngo.R;
import com.austin.mynihonngo.base.BasePager;
import com.austin.mynihonngo.bean.WordType.Children;
import com.austin.mynihonngo.engine.IPageChangeListener;
import com.austin.mynihonngo.view.ViewPagerIndicator;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public abstract class BaseWordPager extends BasePager {

	/**
	 * 页面中的各个子页数据 内容
	 */
	public List<Children> childCliassfy;
	/**
	 * 各个子页UI（如动词1类、动词2类 ……）,在initDataBase方法中add进了页面
	 */
	public List<BasePager> pagers = new ArrayList<BasePager>();
	/**
	 * 每个子页标题集合
	 */
	public List<String> titleList = new ArrayList<String>();
	private MyPagerAdapter pagerAdapter;
	
	public ViewPagerIndicator indicator;
	
	private ViewPager viewpager;
	
	/**
	 * @param context 
	 * @param childCliassfy 
	 * @param layoutResourceId 布局文件;<br/><font color='RED'>注意：给定的布局文件中的ViewPager的id必须为pager<br/>
	 * 			viewpagerInicator的id必须为id_indicator</font>
	 */
	public BaseWordPager(Context context,List<Children> childCliassfy,int layoutResourceId) {
		super(context,layoutResourceId);
		this.childCliassfy = childCliassfy;
	}
	
	@Override
	public View initView() {
		view = View.inflate(context, layoutResourceId, null);
		viewpager = (ViewPager) view.findViewById(R.id.pager);
		indicator = (ViewPagerIndicator) view.findViewById(R.id.id_indicator);
		return view;
	}

	@Override
	public void initData() {
		titleList.clear();
        initDataBase();

		indicator.setTabItemTitles(titleList);
		if(pagerAdapter == null){
			pagerAdapter = new MyPagerAdapter();
		}
		viewpager.setAdapter(pagerAdapter);
		indicator.setOnPageChangeListener(new IPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				//在滑动的过程中需要去加载pagers集合中，NewItemPager对象的数据
				pagers.get(position).initData();
				if(position == 0){
					slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
				}else{
					slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
				}
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub
			}
		});
		//指针关联ViewPager的操作
		indicator.setViewPager(viewpager,0);
		pagers.get(0).initData();
	}
	
	public abstract void initDataBase();

	class MyPagerAdapter extends PagerAdapter{
		//让绑定当前ViewPager指针显示文字
		public CharSequence getPageTitle(int position) {
			return titleList.get(position);
		};
		
		@Override
		public int getCount() {
			return childCliassfy.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			//显示的内容
			((ViewPager)container).addView(pagers.get(position).getRootView());
			return pagers.get(position).getRootView();
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager)container).removeView((View)object);
		}
	}
	public void setIndicatorCount(List<String> titls){
		indicator.setTabItemTitles(titls);
	}
}
