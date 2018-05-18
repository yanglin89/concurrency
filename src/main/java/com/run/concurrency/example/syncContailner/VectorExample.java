package com.run.concurrency.example.syncContailner;

import java.util.List;
import java.util.Vector;
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
 * 使用Vector做多线程，线程同步
 */
@ThreadSafe
public class VectorExample {

	private static Logger logger = LoggerFactory.getLogger(ConcurrencyTest.class);

	private static Vector<Integer> list = new Vector<Integer>(); //上下两种方法创建list都是没有问题的，都是同步容器下创建的
	private static List<Integer> list1 = new Vector<Integer>(); 
	
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
		logger.info("list.size:"+list.size());
	}
	
	private static void update(int i) {
		list.add(i);
	}
	
}
