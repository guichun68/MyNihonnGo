package com.austin.mynihonngo.base;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.austin.mynihonngo.utils.UIUtil;
import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.chat.adapter.EMAChatConfig;

/**
 * @Description TODO
 * @author 
 * @date 2014-7-24 下午4:58:49
 */
public class BaseApplication extends Application {
	private final String TAG = "BaseApplication";
	/** 全局Context，原理是因为Application类是应用最先运行的，所以在我们的代码调用时，该值已经被赋值过了 */
	private static Context mInstance;
	/** 主线程ID */
	private static int mMainThreadId = -1;
	/** 主线程ID */
	private static Thread mMainThread;
	/** 主线程Handler */
	private static Handler mMainThreadHandler;
	/** 主线程Looper */
	private static Looper mMainLooper;

	@Override
	public void onCreate() {
		mInstance = this;

		//初始化环信------------------------------------
		EMOptions options = new EMOptions();
		//默认添加好友时，是不需要验证的，改成需要验证
		options.setAcceptInvitationAlways(false);
		//初始化
		EMClient.getInstance().init(this, options);
		//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
		EMClient.getInstance().setDebugMode(true);
		//----------------------------------------------

		// System.out.println("---BaseApplication---onCreate()---run-------------------");
		// 异常捕获
		// CrashHandler crashHandler = CrashHandler.getInstance();
		// crashHandler.init(getApplicationContext());

		mMainThreadId = android.os.Process.myTid();
		mMainThread = Thread.currentThread();
		mMainThreadHandler = new Handler();
		mMainLooper = getMainLooper();


		
		CrashHandler handler = CrashHandler.getInstance();
        handler.init(getApplicationContext()); //在Appliction里面设置我们的异常处理器为UncaughtExceptionHandler处理器
		// 配置友盟统计：禁止默认的Activity页面统计方式
		// MobclickAgent.openActivityDurationTrack(false);

		// 开启云巴
//		initYunba();

		// TODO 结束云巴？

		// 初始化讯飞语音
		// SpeechUtility.createUtility(this, SpeechConstant.APPID +
		// "=55754bf6");// 
//		SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5635bf63");// 通过的

		// 配置LeanClound
		// 如果使用美国节点，请加上这行代码 AVOSCloud.useAVCloudUS();
//		AVOSCloud.initialize(this, "WThhh79s3mEDeeCLO3yWGmjA",
//				"18a6lYNUYKE4qVIHxoxl8sBF");

		super.onCreate();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	public static Context getApplication() {
		return mInstance;
	}

	/** 获取主线程ID */
	public static int getMainThreadId() {
		return mMainThreadId;
	}

	/** 获取主线程 */
	public static Thread getMainThread() {
		return mMainThread;
	}

	/** 获取主线程的handler */
	public static Handler getMainThreadHandler() {
		return mMainThreadHandler;
	}

	/** 获取主线程的looper */
	public static Looper getMainThreadLooper() {
		return mMainLooper;
	}

}
