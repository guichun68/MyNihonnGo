package com.austin.mynihonngo.pager;

import java.util.List;

import android.content.Context;

import com.austin.mynihonngo.R;
import com.austin.mynihonngo.base.ConstantValue;
import com.austin.mynihonngo.base.GlobalParams;
import com.austin.mynihonngo.bean.WordType.Children;

/**词汇中心之 形容词大类别
 * @author Austin
 *
 */
public class WordPagerAdj extends BaseWordPager {

	public WordPagerAdj(Context context, List<Children> childCliassfy,int layoutResouceId) {
		super(context, childCliassfy,layoutResouceId);
	}

	@Override
	public void initDataBase() {
		for(int i=0;i<childCliassfy.size();i++){
			Children children = childCliassfy.get(i);
			titleList.add(children.getTitle());
			if(children.getTitle().contains("一")){
				pagers.add(new BaseItemPager(context,ConstantValue.ADJ_TYPE_ONE,R.layout.frag_item_word,GlobalParams.URL_ADJS));
			}else if(children.getTitle().contains("二")){
				pagers.add(new BaseItemPager(context,ConstantValue.ADJ_TYPE_TWO,R.layout.frag_item_word,GlobalParams.URL_ADJS));
			}
		}
	}

}
