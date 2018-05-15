package com.run.concurrency.example.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.run.concurrency.annoations.ThreadSafe;




@ThreadSafe
public class CountExample6 {
	
	private static Logger logger = LoggerFactory.getLogger(CountExample6.class);
	
	private static AtomicBoolean isHappend = new AtomicBoolean(false);//默认为false
//	private static AtomicBoolean isHappend = new AtomicBoolean(true);
	
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
					test();
					semaphore.release();
				} catch (Exception e) {
					logger.error("exception",e);
				}
				countDownLatch.countDown();
				
			});
		}
		countDownLatch.await();
		logger.info("isHappend:"+isHappend.get());//获取值
	}
	
	private static void test() {
		if(isHappend.compareAndSet(false, true)) {
			logger.info("execute");
		}
	}

}
