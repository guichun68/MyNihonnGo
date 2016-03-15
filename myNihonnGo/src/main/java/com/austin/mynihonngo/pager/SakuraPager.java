package com.austin.mynihonngo.pager;

import java.util.List;

import android.content.Context;

import com.austin.mynihonngo.R;
import com.austin.mynihonngo.base.GlobalParams;
import com.austin.mynihonngo.bean.WordType.Children;

/**
 * 樱花JP 页面
 * 
 * @author Austin
 * 
 */
public class SakuraPager extends BaseWordPager {
	
	public int mLevel;
	public SakuraPager(Context context, int level,List<Children> nounClassifys,
			int layoutResourceId) {
		super(context, nounClassifys, layoutResourceId);
		this.mLevel = level;
	}

	@Override
	public void initDataBase() {
		for (int i = 0; i < childCliassfy.size(); i++) {
			Children children = childCliassfy.get(i);
			titleList.add(children.getTitle());

			if (children.getTitle().contains("1课")) {
				pagers.add(new BaseSakuraPager(context, mLevel,1,
						R.layout.frag_item_word,
						GlobalParams.URL_SAKURA_CLASS_ITEM));
			} else if (children.getTitle().contains("2课")) {
				pagers.add(new BaseSakuraPager(context, mLevel,2,
						R.layout.frag_item_word,
						GlobalParams.URL_SAKURA_CLASS_ITEM));
			} else if (children.getTitle().contains("3课")) {
				pagers.add(new BaseSakuraPager(context, mLevel,3,
						R.layout.frag_item_word,
						GlobalParams.URL_SAKURA_CLASS_ITEM));
			} else if (children.getTitle().contains("4课")) {
				pagers.add(new BaseSakuraPager(context, mLevel,4,
						R.layout.frag_item_word,
						GlobalParams.URL_SAKURA_CLASS_ITEM));

			} else if (children.getTitle().contains("5课")) {
				pagers.add(new BaseSakuraPager(context, mLevel,5,
						R.layout.frag_item_word,
						GlobalParams.URL_SAKURA_CLASS_ITEM));

			} else if (children.getTitle().contains("6课")) {
				pagers.add(new BaseSakuraPager(context,  mLevel,6,
						R.layout.frag_item_word,
						GlobalParams.URL_SAKURA_CLASS_ITEM));

			} else if (children.getTitle().contains("7课")) {
				pagers.add(new BaseSakuraPager(context,  mLevel,7,
						R.layout.frag_item_word,
						GlobalParams.URL_SAKURA_CLASS_ITEM));

			} else if (children.getTitle().contains("8课")) {
				pagers.add(new BaseSakuraPager(context, mLevel,8,
						R.layout.frag_item_word,
						GlobalParams.URL_SAKURA_CLASS_ITEM));

			} else if (children.getTitle().contains("9课")) {
				pagers.add(new BaseSakuraPager(context, mLevel,9,
						R.layout.frag_item_word,
						GlobalParams.URL_SAKURA_CLASS_ITEM));

			} else if (children.getTitle().contains("10课")) {
				pagers.add(new BaseSakuraPager(context, mLevel,10,
						R.layout.frag_item_word,
						GlobalParams.URL_SAKURA_CLASS_ITEM));

			} else if (children.getTitle().contains("11课")) {
				pagers.add(new BaseSakuraPager(context, mLevel,11,
						R.layout.frag_item_word,
						GlobalParams.URL_SAKURA_CLASS_ITEM));

			} else if (children.getTitle().contains("12课")) {
				pagers.add(new BaseSakuraPager(context, mLevel,12,
						R.layout.frag_item_word,
						GlobalParams.URL_SAKURA_CLASS_ITEM));
			}
		}
	}

}
