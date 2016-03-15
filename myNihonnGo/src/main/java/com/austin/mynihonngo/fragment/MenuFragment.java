package com.austin.mynihonngo.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.austin.mynihonngo.MainActivity;
import com.austin.mynihonngo.R;
import com.austin.mynihonngo.base.BaseFragment;
import com.austin.mynihonngo.base.ConstantValue;
import com.austin.mynihonngo.base.GlobalParams;
import com.austin.mynihonngo.base.NHGBaseAdapter;
import com.austin.mynihonngo.engine.TabChangedListener;
import com.austin.mynihonngo.utils.SharedPreferencesUtils;
import com.austin.mynihonngo.utils.UIUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MenuFragment extends BaseFragment implements TextWatcher{
	private static final String tag = "MenuFragment";
	private List<String> titleList = new ArrayList<String>();

	@ViewInject(R.id.lv_menu_news_center)
	private ListView lv_menu_news_center;
	
	private int currentPosition = 0;
	private MyBaseAdapter adapter;
	
	public void initMenu(List<String> titleList) {
		//需要在左侧侧拉栏目显示的结合
		this.titleList = titleList;
		Log.i(tag,"titleList.size() = "+ titleList.size());
		
		adapter = new MyBaseAdapter(context,titleList,lv_menu_news_center);	
		lv_menu_news_center.setAdapter(adapter);
		
		lv_menu_news_center.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				currentPosition = arg2;
				adapter.notifyDataSetChanged();
				//点击左侧侧拉栏目缩回
				slidingMenu.toggle();
				
				//调用NewCenterPager类中的选择某页操作
				//1，获取mainactivity对象,暴露一个获取HomeFragment方法
				//2,HomeFragment暴露一个获取newCenterPager方法
				//3,调用newCenterPager中的选择加载某个页面
				switch (GlobalParams.CURR_TAB_INDEX) {
				case 0://单词中心
					//-----将侧边栏当前选中的条目的index存储到sp以便下次返回该选项卡下时能恢复当前选中项
					SharedPreferencesUtils.putInt(ConstantValue.SP_SLIDINGM_CURRINDEX_WORD_CENTER, currentPosition);
					//-------------------
					((MainActivity)context).getHomeFragment().getNewCenterPager().switchPager(arg2);
					break;
				case 1://樱花日语语法
					//-----将侧边栏当前选中的条目的index存储到sp以便下次返回该选项卡下时能恢复当前选中项
					SharedPreferencesUtils.putInt(ConstantValue.SP_SLIDINGM_CURRINDEX_SAKURA, currentPosition);
					//-------------------
					((MainActivity)context).getHomeFragment().getSakuraPager().switchPager(arg2);
					break;
				case 2://标日语法
					break;
				case 3://政务信息
					break;
				case 4://设置
					break;
				}
			}
		});
	}
	/**设置侧滑栏中当前要显示的选中的项目（侧栏选中条目内容与当前标题栏内容一致）
	 * @param titleName 当前标题栏内容,以此在侧栏list集合查找通名称的内容的角标并显示正确
	 */
	public void setMenuName(String titleName){
		for(int i=0;i<titleList.size();i++){
			if(titleList.get(i).equals(titleName)){
				currentPosition = i;
				adapter.notifyDataSetChanged();
			}
		}
	}
	
	
	@Override
	public View initView(LayoutInflater inflater) {	
		view = inflater.inflate(R.layout.layout_left_menu, null);
		ViewUtils.inject(this, view);
		return view;
	}
	@Override
	public void initData(Bundle savedInstanceState) {
	}
	
	class MyBaseAdapter extends NHGBaseAdapter<String,ListView>{

		public MyBaseAdapter(Context context, List<String> list, View Q) {
			super(context, list, Q);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if(convertView == null){
				holder = new ViewHolder();
				convertView = View.inflate(context, R.layout.layout_item_menu, null);
				
				holder.iv_menu_item = (ImageView) convertView.findViewById(R.id.iv_menu_item);
				holder.tv_menu_item = (TextView) convertView.findViewById(R.id.tv_menu_item);
				
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.tv_menu_item.setText(titleList.get(position));
			
			if(currentPosition == position){
				holder.tv_menu_item.setTextColor(getResources().getColor(R.color.red));
				holder.iv_menu_item.setBackgroundResource(R.mipmap.menu_arr_select);
			}else{
				holder.tv_menu_item.setTextColor(getResources().getColor(R.color.white));
				holder.iv_menu_item.setBackgroundResource(R.mipmap.menu_arr_normal);
			}
			return convertView;
		}
	}
	
	class ViewHolder{
		ImageView iv_menu_item;
		TextView tv_menu_item;
	}

	public void setDefaultMenu() {
		/*this.currentPosition = 0;
		if(adapter != null)
		{
			adapter.notifyDataSetChanged();
		}*/
	}
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		String title = s.toString();
		setMenuName(title);
	}
	
	public void setCurrentPosition(int position){
		currentPosition = position;
		adapter.notifyDataSetChanged();
	}
}
