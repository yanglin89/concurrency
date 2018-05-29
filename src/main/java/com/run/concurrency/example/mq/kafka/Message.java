package com.run.concurrency.example.mq.kafka;

import java.util.Date;

/**
 * @author Yangwork
 * kafka的消息存储类
 */
//@Data
public class Message {
	
	private Long id;
	
	private String msg;
	
	private Date sendTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	
	

}
