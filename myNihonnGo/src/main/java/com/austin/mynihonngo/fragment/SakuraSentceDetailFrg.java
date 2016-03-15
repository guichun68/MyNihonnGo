package com.austin.mynihonngo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.austin.mynihonngo.MainActivity;
import com.austin.mynihonngo.R;
import com.austin.mynihonngo.base.BaseFragment;
import com.austin.mynihonngo.base.GlobalParams;
import com.austin.mynihonngo.bean.SakuraResult;
import com.austin.mynihonngo.bean.WordResult;
import com.austin.mynihonngo.utils.StringUtil;
import com.austin.mynihonngo.utils.UIManager;
import com.austin.mynihonngo.utils.UIUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;

/**樱花JP 单个句子详解
 * @author Austin
 *
 */
public class SakuraSentceDetailFrg extends BaseFragment implements OnClickListener {
	
	private SakuraResult mSakuraResult;
    // Content View Elements
    private ImageView mIv_left;
    private TextView mTv_sentence;
    private TextView mTv_shiyi;
    private TextView mTv_sentence_cn;
    private TextView mTv_parse_tip;
    private TextView mTv_parse;

    // End Of Content View Elements

    private void bindViews() {
        mIv_left = (ImageView) view.findViewById(R.id.iv_left);
        mTv_sentence = (TextView) view.findViewById(R.id.tv_sentence);
        mTv_shiyi = (TextView) view.findViewById(R.id.tv_shiyi);
        mTv_sentence_cn = (TextView) view.findViewById(R.id.tv_sentence_cn);
        mTv_parse_tip = (TextView) view.findViewById(R.id.tv_parse_tip);
        mTv_parse = (TextView) view.findViewById(R.id.tv_parse);
        
        mIv_left.setOnClickListener(this);
    }

	@Override
	public View initView(LayoutInflater inflater) {
		
		view = inflater.inflate(R.layout.frag_sentence_detail, null);
		bindViews();
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		Bundle bundle = getArguments();
		mSakuraResult = (SakuraResult) bundle.get("sentence");
		
		mTv_sentence.setText(mSakuraResult.getSkrSentenceJP());
		mTv_sentence_cn.setText(mSakuraResult.getSkrSentenceCn());
		mTv_parse.setText(mSakuraResult.getSkrParse());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_left:
			UIManager.getInstance().popBackStack(1);
			break;
		default:
			break;
		}
	}


}
