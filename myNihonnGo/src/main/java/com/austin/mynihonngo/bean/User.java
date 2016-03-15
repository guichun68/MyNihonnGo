package com.austin.mynihonngo.bean;

import java.io.Serializable;

import com.lidroid.xutils.db.annotation.Table;

@Table(name = "User") 
public class User implements Serializable{

	private static final long serialVersionUID = 753022436737410527L;
	private int id;
	
	private Integer userId;
	private String loginName;
	private String mobilePhone;
	private String address;
	private String password;
	private int age;
	/**
	 * true 男 false 女
	 */
	private boolean gender;
	private String hobby;
	private String email;
	private String realName;
	private String city;
	
	public User(Integer userId, String loginName, String mobilePhone,
			String address, String password, int age, boolean gender,
			String hobby, String email, String realName, String city) {
		super();
		this.userId = userId;
		this.loginName = loginName;
		this.mobilePhone = mobilePhone;
		this.address = address;
		this.password = password;
		this.age = age;
		this.gender = gender;
		this.hobby = hobby;
		this.email = email;
		this.realName = realName;
		this.city = city;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public boolean isGender() {
		return gender;
	}
	public void setGender(boolean gender) {
		this.gender = gender;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	
}
