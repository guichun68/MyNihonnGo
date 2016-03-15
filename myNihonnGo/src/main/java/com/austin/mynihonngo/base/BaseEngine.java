package com.austin.mynihonngo.base;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import android.app.Dialog;
import android.os.AsyncTask;

import com.alibaba.fastjson.JSON;
import com.austin.mynihonngo.MainActivity;
import com.austin.mynihonngo.R;
import com.austin.mynihonngo.engine.OnResultListener;
import com.austin.mynihonngo.utils.CustomProgressDialog;
import com.austin.mynihonngo.utils.HttpUtil;
import com.austin.mynihonngo.utils.NetworkUtil;
import com.austin.mynihonngo.utils.UIUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * 服务器交互抽象类
 * 
 * @param <T>
 *            Gson要转换成的bean的类
 */
public abstract class BaseEngine<T> {
	protected static final String TAG = BaseEngine.class.getSimpleName();
	private static final int TIMEOUT = 10 * 1000;
	private Dialog dialog;
	/**
	 * 获取验证码专用
	 * 
	 * @param params
	 *            参数
	 * @param url
	 *            地址
	 * @param listener
	 *            回调函数
	 * @param flag
	 *            是否弹出框
	 * @param clazz
	 *            要转化成的bean的类
	 */
	public void getServiceData2(RequestParams params, String url,
			final OnResultListener listener, final boolean flag,
			final Class<T> clazz) {
		if (!NetworkUtil.checkNetwork(GlobalParams.MAIN)) {
			// PromptUtil.showNoNetWork(GlobalParams.MAIN);
			UIUtil.showToastSafe(R.string.hintCheckNet);
		} else {
			HttpUtils http = new HttpUtils();
			// 设置超时时间为60秒
			http.configTimeout(60000);
			// 设置缓存
			http.configCurrentHttpCacheExpiry(0);
			http.send(HttpRequest.HttpMethod.GET, url, params,
					new RequestCallBack<String>() {
						@Override
						public void onStart() {
							if (flag) {
								dialog = CustomProgressDialog.getProgressDialogFramAni(GlobalParams.MAIN, "请稍后");
							}
						}

						@Override
						public void onLoading(long total, long current,
								boolean isUploading) {
						}

						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							closeDialog();

							String result = responseInfo.result;
							// Log.i(TAG, "result=" + result);
							if (result.length() > 0) {
								listener.onGetData(result, 0);
							}
						}

						@Override
						public void onFailure(HttpException error, String msg) {
							closeDialog();
							// Toast.makeText(GlobalParams.MAIN, "服务器繁忙，稍后请重试！",
							// 0).show();
							UIUtil.showTestToast(msg);
							listener.onFailure(msg);
						}
					});
		}
	}
	/**
	 * 获取验证码专用
	 * 
	 * @param params
	 *            参数
	 * @param url
	 *            地址
	 * @param listener
	 *            回调函数
	 * @param flag
	 *            是否弹出框
	 */
	public void getServiceData(RequestParams params, String url,
			final OnResultListener listener, final boolean flag) {
		if (!NetworkUtil.checkNetwork(UIUtil.getContext())) {
//			UIUtil.showToastSafe(R.string.hintCheckNet);
			listener.onFailure(UIUtil.getString(R.string.hintCheckNet));
		} else {
			HttpUtils http = new HttpUtils();
			// 设置超时时间为60秒
			http.configTimeout(60000);
			// 设置缓存
			http.configCurrentHttpCacheExpiry(0);
			http.send(HttpRequest.HttpMethod.POST, url, params,
					new RequestCallBack<String>() {
				@Override
				public void onStart() {
					if (flag) {
						dialog = CustomProgressDialog.getProgressDialogFramAni(GlobalParams.MAIN, UIUtil.getString(R.string.loading));
						dialog.show();
					}
				}
				
				@Override
				public void onLoading(long total, long current,
						boolean isUploading) {
				}
				
				@Override
				public void onSuccess(ResponseInfo<String> responseInfo) {
					closeDialog();
					
					String result = responseInfo.result;
					if (result.length() > 0) {
						listener.onGetData(result, 0);
					}
				}
				
				@Override
				public void onFailure(HttpException error, String msg) {
					closeDialog();
					listener.onFailure(msg);
				}
			});
		}
	}

	public void getServiceDataUsePostFastjson(RequestParams params,
			final String url, final OnResultListener listener,
			final boolean flag, final Class<T> clazz) {
		// TODO 测试调取接口所用时间
		// final long start = System.currentTimeMillis();

		if (!NetworkUtil.checkNetwork(GlobalParams.MAIN)) {
			// PromptUtil.showNoNetWork(GlobalParams.MAIN);
			UIUtil.showToastSafe(R.string.hintCheckNet);
			listener.onFailure("无网络");
		} else {
			HttpUtils http = new HttpUtils();
			// 设置超时时间
			http.configTimeout(TIMEOUT);
			// 设置缓存
			http.configCurrentHttpCacheExpiry(1000 * 10);
			http.send(HttpRequest.HttpMethod.POST, url, params,
					new RequestCallBack<String>() {
						@Override
						public void onStart() {
							if (flag) {
								CustomProgressDialog.getProgressDialogFramAni(GlobalParams.MAIN, UIUtil.getString(R.string.loading));
							}
						}

						@Override
						public void onLoading(long total, long current,
								boolean isUploading) {
						}

						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							// long end = System.currentTimeMillis();
							// Log.i(TAG, "调取接口所用时间=" + (end - start) / 1000 +
							// "秒");

							// 关闭提示窗口
							closeDialog();
							String result = responseInfo.result;

							// Log.i(TAG, "时间=" + DateUtil.getDateTime(new
							// Date(end)));
							// LogUtils.i("url=" + url);
							// LogUtils.i("result=" + result);
							if (result.length() > 0) {
								listener.onGetData(result, 0);
							}
						}

						@Override
						public void onFailure(HttpException error, String msg) {
							// long end = System.currentTimeMillis();
							// Log.i(TAG, "调取接口所用时间=" + (end - start) / 1000 +
							// "秒");

							closeDialog();

							UIUtil.showTestToast(msg);

							listener.onFailure(msg);

							// 云巴记录
//							LeanCloudUtil.recordErrMsg(url, msg);
						}
					});
		}

	}
	
	/**使用AsyncTask通用请求网络方法
	 * @param params 参数
	 * @param url 地址
	 * @param listener 回调函数
	 * @param flag 是否弹出框
	 * @param clazz 要转化成的bean的类
	 */
	public void getServiceDataUseGetFastjson(final Map<String,String> params,
			final String url, final OnResultListener listener,
			final boolean flag, final Class clazz) {
		
		if (!NetworkUtil.checkNetwork(GlobalParams.MAIN)) {
			UIUtil.showToastSafe(R.string.hintCheckNet);
			listener.onFailure("无网络");
		} else {
			final AsyncTask<String, Integer, String> task = new AsyncTask<String, Integer, String>(){
				
				@Override
				protected void onPreExecute() {
					if (flag) {
						CustomProgressDialog.getProgressDialog2(GlobalParams.MAIN, "请稍后...").show();
					}
				}
				
				@Override
				protected void onProgressUpdate(Integer... values) {
					super.onProgressUpdate(values);
				}
				
				@Override
				protected String doInBackground(String... params3) {
					HttpUtil httpUtils = new HttpUtil(null, null);
					String requestResult = httpUtils.getRequest(params3[0], params);
					return requestResult;
				}
				
				@Override
				protected void onPostExecute(String result) {
					Object obj;
					// 关闭提示窗口
					CustomProgressDialog.dismissDialog(CustomProgressDialog.progressDialog);
					if (result != null && result.length() > 0) {
						if(clazz == null)
							listener.onGetData(result, 0);
						else 
							listener.onGetData(JSON.parseObject(result,clazz), 0);
					}else{
						listener.onFailure("未能获取数据");
					}
				}
			}.execute(url);
			
			new Thread(){
				@Override
				public void run() {
					try{
						task.get(23000, TimeUnit.MILLISECONDS);
					}catch(Exception e) {  
					    task.cancel(true);         
					   listener.onFailure("请求超时");
					   CustomProgressDialog.dismissDialog(CustomProgressDialog.progressDialog);
					}
				}
			}.start();
		}
	}
	public void closeDialog() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}
}
