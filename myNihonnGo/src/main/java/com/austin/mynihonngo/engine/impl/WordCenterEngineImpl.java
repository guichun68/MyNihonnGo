package com.austin.mynihonngo.engine.impl;

import com.austin.mynihonngo.base.BaseEngine;
import com.austin.mynihonngo.base.ConstantValue;
import com.austin.mynihonngo.base.GlobalParams;
import com.austin.mynihonngo.engine.IWordCenterEngine;
import com.austin.mynihonngo.engine.OnResultListener;
import com.lidroid.xutils.http.RequestParams;

public class WordCenterEngineImpl extends BaseEngine implements
		IWordCenterEngine {

	@Override
	public void getWordCenterData(OnResultListener listener, Boolean flag) {
		String url = GlobalParams.URL_SLIDING_MENU_WORD_CENTER;
		getServiceData(null, url, listener, flag);
	}

	@Override
	public void getNounClassifyItemData(RequestParams param,
			OnResultListener listener, Boolean flag) {
		/*String url = GlobalParams.URL_NOUNS;
		getServiceData(param, url, listener, flag);*/
	}

	@Override
	public void getVerbClassifyItemData(RequestParams param,
			OnResultListener listener, Boolean flag) {
		String url = GlobalParams.URL_VERBS;
		getServiceData(param, url, listener, flag);
	}

	@Override
	public void getClassifyItemData(String url,RequestParams param,
			OnResultListener onResultListener, boolean flag) {
		getServiceData(param, url, onResultListener, flag);
	}
}
