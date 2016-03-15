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
import com.austin.mynihonngo.bean.WordResult;
import com.austin.mynihonngo.utils.StringUtil;
import com.austin.mynihonngo.utils.UIManager;
import com.austin.mynihonngo.utils.UIUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;

public class WordDetailFragment extends BaseFragment implements OnClickListener{
	//返回箭头、朗读单词图标、
	private ImageView ivLeft,ivSpeekWord;
	private TextView tvWord;//单词
	private TextView tvKana;//假名
	private TextView tvSpeechType;//词性
	private TextView tvWdCn;//单词中文意思
	private TextView tvSentenceEgSpeek;//例句朗读
	private TextView tvSentence;//例句
	private TextView tvShade;//例句翻译上方遮罩
	private TextView tvSentenceCn;//例句翻译
	private TextView tvExtendWord;//引申单词
	private TextView tvExtendWordCn;//引申单词含义
	private TextView tvOther;//其他说明
	
	private WordResult mWordResult;
	
	@Override
	public View initView(LayoutInflater inflater) {
		
		view = inflater.inflate(R.layout.frag_word_detail, null);
		ivLeft = (ImageView) view.findViewById(R.id.iv_left);
		tvWord = (TextView) view.findViewById(R.id.tv_word);
		tvKana = (TextView) view.findViewById(R.id.tv_kana);
		ivSpeekWord = (ImageView) view.findViewById(R.id.iv_speek);
		tvSpeechType = (TextView) view.findViewById(R.id.tv_speech_type);
		tvWdCn = (TextView) view.findViewById(R.id.tv_wd_cn);
		tvSentenceEgSpeek = (TextView) view.findViewById(R.id.tv_sentence_eg_speek);
		tvSentence = (TextView) view.findViewById(R.id.tv_sentence);
		tvShade = (TextView) view.findViewById(R.id.tv_shade);
		tvSentenceCn = (TextView) view.findViewById(R.id.tv_sentence_cn);
		tvExtendWord = (TextView) view.findViewById(R.id.tv_extend_word);
		tvExtendWordCn = (TextView) view.findViewById(R.id.tv_extend_word_cn);
		tvOther = (TextView) view.findViewById(R.id.tv_other);
		ivLeft.setOnClickListener(this);
		ivSpeekWord.setOnClickListener(this);
		tvSentenceEgSpeek.setOnClickListener(this);
		tvShade.setOnClickListener(this);
		
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		Bundle bundle = getArguments();
		mWordResult = (WordResult) bundle.get("word");
		refreshUI();
	}

	private void refreshUI() {
		tvWord.setText(mWordResult.getWd_name());
		tvKana.setText(mWordResult.getWd_kana());
		tvSpeechType.setText("【"+mWordResult.getWd_part_speech_name()+"】");
		tvWdCn.setText(mWordResult.getWd_name_cn());
		
		if(StringUtil.isEmpty(mWordResult.getWd_sentence_eg())){
			tvSentence.setVisibility(View.GONE);
			tvShade.setVisibility(View.GONE);
		}else{
			tvSentence.setText(""+mWordResult.getWd_sentence_eg());
		}
		tvSentenceCn.setText(StringUtil.isEmpty(mWordResult.getWd_sentence_cn())?" ":mWordResult.getWd_sentence_cn());
		tvExtendWord.setText(StringUtil.isEmpty(mWordResult.getWd_extend_word())?" ":mWordResult.getWd_extend_word());
		tvExtendWordCn.setText(StringUtil.isEmpty(mWordResult.getWd_extend_word_cn())?" ":mWordResult.getWd_extend_word_cn());
		
		if(StringUtil.isEmpty(mWordResult.getWd_extend_others())){
			tvOther.setText(" ");
		}else{
			String wd_extend_others = mWordResult.getWd_extend_others();
			String others = wd_extend_others.replace("|", "\n");//将文本中的“|”变成换行
			tvOther.setText(others);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_left:
			UIManager.getInstance().popBackStack(1);
			break;
		case R.id.iv_speek://单词朗读
			break;
		case R.id.tv_sentence_eg_speek://例句朗读
			break;
		case R.id.tv_shade://例句翻译遮罩
			tvShade.setVisibility(View.INVISIBLE);
			break;
		default:
			break;
		}
	}

}
