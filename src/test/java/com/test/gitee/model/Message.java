package com.test.gitee.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(required=true, value= "接受消息的用户编号")
	private Integer userId;
	@ApiModelProperty("消息体")
	private String body;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
