package com.run.concurrency.example.aqs;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author user
 * Runable不能对任务结果返回，Callable可以返回
 * 使用Future来接受Callable任务的结果
 */
public class FutureExample {
	
	private static Logger logger = LoggerFactory.getLogger(FutureExample.class);
	
	static class MyCallable implements Callable<String>{

		/* (non-Javadoc)
		 * Callable的call方法返回线程泛型的类型
		 */
		@Override
		public String call() throws Exception {
			logger.info("do something in callable");
			Thread.sleep(5000);
			return "Done";
		}
		
		public static void main(String[] args) throws Exception {
			ExecutorService executorService = Executors.newCachedThreadPool();
			Future<String> future = executorService.submit(new MyCallable());
			logger.info("do something in main");
			Thread.sleep(1000);
			String result = future.get();//调取任务的结果，如果之前的方法没有结束，会一直阻塞在这儿
			logger.info("result:"+result);
		}
		
	}
	
}
