package com.austin.mynihonngo.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.austin.mynihonngo.MainActivity;
import com.austin.mynihonngo.R;
import com.austin.mynihonngo.base.BaseFragment;
import com.austin.mynihonngo.base.ConstantValue;
import com.austin.mynihonngo.base.GlobalParams;
import com.austin.mynihonngo.bean.User;
import com.austin.mynihonngo.engine.IUserEngine;
import com.austin.mynihonngo.engine.OnResultListener;
import com.austin.mynihonngo.utils.BeanFactoryUtil;
import com.austin.mynihonngo.utils.CacheUtil;
import com.austin.mynihonngo.utils.CustomProgressDialog;
import com.austin.mynihonngo.utils.MD5Utils;
import com.austin.mynihonngo.utils.NetworkUtil;
import com.austin.mynihonngo.utils.SharedPreferencesUtils;
import com.austin.mynihonngo.utils.StringUtil;
import com.austin.mynihonngo.utils.UIManager;
import com.austin.mynihonngo.utils.UIUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.LogUtils;

public class UserLogintFragment extends BaseFragment implements
		OnClickListener, OnResultListener {
	private static final String TAG = UserLogintFragment.class.getSimpleName();
	private TextView tvLogo;
	private Button btnLogin;
	private CheckBox chkAutoLogin;
	private EditText etLoginAccount;
	private EditText etLoginPwd;
	private TextView tvForgetPwd;
	private TextView tvHint;
	private Dialog progressDialog;
	
	Boolean autoLogin;
	private User user = null;
	private String lastLoginName = null;
	private long startTime;
	private String pwd;// 记录用户密码
	private String account;// 记录用户名

	/**
	 * 设置监听
	 */
	private void regListener() {
		tvLogo.setOnClickListener(this);
		btnLogin.setOnClickListener(this);
		tvForgetPwd.setOnClickListener(this);
		tvHint.setOnClickListener(this);
		chkAutoLogin.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
										 boolean isChecked) {
				if (isChecked) {
					SharedPreferencesUtils.putBoolean(
							SharedPreferencesUtils.AUTO_LOGIN, true);
				} else {
					SharedPreferencesUtils.putBoolean(
							SharedPreferencesUtils.AUTO_LOGIN, false);
				}
			}
		});
	}

	@Override
	public View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.frag_login, null);
		tvLogo = (TextView) view.findViewById(R.id.tvLogo);
		btnLogin = (Button) view.findViewById(R.id.btnLogin);// 登录按钮
		etLoginAccount = (EditText) view.findViewById(R.id.loginAccount);// 账号
		etLoginPwd = (EditText) view.findViewById(R.id.loginPwd);// 密码
		chkAutoLogin = (CheckBox) view.findViewById(R.id.cb_autologin);
		tvForgetPwd = (TextView) view.findViewById(R.id.tvForgetPwd);// 忘记密码
		progressDialog = CustomProgressDialog.getProgressDialogFramAni(context,UIUtil.getString(R.string.loading));
		tvHint = (TextView) view.findViewById(R.id.tvHint);
		//登录页面禁用侧滑
		((MainActivity)context).getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		// 给忘记密码加下划线
		tvForgetPwd.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		regListener();
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		
		autoLogin = SharedPreferencesUtils
				.getBoolean(SharedPreferencesUtils.AUTO_LOGIN);
		
		lastLoginName = SharedPreferencesUtils.getString(
				SharedPreferencesUtils.LAST_LOGIN_NAME, "");
		String psw = SharedPreferencesUtils.getString(SharedPreferencesUtils.USER_PSW, "");
		// 从数据库查找用户信息
		User user;
		try {
			if(!StringUtil.isEmpty(lastLoginName))
			{
				user = CacheUtil.findUser(lastLoginName);
				if(autoLogin && user!=null){
					//获取用户信息
					etLoginAccount.setText(user.getLoginName());
					etLoginPwd.setText(psw);
					doAutoLogin();
				}
			}
		}
		catch (DbException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tvLogo:
				//注册失败会抛出HyphenateException
				try {
					EMClient.getInstance().createAccount("rory", pwd);//同步方法
					Log.e(TAG,"注册成功");
				} catch (HyphenateException e) {
					Log.e(TAG,"注册失败");
					e.printStackTrace();
				}
				break;
			case R.id.btnLogin:

			// 记录开始登录时间
			startTime = System.currentTimeMillis();

			doManualLogin();
			break;
			case R.id.tvForgetPwd:
			// TODO 跳转到找回密码页面

			/*
			 * target = new UserRetrievePwdFragment();
			 * UIManager.getInstance().changeFragment(target, true, false,
			 * null); GlobalParams.currFragment = target;
			 */
			break;
			case R.id.tvHint:
			fiveClick();
			break;
		}
		

	}
	long[] mHits = new long[2];
	private void fiveClick() {
		System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
		mHits[mHits.length - 1] = SystemClock.uptimeMillis();
		if (mHits[0] >= (SystemClock.uptimeMillis() - 500)) {
			ipConfigDialog();
		}
	}

	private void ipConfigDialog() {
		AlertDialog.Builder adb = new AlertDialog.Builder(context);
		View view = View.inflate(context, R.layout.dialog_ipconfig, null);
		((TextView)view.findViewById(R.id.tv_curr_server)).setText("current server:"+GlobalParams.BASE_URL);
		
		final EditText etIpInput = (EditText) view.findViewById(R.id.et_input);
		Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!TextUtils.isEmpty(etIpInput.getText().toString())){
					GlobalParams.BASE_URL = "http://"+etIpInput.getText().toString();
					UIUtil.showToastSafe("已设置为:"+GlobalParams.BASE_URL);
					GlobalParams.refreshIP();
				}
			}
		});
		
		Button btnEclipseServer,btnGenymotion,btnFj;
		btnEclipseServer = (Button) view.findViewById(R.id.btn_localserver);
		btnGenymotion = (Button) view.findViewById(R.id.btn_test_server);
		btnFj = (Button) view.findViewById(R.id.btn_hbx_server);
		btnEclipseServer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				GlobalParams.BASE_URL = BeanFactoryUtil.properties.getProperty("BaseURL_eclipse");
				GlobalParams.refreshIP();
				UIUtil.showToastSafe("已设置为:"+GlobalParams.BASE_URL);
			}
		});
		btnGenymotion.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				GlobalParams.BASE_URL = BeanFactoryUtil.properties.getProperty("BaseURL_genymotion");
				GlobalParams.refreshIP();
				UIUtil.showToastSafe("已设置为:"+GlobalParams.BASE_URL);
			}
		});
		btnFj.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				GlobalParams.BASE_URL = BeanFactoryUtil.properties.getProperty("BaseURL_fjjsp");
				GlobalParams.refreshIP();
				UIUtil.showToastSafe("已设置为:"+GlobalParams.BASE_URL);
			}
		});
		final AlertDialog dialog = adb.create();
		dialog.setView(view, 0,0,0,0);
		dialog.show();
	}

	/**
	 * 手动登录方法，进行数据校验，然后调用登录接口进行登录操作
	 */
	private void doManualLogin() {
		// 获取用户输入的数据
		account = etLoginAccount.getText().toString();
		pwd = etLoginPwd.getText().toString();

		if(!verifyInput()){
			return;
		}
		doRemoteLogin(account, MD5Utils.encode(pwd));
		// 进行本地登录
	}

	private void doAutoLogin() {
		try{
			// 获取本地存储的自动登录标识
			if (autoLogin) {
				LogUtils.i("进行自动登录");

				// 记录开始登录时间
				startTime = System.currentTimeMillis();

				if (user != null && !StringUtil.isEmpty(lastLoginName)) {
					doRemoteLogin(lastLoginName, user.getPassword());
				} else {
					if(verifyInput()){
						doRemoteLogin(etLoginAccount.getText().toString(), MD5Utils.encode(etLoginPwd.getText().toString()));
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用网络登录
	 * 
	 * @param account
	 * @param pwd
	 *            加密之后的
	 */
	private void doRemoteLogin(String account, String pwd) {
		// LogUtils.i("进行远程登录");

		if (!NetworkUtil.checkNetwork(GlobalParams.MAIN)) {
			UIUtil.showToastSafe(R.string.hintCheckNet);
			return;
		}
		
		IUserEngine engine = BeanFactoryUtil.getImpl(IUserEngine.class);
		User user = new User();
		user.setLoginName(account);
		user.setPassword(pwd);
		this.pwd = pwd;
		this.account = account;
		progressDialog.show();
		engine.login(user, this, false);
	}

	/**
	 * 在登录成功，获取到用户信息后,做一些缓存工作
	 * 
	 * @param user
	 *            用户信息
	 * @param type
	 *            登录类型
	 */
	private void afterGetUserInfo(User user, String type) {
		SharedPreferencesUtils.putString(
				SharedPreferencesUtils.LAST_LOGIN_NAME, user.getLoginName());
		// 记录用户信息
		SharedPreferencesUtils.LAST_LOGIN_NAME = user.getLoginName();
		//缓存用户密码 以便自动登陆时用
		SharedPreferencesUtils.putString(SharedPreferencesUtils.USER_PSW, etLoginPwd.getText().toString());
		// 存到数据库
		try {
			CacheUtil.saveUser(user);
		} catch (DbException e) {
			UIUtil.showTestToast("保存用户异常");
			e.printStackTrace();
		}
		// 跳转到首页
		Fragment target = new HomeFragment();
		UIManager.getInstance().changeFragmentWithTag(target, false,
				null, ConstantValue.FRAG_TAG_HOME);
	}

	@Override
	public void onGetData(Object obj, int what) {
		CustomProgressDialog.dismissDialog(progressDialog);
		String result = (String) obj;
		if(result.toLowerCase().contains("html")){
			UIUtil.showToastSafe(UIUtil.getString(R.string.hintCheckNet));
			return;
		}
//		JSONObject parseObject = com.alibaba.fastjson.JSON.parseObject(result);
//		int code =  parseObject.getInteger("code");
		int code = 4;
		// 获取服务器返回的提示信息
		//String msg = (String) parseObject.get("msg");
		
//		if (ConstantValue.ACCCESS_SUCCESS==code) {// 登录成功
		if (ConstantValue.ACCCESS_SUCCESS==1) {// 登录成功

//			User user = parseObject.getObject("user", User.class);
			User user = new User();
			user.setLoginName("austin");
			afterGetUserInfo(user, ConstantValue.LOGIN_TYPE_REMOTE);

		} else {
			// 登录失败
			if (ConstantValue.ACCCESS_REFUSE==code) {
				UIUtil.showToastSafe(R.string.hintAccountOrPwdWrong);
			} else {
				UIUtil.showToastSafe(R.string.hintLoginFail);
			}
		}

	}

	@Override
	public void onFailure(String msg) {
		CustomProgressDialog.dismissDialog(progressDialog);
		if(msg != null)
		{
			onGetData(new String("ok"),3);
			return;
		}
		UIUtil.showToastSafe(R.string.hintLoginFail);
	}

	/**验证输入 (判空)
	 * @return
	 */
	private boolean verifyInput() {
		Animation shake = AnimationUtils.loadAnimation(getActivity(),
				R.anim.shake);
		// 获取用户输入的数据
		account = etLoginAccount.getText().toString();
		pwd = etLoginPwd.getText().toString();
		if (StringUtil.isEmpty(account)) {
			UIUtil.showToastSafe(R.string.hintEnterUserName);
			etLoginAccount.startAnimation(shake);
			return false;
		}
		if (StringUtil.isEmpty(pwd)) {
			UIUtil.showToastSafe(R.string.hintEnterPwd);
			etLoginPwd.startAnimation(shake);
			return false;
		}
		return true;
	}

}
