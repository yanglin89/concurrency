package com.run.concurrency.example.aqs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SemaphoreExample {
	
	private static Logger logger = LoggerFactory.getLogger(SemaphoreExample.class);
	
	private final static int threadCount = 200;
	
	public static void main(String[] args) throws InterruptedException {
		
		ExecutorService exec = Executors.newCachedThreadPool();
		
		final Semaphore semaphore = new Semaphore(20);
		
		for(int i = 0; i < threadCount; i++) {
			final int threadNum = i;
			exec.execute(() -> {
				try {
					semaphore.acquire();//获取一个许可，获得的许可数为new Semaphore(3)中定义的3个数量线程
					test(threadNum);
					semaphore.release();//释放一个许可
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
			});
		}
		logger.info("finish");
		exec.shutdown();
	}
	
	private static void test(int threadNum) throws InterruptedException {
		logger.info("threadNum:-->"+threadNum);
		Thread.sleep(1000);
	}

}
