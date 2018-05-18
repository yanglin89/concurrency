package com.run.concurrency.example.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
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
 * 使用ConcurrentSkipListMap做多线程，线程不安全
 * ConcurrentSkipListMap 在线程数量越多的情况下表现越好，因为它基本与线程数量无关
 */
@ThreadSafe
public class ConcurrentSkipListMapExample2 {

	private static Logger logger = LoggerFactory.getLogger(ConcurrencyTest.class);

	private static Map<Integer,Integer> map = new ConcurrentSkipListMap<Integer,Integer>(); 
	
	//请求总数
	public static int clientTotal = 5000;
	//并发线程数量
	public static int threadTotal = 200;
	
	public static void main(String[] args) throws Exception {
		ExecutorService executorService = Executors.newCachedThreadPool();
		final Semaphore semaphore = new Semaphore(threadTotal);
		final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
		for(int i = 0; i<clientTotal; i++) {
			 int count = i;
			executorService.execute(()-> {
				try {
					semaphore.acquire();
					update(count);
					semaphore.release();
				} catch (Exception e) {
					logger.error("exception",e);
				}
				countDownLatch.countDown();
				
			});
		}
		countDownLatch.await();
		executorService.shutdown();
		logger.info("map.size:"+map.size());
	}
	
	private static void update(int i) {
		map.put(i,i);
	}
	
}
