package com.austin.mynihonngo.engine;

import com.austin.mynihonngo.bean.User;

public interface IUserEngine {
	/**
	 * 用户注册的方法
	 * 
	 * @param user
	 *            封装用户信息的bean
	 * @param listener
	 *            回调的接口
	 * @param flag
	 *            是否显示进度框
	 */
	void regist(User user, OnResultListener listener, Boolean flag);

	/**
	 * 用户登录的方法
	 * 
	 * @param user
	 * @param listener
	 * @param flag
	 */
	void login(User user, OnResultListener listener, Boolean flag);

	/**
	 * 找回用户密码（获取验证码）
	 * 
	 * @param loginName
	 * @param listener
	 * @param flag
	 */
	void findPasswordVerificationCode(String loginName,
									  OnResultListener listener, Boolean flag);

	/**
	 * 修改用户密码
	 * 
	 * @param userId
	 * @param beginTime
	 * @param password
	 * @param listener
	 * @param flag
	 */
	void updateUserPassword(String userId, String beginTime, String password,
							OnResultListener listener, Boolean flag);

	/**
	 * 新增用户GPS信息
	 * 
	 * @param deviceName
	 *            车牌号
	 * @param deviceId
	 *            设备标识
	 * @param userId
	 *            用户标识
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param listener
	 * @param flag
	 */
	void addUserGpsInfo(String deviceName, String deviceId, Integer userId,
						String longitude, String latitude, OnResultListener listener,
						Boolean flag);

	/**
	 * 修改用户信息,用户信息(userId必有)，其他信息动态，有则修改，无则不改
	 * 
	 * @param params
	 *            User转换成的json字符串
	 * @param listener
	 * @param flag
	 */
	void updateUser(String params, OnResultListener listener, Boolean flag);

	/**
	 * 更改密码
	 * 
	 * @param userId
	 *            用户标识
	 * @param initPwd
	 *            原始密码
	 * @param newPwd
	 *            新密码
	 * @param listener
	 * @param flag
	 */
	void changeUserPassword(Integer userId, String initPwd, String newPwd,
							OnResultListener listener, Boolean flag);

	/**
	 * 新增或修改用户与第三方绑定关系
	 * 
	 * @param userId
	 *            用户标识(必填)
	 * @param openId
	 *            第三方标识(必填)
	 * @param bindingType
	 *            绑定类型 2:个推出租车 4:个推私家车
	 * @param listener
	 * @param flag
	 */
	void addOrUpdUserBinding(Integer userId, String openId, String bindingType,
							 OnResultListener listener, Boolean flag);

}
