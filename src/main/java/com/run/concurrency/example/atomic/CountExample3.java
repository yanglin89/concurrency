package com.run.concurrency.example.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.run.concurrency.annoations.ThreadSafe;




@ThreadSafe
public class CountExample3 {
	
	private static Logger logger = LoggerFactory.getLogger(CountExample3.class);
	//请求总数
	public static int clientTotal = 5000;
	//并发线程数量
	public static int threadTotal = 200;
	//通过LongAdder来确保结果正确，与atomic底层使用CAS --> Unsafe.compareAndSwapInt 的底层不断循环不同
	public static LongAdder count = new LongAdder();
	//LongAdder适合高并发情况，但是需要统计的时候可能会存在误差
	
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
		logger.info("count:"+count);//获取值
	}
	
	private static void add() {
		//对比++i   i++
		count.increment();;
	}

}
