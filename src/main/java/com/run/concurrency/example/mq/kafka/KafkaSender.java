package com.run.concurrency.example.mq.kafka;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Yangwork
 * kafka发送消息
 */
@Component
public class KafkaSender {
	
	private static Logger logger = LoggerFactory.getLogger(KafkaSender.class);
	
	@Resource
	private KafkaTemplate<String,String> kafkaTemplate;

	private Gson gson = new GsonBuilder().create();
	
	public void send(String msg) {
		Message message = new Message();
		message.setId(System.currentTimeMillis());
		message.setMsg(msg);
		message.setSendTime(new Date());
		logger.info("send message: {}",message);
		//TopicConstants是一个接口interface
		//发送消息message到TEST消息队列
		kafkaTemplate.send(TopicConstants.TEST, gson.toJson(message));
	}
}






