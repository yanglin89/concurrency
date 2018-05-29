package com.run.concurrency.example.mq.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Yangwork
 * RabbitMQ 监听test队列下的消息
 */
@Component
public class RabbitMQServer {
	
	private static Logger logger = LoggerFactory.getLogger(RabbitMQServer.class);
	
	@RabbitListener(queues = QueueConstants.TEST)
	private void receive(String message) {
		logger.info("{}",message);
	}

}
