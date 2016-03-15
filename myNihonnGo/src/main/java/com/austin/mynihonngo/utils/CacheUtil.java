package com.austin.mynihonngo.utils;

import com.austin.mynihonngo.base.GlobalParams;
import com.austin.mynihonngo.bean.User;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

/**
 * @Description 数据库操作的工具类
 * @author 
 * @date 2015-7-2 下午4:39:05
 */
public class CacheUtil {

//	public static DbUtils db = DbUtils.create(UIUtil.getContext(),
//			FileUtils.getDir(GlobalParams.MAIN, ""), "nihonngo.db");
//	db = DbUtils.
	public static DbUtils db = DbUtils.create(UIUtil.getContext());

	/**
	 * 根据登录名查找用户信息
	 * 
	 * @param loginName
	 * @return
	 * @throws DbException
	 */
	public static User findUser(String loginName) throws DbException {
		return db.findFirst(Selector.from(User.class).where("loginName", "=",
				loginName));
	}

	public static User findLastLoginUser() {
		String loginName = SharedPreferencesUtils.getString(
				SharedPreferencesUtils.LAST_LOGIN_NAME, "");

		if (StringUtil.isEmpty(loginName))
			return null;

		try {
			return db.findFirst(Selector.from(User.class).where("loginName",
					"=", loginName));
		} catch (DbException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 保存用户信息
	 * 
	 * @param user
	 * @throws DbException
	 */
	public static void saveUser(User user) throws DbException {
		db.save(user);
	}

	/**
	 * 更新数据库中用户的密码
	 * 
	 * @param account
	 * @param pwd
	 * @throws DbException
	 */
	public static void updatePwd(String account, String pwd) throws DbException {
		User user = db.findFirst(Selector.from(User.class).where("loginName",
				"=", account));
		if (user != null) {
			// LogUtils.i("用户的新密码为："+ pwd);
			user.setPassword(pwd);
			db.update(user, "password");
		}

	}

}
