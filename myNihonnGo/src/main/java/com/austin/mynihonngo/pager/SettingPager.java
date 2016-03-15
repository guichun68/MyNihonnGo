package com.austin.mynihonngo.pager;

import android.content.Context;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.austin.mynihonngo.R;
import com.austin.mynihonngo.base.BasePager;
import com.austin.mynihonngo.base.ConstantValue;
import com.austin.mynihonngo.base.GlobalParams;
import com.austin.mynihonngo.utils.UIUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class SettingPager extends BasePager {

	private RelativeLayout title_bar;
	
	private FrameLayout frameLayout;//随着顶部标签改变而改变的底部页面
	
	public SettingPager(Context context) {
		super(context);
	}

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.frame_settings, null);
		title_bar = (RelativeLayout) view.findViewById(R.id.title_bar);
		frameLayout = (FrameLayout) view.findViewById(R.id.setting_fl);
		//设置侧栏栏目menu布局中的title文字到具体的页面
		initTitleBar();//初始化标题栏
		
		/*TextView tv = new TextView(context);
		tv.setText("页面5");
		tv.setTextSize(30);*/
		Button btn = new Button(context);
		btn.setText("设置为10.0.2.2");
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				GlobalParams.BASE_URL = "http://10.0.2.2";
				UIUtil.showToastSafe("已设置为10.0.2.2");
			}
		});
		frameLayout.addView(btn);
		
		return view;
	}

	@Override
	public void initData() {

	}

}
