package com.austin.mynihonngo.pager;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.austin.mynihonngo.R;
import com.austin.mynihonngo.base.BasePager;
import com.austin.mynihonngo.base.GlobalParams;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class FunctionPager extends BasePager {
	
	private RelativeLayout title_bar;
	
	private FrameLayout frameLayout;//随着顶部标签改变而改变的底部页面
	
	public FunctionPager(Context context) {
		super(context);
	}

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.frame_function, null);
		title_bar = (RelativeLayout) view.findViewById(R.id.title_bar);
		frameLayout = (FrameLayout) view.findViewById(R.id.function_fl);
		//设置侧栏栏目menu布局中的title文字到具体的页面
		initTitleBar();//初始化标题栏
		
		
		TextView tv = new TextView(context);
		tv.setText("页面1");
		tv.setTextSize(30);
		frameLayout.addView(tv);
		
		
		return view;
		
		/*
		TextView textView = new TextView(context);
		textView.setText("首页");
		return textView;*/
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
	}

}
