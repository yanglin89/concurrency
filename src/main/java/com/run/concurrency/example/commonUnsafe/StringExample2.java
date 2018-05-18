package com.run.concurrency.example.commonUnsafe;

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
 * StringBuffer是线程安全的类，StringBuilder是线程不安全的类
 * 因为StringBuffer的底层在操作的时候添加了synchronized的关键字，同一个时间只有一个线程可以用，性能损耗
 */
@ThreadSafe
public class StringExample2 {
	
	private static Logger logger = LoggerFactory.getLogger(ConcurrencyTest.class);
	//请求总数
	public static int clientTotal = 5000;
	//并发线程数量
	public static int threadTotal = 200;
	
	public static StringBuffer stringBuffer = new StringBuffer();
	
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
		logger.info("stringBuilder:"+stringBuffer.length());
	}
	
	private static void update() {
		stringBuffer.append("1");
	}

	
}
