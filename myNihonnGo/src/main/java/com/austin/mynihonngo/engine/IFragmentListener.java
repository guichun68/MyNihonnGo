package com.austin.mynihonngo.engine;

import java.util.ArrayList;

import com.austin.mynihonngo.base.BasePager;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public interface IFragmentListener {
	
	/**打开指定fragment
	 * @param fragTag 指定要打开的fragment的tag标识，该标识从GlobalParams中的FRAG_TAG_XXX取值
	 * @param 要传递的参数
	 */
	public void openFragment(String fragTag, Integer... param);
	
	/**打开指定fragment
	 * @param frgTag 指定要打开的fragment的tag标识，该标识从GlobalParams中的FRAG_TAG_XXX取值
	 * @param bundle 传递的bundle类型值
	 */
	public void openFragment(Fragment hidTag, String fragTag, Bundle bundle);
	
	/**打开指定fragment
	 * @param hidFrg 要隐藏的fragment
	 * @param list 传递的list值
	 */
	public <T>void  openFragment(Fragment hidFrg, String fragTag, ArrayList<T> list);

	public void openFragment(String fragTag, Bundle bundle);

	public void openFragment(BasePager pager,
							 String targetFrag, Bundle bundle);

}
