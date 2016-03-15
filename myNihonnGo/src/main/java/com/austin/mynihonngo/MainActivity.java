package com.austin.mynihonngo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Window;

import com.austin.mynihonngo.base.ConstantValue;
import com.austin.mynihonngo.base.GlobalParams;
import com.austin.mynihonngo.engine.TabChangedListener;
import com.austin.mynihonngo.fragment.HomeFragment;
import com.austin.mynihonngo.fragment.MenuFragment;
import com.austin.mynihonngo.fragment.UserLogintFragment;
import com.austin.mynihonngo.utils.SharedPreferencesUtils;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity implements TabChangedListener{
	private SlidingMenu slidingMenu;
	private static MainActivity mForegroundActivity;
	MenuFragment menuFragment;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mForegroundActivity = this;
		
		GlobalParams.MAIN = this;
		setContentView(R.layout.content);
		setBehindContentView(R.layout.frame_menu);
		
		slidingMenu = getSlidingMenu();
//		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);// 禁用slidingMenu（侧边栏）
		slidingMenu.setMode(SlidingMenu.LEFT);
		//设置内容页的显示距离
		slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		//jji
		//
		slidingMenu.setShadowDrawable(R.drawable.shadow);
		//当前倒影的宽度
		slidingMenu.setShadowWidthRes(R.dimen.shadow_width);
		
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
 
		
		menuFragment = new MenuFragment(); 
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu, menuFragment, ConstantValue.FRAG_TAG_MENU)
		.commit();
		
		if(verifyLogin()){
			Fragment homeFrg = getSupportFragmentManager().findFragmentByTag(ConstantValue.FRAG_TAG_HOME);
			homeFrg = homeFrg==null?new HomeFragment():homeFrg;
			getSupportFragmentManager()
			.beginTransaction()
			.replace(R.id.content_frame, homeFrg, ConstantValue.FRAG_TAG_HOME)
			.commit();
		}else{
			Fragment loginFrag = new UserLogintFragment();
			getSupportFragmentManager()
			.beginTransaction()
			.replace(R.id.content_frame, loginFrag,ConstantValue.FRAG_TAG_LOGIN)
			.commit();
		}
		
	}

	/**初始化登录，如果本地sp已记录登录信息return true
	 * @return true:成功 false：失败
	 */
	private boolean verifyLogin() {
		String loginInfo = SharedPreferencesUtils.getStringData(this, "LoginInfo", "");
		return !TextUtils.isEmpty(loginInfo);
	}

	public MenuFragment getMenuFragment() {
		return (MenuFragment) getSupportFragmentManager().findFragmentByTag(ConstantValue.FRAG_TAG_MENU);
	}

	public HomeFragment getHomeFragment() {
		return (HomeFragment) getSupportFragmentManager().findFragmentByTag(ConstantValue.FRAG_TAG_HOME);
	}
	public static MainActivity getForegroundActivity() {
		return mForegroundActivity;
	}

	@Override
	public void changedListener(int tabIndex) {
		menuFragment.setDefaultMenu();
	}

	@Override
	public void setSlidingMenuEnable(boolean enable) {
		// TODO Auto-generated method stub
		if(enable){
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);//启用侧边栏
		}else{
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);// 禁用slidingMenu（侧边栏）
		}
		
	}
	
	
}
