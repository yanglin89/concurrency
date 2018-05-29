package com.run.concurrency.example.mq.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author Yangwork
 * kafka监听消息
 *
 */
@Component
public class KafkaReceiver {
	
	private static Logger logger = LoggerFactory.getLogger(KafkaSender.class);
	
	/**
	 * @param record
	 * 监听TEST消息队列并接受消息
	 */
	@KafkaListener(topics = {TopicConstants.TEST})
	public void receive(ConsumerRecord<?,?> record) {
		logger.info("record:{}",record);
	}


}
