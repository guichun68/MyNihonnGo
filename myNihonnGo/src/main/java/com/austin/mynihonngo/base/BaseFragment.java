package com.austin.mynihonngo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.austin.mynihonngo.MainActivity;
import com.austin.mynihonngo.engine.TabChangedListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public abstract class BaseFragment extends Fragment {
	public Context context;
	public View view;
	public SlidingMenu slidingMenu;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = getActivity();
		slidingMenu = ((MainActivity)context).getSlidingMenu();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = initView(inflater);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData(savedInstanceState);
	}
	
	public abstract View initView(LayoutInflater inflater) ;
	public abstract void initData(Bundle savedInstanceState) ;
}
