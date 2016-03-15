package com.austin.mynihonngo.pager;

import java.util.List;

import android.content.Context;

import com.austin.mynihonngo.R;
import com.austin.mynihonngo.base.ConstantValue;
import com.austin.mynihonngo.base.GlobalParams;
import com.austin.mynihonngo.bean.WordType.Children;


/**词汇中心之 名词大类别
 * @author Austin
 *
 */
public class WordPagerNoun extends BaseWordPager{
	
	public WordPagerNoun(Context context,List<Children> nounClassifys,int layoutResourceId) {
		super(context,nounClassifys,layoutResourceId);
	}

	@Override
	public void initDataBase() {
		for(int i=0;i<childCliassfy.size();i++){
			Children children = childCliassfy.get(i);
			titleList.add(children.getTitle());
			
			if(children.getTitle().contains("动物")){
				pagers.add(new BaseItemPager(context,ConstantValue.NOUN_TYPE_ANIMAL,R.layout.frag_item_word,GlobalParams.URL_NOUNS));
			}else if(children.getTitle().contains("植物")){
				pagers.add(new BaseItemPager(context,ConstantValue.NOUN_TYPE_PLANT,R.layout.frag_item_word,GlobalParams.URL_NOUNS));
			}else if(children.getTitle().contains("交通工具")){
				pagers.add(new BaseItemPager(context,ConstantValue.NOUN_TYPE_VEHICLE,R.layout.frag_item_word,GlobalParams.URL_NOUNS));
			}else if(children.getTitle().contains("其他")){
				pagers.add(new BaseItemPager(context,ConstantValue.NOUN_TYPE_OTHER,R.layout.frag_item_word,GlobalParams.URL_NOUNS));
			}
		}
	}
	
}
