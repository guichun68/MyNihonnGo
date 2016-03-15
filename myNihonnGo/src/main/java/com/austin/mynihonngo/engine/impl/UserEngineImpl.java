package com.austin.mynihonngo.engine.impl;

import com.austin.mynihonngo.base.BaseEngine;
import com.austin.mynihonngo.base.ConstantValue;
import com.austin.mynihonngo.base.GlobalParams;
import com.austin.mynihonngo.bean.User;
import com.austin.mynihonngo.engine.IUserEngine;
import com.austin.mynihonngo.engine.OnResultListener;
import com.austin.mynihonngo.utils.MD5Utils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.util.LogUtils;

public class UserEngineImpl extends BaseEngine implements IUserEngine {

	@Override
	public void regist(User user, OnResultListener listener, Boolean flag) {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("loginName", user.getLoginName());
		// 将密码进行MD5加密
		String password = MD5Utils.encode(user.getPassword());
		// System.out.println("password="+password);
		params.addQueryStringParameter("password", password);
		String url = GlobalParams.URL_REGIST + "/airbest/userAction/register";
		getServiceDataUsePostFastjson(params, url, listener, flag, String.class);
	}

	@Override
	public void login(User user, OnResultListener listener, Boolean flag) {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("username", user.getLoginName());
		params.addQueryStringParameter("password", user.getPassword());
		LogUtils.i("password=" + user.getPassword());
		String url = GlobalParams.URL_LOGIN;
		getServiceDataUsePostFastjson(params, url, listener, flag, String.class);
	}

	@Override
	public void findPasswordVerificationCode(String loginName,
			OnResultListener listener, Boolean flag) {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("loginName", loginName);
		String url = GlobalParams.URL_LOGIN
				+ "/airbest/userAction/findPasswordVerificationCode";
		getServiceData2(params, url, listener, flag, String.class);
	}

	@Override
	public void updateUserPassword(String userId, String beginTime,
			String password, OnResultListener listener, Boolean flag) {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("userId", userId);
		params.addQueryStringParameter("beginTime", beginTime);
		// 将密码进行MD5加密
		String pwd = MD5Utils.encode(password);
		params.addQueryStringParameter("password", pwd);
		String url = GlobalParams.URL_LOGIN
				+ "/airbest/userAction/updateUserPassword";
		getServiceDataUsePostFastjson(params, url, listener, flag, String.class);
	}

	@Override
	public void updateUser(String params, OnResultListener listener,
			Boolean flag) {
		RequestParams params2 = new RequestParams();
		params2.addQueryStringParameter("params", params);
		String url = GlobalParams.URL_NULL
				+ "/airbest/userAction/updateUser";
		getServiceDataUsePostFastjson(params2, url, listener, flag,
				String.class);
	}

	@Override
	public void changeUserPassword(Integer userId, String initPwd,
			String newPwd, OnResultListener listener, Boolean flag) {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("userId", String.valueOf(userId));
		params.addQueryStringParameter("initPwd", MD5Utils.encode(initPwd));
		params.addQueryStringParameter("newPwd", MD5Utils.encode(newPwd));
		String url = GlobalParams.URL_NULL
				+ "/airbest/userAction/changeUserPassword";
		getServiceDataUsePostFastjson(params, url, listener, flag, String.class);
	}

	@Override
	public void addOrUpdUserBinding(Integer userId, String openId,
			String bindingType, OnResultListener listener, Boolean flag) {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("userId", String.valueOf(userId));
		params.addQueryStringParameter("openId", openId);
		params.addQueryStringParameter("bindingType", bindingType);
		// osType:操作系统类型（必填，android:安卓；ios:苹果）
		params.addQueryStringParameter("osType", "android");
		String url = GlobalParams.URL_NULL
				+ "/airbest/userAction/addOrUpdUserBinding";
		getServiceDataUsePostFastjson(params, url, listener, flag, String.class);
	}

	@Override
	public void addUserGpsInfo(String deviceName, String deviceId,
			Integer userId, String longitude, String latitude,
			OnResultListener listener, Boolean flag) {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("deviceName", deviceName);
		params.addQueryStringParameter("deviceId", deviceId);
		params.addQueryStringParameter("userId", String.valueOf(userId));
		params.addQueryStringParameter("longitude", longitude);
		params.addQueryStringParameter("latitude", latitude);
		String url = GlobalParams.URL_NULL
				+ "/airbest/userAction/addUserGpsInfo";
		getServiceDataUsePostFastjson(params, url, listener, flag, String.class);
	}

}
