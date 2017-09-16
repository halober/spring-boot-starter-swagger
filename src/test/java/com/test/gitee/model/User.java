package com.test.gitee.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty(required=true,value="登录的用户名")
	private String userName;
	@ApiModelProperty(required=true,value="登录的密码")
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
