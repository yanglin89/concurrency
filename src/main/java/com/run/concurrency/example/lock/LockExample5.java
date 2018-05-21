package com.run.concurrency.example.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.StampedLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.run.concurrency.annoations.ThreadSafe;



/**
 * 锁的使用总结：
 * 1.当只有少量竞争者的时候，synchronized锁会是一个很好的选择
 * 2.当竞争者不少，线程增长趋势可预估，ReentrantLock是一个很好的选择
 * synchronized 不会引发死锁，JVM会解锁，其他所需要手动解锁，可能会导致死锁
 * 
 * @author user
 * StampedLock在有大量读操作时有较好的表现
 */
@ThreadSafe
public class LockExample5 {
	
	private static Logger logger = LoggerFactory.getLogger(LockExample5.class);
	//请求总数
	public static int clientTotal = 5000;
	//并发线程数量
	public static int threadTotal = 200;
	
	public static int count = 0;
	
	//使用StampedLock，首先定义一个锁的实例
	private final static StampedLock lock = new StampedLock();
	
	public static void main(String[] args) throws Exception {
		ExecutorService executorService = Executors.newCachedThreadPool();
		final Semaphore semaphore = new Semaphore(threadTotal);
		final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
		for(int i = 0; i<clientTotal; i++) {
			executorService.execute(()-> {
				try {
					semaphore.acquire();
					add();
					semaphore.release();
				} catch (Exception e) {
					logger.error("exception",e);
				}
				countDownLatch.countDown();
				
			});
		}
		countDownLatch.await();
		executorService.shutdown();
		logger.info("count:"+count);
	}
	
	private static void add() {
		long stamp = lock.writeLock();//使用StampedLock添加写锁，会返回一个stamp值
		try {
			count++;
		} finally {
			lock.unlock(stamp);//释放锁的时候要加上stamp值
		}

	}

}
