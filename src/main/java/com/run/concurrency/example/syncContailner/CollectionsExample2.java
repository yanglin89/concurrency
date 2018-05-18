package com.run.concurrency.example.syncContailner;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;
import com.run.concurrency.ConcurrencyTest;
import com.run.concurrency.annoations.ThreadSafe;

/**
 * @author user
 * 使用Collections类提供的synchronizedList方法创建集合，做多线程，线程同步
 */
@ThreadSafe
public class CollectionsExample2 {

	private static Logger logger = LoggerFactory.getLogger(ConcurrencyTest.class);

	private static Set<Integer> set = Collections.synchronizedSet(Sets.newHashSet()); 
	
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
		logger.info("set.size:"+set.size());
	}
	
	private static void update(int i) {
		set.add(i);
	}
	
}
