package com.run.concurrency.example.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadPoolExample3 {
	
	private static Logger logger = LoggerFactory.getLogger(ThreadPoolExample3.class);
	
	public static void main(String[] args) {
		
		//单线程化的线程执行流程，类似于单线程，所以执行结果是按照顺序执行的
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		for(int i = 0; i< 10; i++) {
			final int index = i;
			executorService.execute(new Runnable() {
				
				@Override
				public void run() {
					logger.info("index:"+index);
				}
			});
		}
		executorService.shutdown();
	}

}
