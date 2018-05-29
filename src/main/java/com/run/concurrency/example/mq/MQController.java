package com.run.concurrency.example.mq;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.run.concurrency.example.mq.kafka.KafkaSender;
import com.run.concurrency.example.mq.rabbitmq.RabbitMQClient;

/**
 * @author Yangwork
 * 消息队列controller，注入消息队列发送类来发布消息
 *
 */
@Controller
@RequestMapping("/mq")
public class MQController {
	
	private static Logger logger = LoggerFactory.getLogger(MQController.class);
	
	@Resource
	private RabbitMQClient rabbitMQClient;
	
	@Resource
	private KafkaSender kafkaSender;
	
	@RequestMapping("/send")
	@ResponseBody
	public String send(@RequestParam("message") String message) {
		rabbitMQClient.send(message);
		kafkaSender.send(message);
		return "success";
	}

}
