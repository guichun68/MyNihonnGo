package com.austin.mynihonngo.engine.impl;

import com.austin.mynihonngo.base.BaseEngine;
import com.austin.mynihonngo.base.GlobalParams;
import com.austin.mynihonngo.engine.ISakuraEngine;
import com.austin.mynihonngo.engine.OnResultListener;

public class SakuraEngine extends BaseEngine implements ISakuraEngine {

	@Override
	public void getSakuraData(OnResultListener listener, Boolean flag) {
		String url = GlobalParams.URL_SLIDING_MENU_SAKURA;
		getServiceData(null, url, listener, flag);
	}

}
