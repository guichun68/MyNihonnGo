package com.austin.mynihonngo.pager;

import java.util.List;

import android.content.Context;

import com.austin.mynihonngo.R;
import com.austin.mynihonngo.base.ConstantValue;
import com.austin.mynihonngo.base.GlobalParams;
import com.austin.mynihonngo.bean.WordType.Children;

/**词汇中心 之 其他大 类别
 * @author Austin
 *
 */
public class WordPagerOther extends BaseWordPager {

	public WordPagerOther(Context context,List<Children> childCliassfy,int layoutResouceId) {
		super(context,childCliassfy,layoutResouceId);
	}

	@Override
	public void initDataBase() {
		for(int i=0;i<childCliassfy.size();i++){
			Children children = childCliassfy.get(i);
			titleList.add(children.getTitle());
			if(children.getTitle().contains("接头词")){
				pagers.add(new BaseItemPager(context,ConstantValue.OTHER_TYPE_ONE,R.layout.frag_item_word,GlobalParams.URL_OTHER));
			}else if(children.getTitle().contains("接尾词")){
				pagers.add(new BaseItemPager(context,ConstantValue.OTHER_TYPE_TWO,R.layout.frag_item_word,GlobalParams.URL_OTHER));
			}else if(children.getTitle().contains("全部")){
				pagers.add(new BaseItemPager(context,ConstantValue.OTHER_TYPE_ALL,R.layout.frag_item_word,GlobalParams.URL_OTHER));
			}
		}
	}

}
