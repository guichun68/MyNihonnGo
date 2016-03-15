package com.austin.mynihonngo.pager;

import java.util.List;

import android.content.Context;

import com.austin.mynihonngo.R;
import com.austin.mynihonngo.base.ConstantValue;
import com.austin.mynihonngo.base.GlobalParams;
import com.austin.mynihonngo.bean.WordType.Children;

/**词汇中心之 动词 大类别
 * @author Austin
 *
 */
public class WordPagerVerb extends BaseWordPager {
	
	public WordPagerVerb(Context context,List<Children> verbClassifys,int layoutResourceId) {
		super(context,verbClassifys,layoutResourceId);
	}

	@Override
	public void initDataBase() {
		for(int i=0;i<childCliassfy.size();i++){
			Children children = childCliassfy.get(i);
			titleList.add(children.getTitle());
			if(children.getTitle().contains("一")){
				pagers.add(new BaseItemPager(context,ConstantValue.VERB_TYPE_ONE,R.layout.frag_item_word,GlobalParams.URL_VERBS));
			}else if(children.getTitle().contains("二")){
				pagers.add(new BaseItemPager(context, ConstantValue.VERB_TYPE_TWO, R.layout.frag_item_word, GlobalParams.URL_VERBS));
			}else if(children.getTitle().contains("三")){
				pagers.add(new BaseItemPager(context, ConstantValue.VERB_TYPE_THREE, R.layout.frag_item_word, GlobalParams.URL_VERBS));
			}
		}
		
	}
	
}
