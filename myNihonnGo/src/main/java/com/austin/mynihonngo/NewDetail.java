package com.austin.mynihonngo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class NewDetail extends Activity {
	private WebView webView;
	private FrameLayout loading_view;
	
	public Context context;
	public View view;
	private Button btn_left;
	private ImageButton imgbtn_left;
	public SlidingMenu slidingMenu;
	public TextView txt_title;
	private ImageButton imgbtn_right;
	private ImageButton btn_right;
	private String url;
	private WebSettings settings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.act_news_detail);
		
		webView = (WebView) findViewById(R.id.news_detail_wv);
		loading_view = (FrameLayout) findViewById(R.id.loading_view);
		initTitle();
		
		if(getIntent()!=null){
			url = getIntent().getStringExtra("url");
		}
		settings = webView.getSettings();
		
		webView.setWebViewClient(new WebViewClient(){
			@Override
			public void onPageFinished(WebView view, String url) {
				loading_view.setVisibility(View.GONE);
				super.onPageFinished(view, url);
			}
		});
		
		webView.loadUrl(url);
	}

	private void initTitle() {
		btn_left = (Button)findViewById(R.id.btn_left);
		btn_left.setVisibility(View.GONE);
		
		imgbtn_left = (ImageButton) findViewById(R.id.imgbtn_left);
		imgbtn_left.setImageResource(R.mipmap.back);
		imgbtn_left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				NewDetail.this.finish();
			}
		});
		
		txt_title = (TextView) findViewById(R.id.txt_title);
	
		imgbtn_right = (ImageButton) findViewById(R.id.imgbtn_right);
		imgbtn_right.setImageResource(R.mipmap.icon_textsize);
		
		imgbtn_right.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//字体大小改变
				settings.setTextSize(TextSize.LARGEST);
			}
		});
		
		btn_right = (ImageButton) findViewById(R.id.btn_right);
		btn_right.setImageResource(R.mipmap.icon_share);
	}
}
