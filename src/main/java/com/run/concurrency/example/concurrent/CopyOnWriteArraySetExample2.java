package com.run.concurrency.example.concurrent;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
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
 * 使用CopyOnWriteArraySet做多线程，线程安全,CopyOnWriteArraySet底层通过一个CopyOnWriteArrayList来创建
 * CopyOnWriteArrayList 在对集合做add操作的时候，首先上锁，然后利用原来的集合复制一个比原来长度大1的集合
 * 然后在复制的集合中添加新集合，然后把元集合指向新集合，最后解除锁
 * 
 * CopyOnWriteArrayList 适合对集合数据量小的集合的操作，适合的操作大于写操作的集合
 */
@ThreadSafe
public class CopyOnWriteArraySetExample2 {

	private static Logger logger = LoggerFactory.getLogger(ConcurrencyTest.class);

	private static Set<Integer> set = new CopyOnWriteArraySet<Integer>(); 
	
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
		logger.info("list.size:"+set.size());
	}
	
	private static void update(int i) {
		set.add(i);
	}
	
}
