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
import com.run.concurrency.annoations.ThreadSafe;

/**
 * @author user
 * 使用SimpleDateFormat类的时候要每次声明一个新的变量，局部变量，才不会导致线程不安全
 */
@ThreadSafe
public class DateFormatExample2 {
	private static Logger logger = LoggerFactory.getLogger(ConcurrencyTest.class);

	
	//请求总数
	public static int clientTotal = 5000;
	//并发线程数量
	public static int threadTotal = 200;
	
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
			//使用SimpleDateFormat类的时候要每次声明一个新的变量，局部变量，才不会导致线程不安全
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
			simpleDateFormat.parse("20180517");
			logger.info("done");
		} catch (ParseException e) {
			logger.error("parse ecxeption:"+ e.getMessage());
		}
	}
	
}
