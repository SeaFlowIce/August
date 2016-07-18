package com.galgame.util;

import cn.bmob.v3.BmobUser;

public class MyUser extends BmobUser {
	private int age;
	
	public MyUser() {
	}
	
	public MyUser(String username, String password) {
		this(username, password, 0);
	}
	public MyUser(String username, String password, int age) {
		setUsername(username);
		setPassword(password);
		this.setAge(age);
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
