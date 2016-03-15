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

import com.alibaba.fastjson.JSONArray;
import com.austin.mynihonngo.R;
import com.austin.mynihonngo.base.BasePager;
import com.austin.mynihonngo.base.ConstantValue;
import com.austin.mynihonngo.base.GlobalParams;
import com.austin.mynihonngo.base.NHGBaseAdapter;
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
import com.lidroid.xutils.http.RequestParams;

public class BaseItemPager extends BasePager implements OnRefreshListener {

	protected static final String TAG = BaseItemPager.class.getSimpleName();
	protected static final String IDS = "ids";
	
	/**
	 * 上拉加载，下拉刷新组件
	 */
	private SwipyRefreshLayout mSwipeRefreshLayout;

	private ListView mListView;
	
	/**
	 * 适配器所需数据
	 */
	private List<WordResult> mWordsList = new ArrayList<WordResult>();

	/**
	 * 当前展示的子页数据类型，如在名词界面时，代表 动物、植物等分类请求参数id
	 */
	private int mClassiNo;//当前页面的类别，动物：1 ；植物2； 交通工具类3，其他4
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
	public BaseItemPager(Context context,int classItemId,int layoutResourceId,String url) {
		super(context,layoutResourceId);
		mClassiNo = classItemId;
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
				WordResult wr = (WordResult) parent.getAdapter().getItem(position);
				Bundle bundle = new Bundle();
				bundle.putSerializable("word", wr);
				mFragListener.openFragment(BaseItemPager.this,ConstantValue.FRAG_TAG_WORD_DETAIL, bundle);
			}

		});
		
		return view;
	}

	@Override
	public void initData() {
		String idString = SharedPreferencesUtils.getStringData(context, IDS, "");
		
		String[] ids = idString.split("#");
		
		getWordItemData(true);
	}
	
	
	class MyAdapter extends NHGBaseAdapter<WordResult, ListView>{

		public MyAdapter(Context context, List<WordResult> list, View Q) {
			super(context, list, Q);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder vh;
			if(convertView == null){
				convertView = View.inflate(context, R.layout.item_word, null);
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
			
			vh.tvWord.setText(getItem(position).getWd_name());
			vh.tvKanna.setText(getItem(position).getWd_kana());
			int tone = getItem(position).getWd_tone();
			String toneStr = null;
			switch (tone) {
			case 0:
				toneStr = context.getResources().getString(R.string.zero_tone);
				break;
			case 1:
				toneStr = context.getResources().getString(R.string.one_tone);
				break;
			case 2:
				toneStr = context.getResources().getString(R.string.two_tone);
				break;
			case 3:
				toneStr = context.getResources().getString(R.string.three_tone);
				break;
			case 4:
				toneStr = context.getResources().getString(R.string.four_tone);
				break;
			case 5:
				toneStr = context.getResources().getString(R.string.five_tone);
				break;
			case 6:
				toneStr = context.getResources().getString(R.string.six_tone);
				break;
			case 7:
				toneStr = context.getResources().getString(R.string.seven_tone);
				break;
			case 8:
				toneStr = context.getResources().getString(R.string.eight_tone);
				break;
			case 9:
				toneStr = context.getResources().getString(R.string.nine_tone);
				break;
			case 10:
				toneStr = context.getResources().getString(R.string.ten_tone);
				break;
			case 100:
				toneStr = null;
				break;
			}
			vh.tvTone.setText(toneStr==null?"":toneStr);
			vh.tvType.setText("【"+getItem(position).getWd_part_speech_name()+"】");
			vh.tvCn.setText(getItem(position).getWd_name_cn());
			return convertView;
		}
		
		class ViewHolder{
			public final View root;
			public final TextView tvWord;
			public final TextView tvKanna;
			public final TextView tvType;
			public final TextView tvCn;
			public final TextView tvTone;
			
			public ViewHolder(View root) {
				super();
				this.tvTone = (TextView) root.findViewById(R.id.tv_tone);
				this.tvWord = (TextView) root.findViewById(R.id.tv_word);
				this.tvKanna = (TextView) root.findViewById(R.id.tv_kanna);
				this.tvType = (TextView) root.findViewById(R.id.tv_wordtype);;
				this.tvCn = (TextView) root.findViewById(R.id.tv_name_cn);
				this.root = root;
			}
			
		}
	}


	@Override
	public void onRefresh(SwipyRefreshLayoutDirection direction) {
		if(direction==SwipyRefreshLayoutDirection.TOP){
			//顶部 下拉刷新
			Log.i(TAG, "下拉刷新");
			getWordItemData(true);
		}
		else if(direction == SwipyRefreshLayoutDirection.BOTTOM){
			// 底部上拉加载更多
			//上拉加载
			Log.i(TAG, "上拉加载");
			mPageNo++;
			getWordItemData(false);
		}
	}

	private void getWordItemData(final boolean isRefresh) {

		RequestParams param = new RequestParams();
		param.addBodyParameter("classifyType", mClassiNo+"");
		if(isRefresh){
			mPageNo=1;
		}
		param.addBodyParameter("pageNo", mPageNo+"");
		IWordCenterEngine engine = BeanFactoryUtil.getImpl(IWordCenterEngine.class);
		engine.getClassifyItemData(url,param,new OnResultListener() {
			
			@Override
			public void onGetData(Object obj, int what) {
				processData((String)obj,isRefresh);
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
		List<WordResult> words = JSONArray.parseArray(result, WordResult.class);
		
		if(words==null || words.isEmpty() || words.get(0) == null){
			UIUtil.showToastSafe(UIUtil.getString(R.string.no_enough_data));
			mSwipeRefreshLayout.setRefreshing(false);
			if(!isRefresh)
			{
				mPageNo--;//此次加载更多 操作没有加载到更多数据，页数--回到当初
			}
			return;
		}
			
		if(words.size()>0){
			if(isRefresh){
				//重新获取一次
				//先清除
				mWordsList.clear();
			}
			mWordsList.addAll(words);
			
			if(mAdapter==null){
				//刷新或者加载后获取到的newsList
				mAdapter = new MyAdapter(context,mWordsList,mListView);
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
		return mClassiNo;
	}

	public void setmClassiNo(int mClassiNo) {
		this.mClassiNo = mClassiNo;
	}
	
	
}
