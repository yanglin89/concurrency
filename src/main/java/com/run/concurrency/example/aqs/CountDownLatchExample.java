package com.run.concurrency.example.aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CountDownLatchExample {
	
	private static Logger logger = LoggerFactory.getLogger(CountDownLatchExample.class);
	
	private final static int threadCount = 200;
	
	public static void main(String[] args) throws InterruptedException {
		
		ExecutorService exec = Executors.newCachedThreadPool();
		
		final CountDownLatch countDownLatch = new CountDownLatch(threadCount);
		
		for(int i = 0; i < threadCount; i++) {
			final int threadNum = i;
			exec.execute(() -> {
				try {
					test(threadNum);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					countDownLatch.countDown();
				}
			});
		}
		countDownLatch.await();
		logger.info("finish");
		exec.shutdown();
	}
	
	private static void test(int threadNum) throws InterruptedException {
		Thread.sleep(100);
		logger.info("threadNum:-->"+threadNum);
		Thread.sleep(100);
	}

}
