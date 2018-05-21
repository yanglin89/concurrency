package com.run.concurrency.example.aqs;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author user
 * 希望起线程去做某一件事情，并且关注返回结果的时候,建议使用FutureTask
 * 利用Callable
 */
public class FutureTaskExample {
	
	private static Logger logger = LoggerFactory.getLogger(FutureExample.class);

	
	public static void main(String[] args) throws Exception {
		
		FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {

			/* (non-Javadoc)
			 * 重写Callable接口的call方法
			 */
			@Override
			public String call() throws Exception {
				logger.info("do something in callable");
				Thread.sleep(5000);
				return "Done";
			}
		});
		
		new Thread(futureTask).start();
		logger.info("do something in main");
		Thread.sleep(1000);
		String result = futureTask.get();//通过futureTask.get()获取执行结果
		logger.info("result:"+result);

	}

}
