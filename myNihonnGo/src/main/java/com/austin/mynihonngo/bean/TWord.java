package com.austin.mynihonngo.bean;

import java.io.Serializable;


/**单词Bean
 * @author Austin
 *
 */
public class TWord implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 479003281430787768L;
	private int wd_id;//单词id
	private int wd_part_speech_id;//词性：动词、名词、。。。。
	private boolean wd_iscollection;//是否已经加入了收藏
	private String wd_sentence_eg;//例句
	private String wd_sentence_cn;//例句中文意思
	private int wd_tone;//音调，1形音还是2形音 或其他
	private String wd_name;//具体单词
	private int wd_noun_type;//如果是名词，该字段代表名词类型(是noun_type的外键)，比如“动物类”、“植物类”。。。。
	private String wd_name_cn;//单词中文含义
	private int wd_verb_type;//如果是动词，此变量表示其词性，1：他动词 2：自动词
	private String wd_kana;//纯假名批注，如 "調べる"则为"しらべる"，方便学习读音
	private String wd_extend_word;//引申单词
	private String wd_extend_word_cn;//引申单词中文含义
	private String wd_extend_others;//该单词其他相关介绍
	private boolean wd_audit; // 审核是否通过
	
	private Integer wd_belong_book_id;//该单词所属的课本，如1代表初上，2代表初下，以此类推（只用来标识标日的课本）
	private Integer wd_belong_book_course;//所属课本的第几课	
	public TWord() {
		super();
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getWd_id() {
		return wd_id;
	}

	public void setWd_id(int wd_id) {
		this.wd_id = wd_id;
	}

	public int getWd_part_speech_id() {
		return wd_part_speech_id;
	}

	public void setWd_part_speech_id(int wd_part_speech_id) {
		this.wd_part_speech_id = wd_part_speech_id;
	}

	public boolean isWd_iscollection() {
		return wd_iscollection;
	}

	public void setWd_iscollection(boolean wd_iscollection) {
		this.wd_iscollection = wd_iscollection;
	}

	public String getWd_sentence_eg() {
		return wd_sentence_eg;
	}

	public void setWd_sentence_eg(String wd_sentence_eg) {
		this.wd_sentence_eg = wd_sentence_eg;
	}

	public String getWd_sentence_cn() {
		return wd_sentence_cn;
	}

	public void setWd_sentence_cn(String wd_sentence_cn) {
		this.wd_sentence_cn = wd_sentence_cn;
	}

	public int getWd_tone() {
		return wd_tone;
	}

	public void setWd_tone(int wd_tone) {
		this.wd_tone = wd_tone;
	}

	public String getWd_name() {
		return wd_name;
	}

	public void setWd_name(String wd_name) {
		this.wd_name = wd_name;
	}

	public int getWd_noun_type() {
		return wd_noun_type;
	}

	public void setWd_noun_type(int wd_noun_type) {
		this.wd_noun_type = wd_noun_type;
	}

	public String getWd_name_cn() {
		return wd_name_cn;
	}

	public void setWd_name_cn(String wd_name_cn) {
		this.wd_name_cn = wd_name_cn;
	}
	
	@Override
	public String toString() {
		return this.wd_name+":"+this.wd_name_cn;
	}

	public int getWd_verb_type() {
		return wd_verb_type;
	}

	public void setWd_verb_type(int wd_verb_type) {
		this.wd_verb_type = wd_verb_type;
	}



	public String getWd_kana() {
		return wd_kana;
	}

	public void setWd_kana(String wd_kana) {
		this.wd_kana = wd_kana;
	}



	public String getWd_extend_word() {
		return wd_extend_word;
	}



	public void setWd_extend_word(String wd_extend_word) {
		this.wd_extend_word = wd_extend_word;
	}



	public String getWd_extend_word_cn() {
		return wd_extend_word_cn;
	}



	public void setWd_extend_word_cn(String wd_extend_word_cn) {
		this.wd_extend_word_cn = wd_extend_word_cn;
	}



	public String getWd_extend_others() {
		return wd_extend_others;
	}



	public void setWd_extend_others(String wd_extend_others) {
		this.wd_extend_others = wd_extend_others;
	}



	public boolean isWd_audit() {
		return wd_audit;
	}



	public void setWd_audit(boolean wd_audit) {
		this.wd_audit = wd_audit;
	}



	public Integer getWd_belong_book_id() {
		return wd_belong_book_id;
	}



	public void setWd_belong_book_id(Integer wd_belong_book_id) {
		this.wd_belong_book_id = wd_belong_book_id;
	}



	public Integer getWd_belong_book_course() {
		return wd_belong_book_course;
	}



	public void setWd_belong_book_course(Integer wd_belong_book_course) {
		this.wd_belong_book_course = wd_belong_book_course;
	}
	
	
}
