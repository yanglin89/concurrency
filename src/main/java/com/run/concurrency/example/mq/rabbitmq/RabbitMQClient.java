package com.run.concurrency.example.mq.rabbitmq;

import javax.annotation.Resource;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Yangwork
 * rabbitmq 将消息发送到test消息队列
 */
@Component
public class RabbitMQClient {
	
	@Resource
	private RabbitTemplate rebbitTemplate;
	
	public void send(String message) {
		rebbitTemplate.convertAndSend(QueueConstants.TEST, message);
	}

}
