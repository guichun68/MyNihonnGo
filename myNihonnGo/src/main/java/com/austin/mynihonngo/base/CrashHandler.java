package com.austin.mynihonngo.base;

import java.lang.Thread.UncaughtExceptionHandler;

import com.austin.mynihonngo.utils.CustomProgressDialog;
import com.austin.mynihonngo.utils.UIUtil;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class CrashHandler implements UncaughtExceptionHandler {

    private static CrashHandler instance;  //单例引用，这里我们做成单例的，因为我们一个应用程序里面只需要一个UncaughtExceptionHandler实例
   
    private CrashHandler(){}
   
    public synchronized static CrashHandler getInstance(){  //同步方法，以免单例多线程环境下出现异常
        if (instance == null){
            instance = new CrashHandler();
        }
        return instance;
    }
   
    public void init(Context ctx){  //初始化，把当前对象设置成UncaughtExceptionHandler处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }
   
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {  //当有未处理的异常发生时，就会来到这里。。
    	
    	CustomProgressDialog.getPromptDialog(UIUtil.getContext(), "抱歉,程序发生异常,将重新启动",new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			    Intent i = UIUtil.getContext().getPackageManager()  
			            .getLaunchIntentForPackage(UIUtil.getContext().getPackageName());  
			    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			    UIUtil.getContext().startActivity(i);  
			}
		});
    	
        Log.d("Sandy", "uncaughtException, thread: " + thread
                + " name: " + thread.getName() + " id: " + thread.getId() + "exception: "
                + ex);
        ex.printStackTrace();
         String threadName = thread.getName();
         if ("sub1".equals(threadName)){
               Log.d("Sandy", "xxx");
         }/*else if(){
               //这里我们可以根据thread name来进行区别对待，同时，我们还可以把异常信息写入文件，以供后来分析。
          }*/
    }

}