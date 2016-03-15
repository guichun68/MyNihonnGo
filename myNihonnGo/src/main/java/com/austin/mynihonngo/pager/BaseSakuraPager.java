package com.austin.mynihonngo.pager;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.austin.mynihonngo.R;
import com.austin.mynihonngo.base.BasePager;
import com.austin.mynihonngo.base.ConstantValue;
import com.austin.mynihonngo.base.GlobalParams;
import com.austin.mynihonngo.base.NHGBaseAdapter;
import com.austin.mynihonngo.bean.SakuraResult;
import com.austin.mynihonngo.bean.WordResult;
import com.austin.mynihonngo.bean.WordType.Children;
import com.austin.mynihonngo.engine.IFragmentListener;
import com.austin.mynihonngo.engine.IWordCenterEngine;
import com.austin.mynihonngo.engine.OnResultListener;
import com.austin.mynihonngo.utils.BeanFactoryUtil;
import com.austin.mynihonngo.utils.SharedPreferencesUtils;
import com.austin.mynihonngo.utils.StringUtil;
import com.austin.mynihonngo.utils.UIUtil;
import com.austin.mynihonngo.view.swipyrefreshlayout.SwipyRefreshLayout;
import com.austin.mynihonngo.view.swipyrefreshlayout.SwipyRefreshLayout.OnRefreshListener;
import com.austin.mynihonngo.view.swipyrefreshlayout.SwipyRefreshLayoutDirection;
import com.google.gson.Gson;
import com.lidroid.xutils.http.RequestParams;

public class BaseSakuraPager extends BasePager implements OnRefreshListener {

	protected static final String TAG = BaseSakuraPager.class.getSimpleName();
	protected static final String IDS = "ids";
	
	/**
	 * 上拉加载，下拉刷新组件
	 */
	private SwipyRefreshLayout mSwipeRefreshLayout;

	private ListView mListView;
	
	/**
	 * 适配器所需数据
	 */
	private List<SakuraResult> mSakuraResultList = new ArrayList<SakuraResult>();

	
	private int mLevel;//当前页面的级别
	private int mClassNo;//课时
	/**
	 * 分页，默认第一页
	 */
	private Integer mPageNo = 1;
	private MyAdapter mAdapter;
	private IFragmentListener mFragListener;
	private String url;

	/**
	 * @param context
	 * @param classItemId 对应大类的栏目分类 号
	 * @param layoutResourceId 布局文件id，<br/><font color='RED'>
	 * 			注意：布局中的SwipeRefreshLayout控件的id必须为swipyrefreshlayout;
	 *			布局中的ListView id必须为lv_one_type;
	 * </font>
	 * @param url: 接口地址，从GlobalParam.URL_...取值，以便告知接口你要获取何种词性的单词
	 */
	public BaseSakuraPager(Context context,int levelID,int classNo,int layoutResourceId,String url) {
		super(context,layoutResourceId);
		mLevel = levelID;
		mClassNo = classNo;
		mFragListener = (IFragmentListener) this;
		this.url = url;
	}

