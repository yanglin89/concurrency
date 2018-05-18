package com.run.concurrency.example.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.run.concurrency.annoations.ThreadSafe;




@ThreadSafe
public class CountExample2 {
	
	private static Logger logger = LoggerFactory.getLogger(CountExample2.class);
	//请求总数
	public static int clientTotal = 5000;
	//并发线程数量
	public static int threadTotal = 200;
	//通过AtomicXX来确保结果正确，底层使用CAS --> Unsafe.compareAndSwapInt
	public static AtomicLong count = new AtomicLong(0);
	
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
		logger.info("count:"+count.get());//获取值
	}
	
	private static void add() {
		//对比++i   i++
//		count.incrementAndGet();
		count.getAndIncrement();
	}

}
