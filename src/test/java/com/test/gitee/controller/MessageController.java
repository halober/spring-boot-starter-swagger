package com.test.gitee.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.gitee.model.Message;
import com.test.gitee.model.ResponseEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "操作消息的api")
@RestController
@RequestMapping("/api/message")
public class MessageController {
	List<Message> messages = new LinkedList<>();

	@ApiOperation("发送消息")
	@PostMapping("/push")
	public ResponseEntity<Void> push(@RequestBody Message message) {
		messages.add(message);
		return ResponseEntity.success();
	}

	@ApiOperation("查看消息列表")
	@PostMapping("/list")
	public ResponseEntity<List<Message>> list(@ApiParam("查看谁的消息") @RequestParam Integer userId) {
		List<Message> list = new LinkedList<>();
		messages.forEach(message -> {
			if (userId.equals(message.getUserId())) {
				list.add(message);
			}
		});
		return ResponseEntity.success(list);
	}
}
