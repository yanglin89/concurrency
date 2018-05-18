package com.run.concurrency.example.commonUnsafe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.run.concurrency.ConcurrencyTest;
import com.run.concurrency.annoations.NotThreadSafe;

@NotThreadSafe
public class DateFormatExample1 {
	private static Logger logger = LoggerFactory.getLogger(ConcurrencyTest.class);

	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
	
	//请求总数
	public static int clientTotal = 5;
	//并发线程数量
	public static int threadTotal = 2;
	
	public static void main(String[] args) throws Exception {
		ExecutorService executorService = Executors.newCachedThreadPool();
		final Semaphore semaphore = new Semaphore(threadTotal);
		final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
		for(int i = 0; i<clientTotal; i++) {
			executorService.execute(()-> {
				try {
					semaphore.acquire();
					update();
					semaphore.release();
				} catch (Exception e) {
					logger.error("exception",e);
				}
				countDownLatch.countDown();
				
			});
		}
		countDownLatch.await();
		executorService.shutdown();
	}
	
	private static void update() {
		try {
			simpleDateFormat.parse("20180517");
		} catch (ParseException e) {
			logger.error("parse ecxeption:"+ e.getMessage());
		}
	}
	
}
