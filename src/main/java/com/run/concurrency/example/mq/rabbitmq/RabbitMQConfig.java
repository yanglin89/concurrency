package com.run.concurrency.example.mq.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
	
	/**
	 * @return
	 * 定义要发送消息的队列
	 */
	@Bean
	public Queue queue() {
		return new Queue(QueueConstants.TEST);
	}

}