	@Override
	public View initView() {
		view = View.inflate(context,layoutResourceId, null);
		mSwipeRefreshLayout = (SwipyRefreshLayout) view.findViewById(R.id.swipyrefreshlayout);
		mListView = (ListView) view.findViewById(R.id.lv_one_type);
		//监听下拉和上拉操作
		mSwipeRefreshLayout.setOnRefreshListener(this);
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				UIUtil.showToastSafe("clicked:"+position);
				SakuraResult sr = (SakuraResult) parent.getItemAtPosition(position);
				Bundle bundle = new Bundle();
				bundle.putSerializable("sentence", sr);
				mFragListener.openFragment(BaseSakuraPager.this, ConstantValue.FRAG_TAG_SENTENCE_DETAIL,bundle);
				/*SakuraResult wr = (SakuraResult) parent.getAdapter().getItem(position);
				Bundle bundle = new Bundle();
				bundle.putSerializable("word", wr);
				mFragListener.openFragment(BaseSakuraPager.this,ConstantValue.FRAG_TAG_WORD_DETAIL, bundle);*/
			}

		});
		
		return view;
	}

	@Override
	public void initData() {
		getWordItemData();
	}
	
	
	class MyAdapter extends NHGBaseAdapter<SakuraResult, ListView>{

		public MyAdapter(Context context, List<SakuraResult> list, View Q) {
			super(context, list, Q);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder vh;
			if(convertView == null){
				convertView = View.inflate(context, R.layout.item_sentence, null);
				vh = new ViewHolder(convertView);
				convertView.setTag(vh);
			}else{
				vh = (ViewHolder) convertView.getTag();
			}
			if(position%2!=0){
				vh.root.setBackgroundColor(context.getResources().getColor(R.color.word_item_gray));
			}else{
				vh.root.setBackgroundColor(context.getResources().getColor(R.color.white));
			}
			vh.tvNo.setText(position+1+"");//序号
			vh.tvSentence.setText(getItem(position).getSkrSentenceCn());
			return convertView;
		}
		
		class ViewHolder{
			public final View root;
			public final TextView tvSentence;
			/**
			 * 句子序号
			 */
			public final TextView tvNo;
			public ViewHolder(View root) {
				super();
				this.tvSentence = (TextView) root.findViewById(R.id.tv_sentence);
				tvNo = (TextView) root.findViewById(R.id.tv_no);
				this.root = root;
			}
			
		}
	}


	@Override
	public void onRefresh(SwipyRefreshLayoutDirection direction) {
		dismissSwipeLoading();
	}

	private void getWordItemData() {

		RequestParams param = new RequestParams();
		param.addBodyParameter("level", mLevel+"");
		
		param.addBodyParameter("pageNo", mPageNo+"");
		param.addBodyParameter("classNo", mClassNo+"");
		IWordCenterEngine engine = BeanFactoryUtil.getImpl(IWordCenterEngine.class);
		engine.getClassifyItemData(url,param,new OnResultListener() {
			
			@Override
			public void onGetData(Object obj, int what) {
				processData((String)obj,true);
				dismissSwipeLoading();
			}
			
			@Override
			public void onFailure(String msg) {
				UIUtil.showToastSafe(msg);
				dismissSwipeLoading();
			}
		}, true);

	}
	
	private void processData(String result,boolean isRefresh) {
		if(StringUtil.isEmpty(result)){
			UIUtil.showToastSafe(UIUtil.getString(R.string.no_enough_data));
			dismissSwipeLoading();
			return;
		}
		List<SakuraResult> sakuraResults = JSON.parseArray(result, SakuraResult.class);
//		JSON.parseArray(result); Gson gson = new Gson();
		if(sakuraResults==null || sakuraResults.isEmpty() || sakuraResults.get(0) == null){
			UIUtil.showToastSafe(UIUtil.getString(R.string.no_enough_data));
			mSwipeRefreshLayout.setRefreshing(false);
			if(!isRefresh)
			{
				mPageNo--;//此次加载更多 操作没有加载到更多数据，页数--回到当初
			}
			return;
		}
			
		if(sakuraResults.size()>0){
			if(isRefresh){
				//重新获取一次
				//先清除
				mSakuraResultList.clear();
			}
			mSakuraResultList.addAll(sakuraResults);
			
			if(mAdapter==null){
				//刷新或者加载后获取到的newsList
				mAdapter = new MyAdapter(context,mSakuraResultList,mListView);
				mListView.setAdapter(mAdapter);
			}else{
				mAdapter.notifyDataSetChanged();
			}
		}
		//移除顶部和底部view的操作
		mSwipeRefreshLayout.setRefreshing(false);
	}
	
	private void dismissSwipeLoading() {
		if(mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing())
		{
			mSwipeRefreshLayout.setRefreshing(false);
		}
	}

	public int getmClassiNo() {
		return mLevel;
	}

	public void setmClassiNo(int mClassiNo) {
		this.mLevel = mClassiNo;
	}
	
	
}
