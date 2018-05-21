package com.run.concurrency.example.aqs;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yangwork
 * CyclicBarrier
 * CyclicBarrier.reset() 是将屏障状态置为初始状态
 */
public class CyclicBarrierExample3 {
	
	private static Logger logger = LoggerFactory.getLogger(CyclicBarrierExample3.class);
	
	//指定有多少个线程进入等待
	//初始化CyclicBarrier的时候，还可以指定一个runnable，当拦截个数条件达到的时候，优先执行此处添加的runnable
	private static CyclicBarrier barrier = new CyclicBarrier(5,() -> {
		logger.info("条件达到后，优先执行我这个初始化时添加的runnable");
	}) ;
	
	public static void main(String[] args) throws InterruptedException {

		ExecutorService executor = Executors.newCachedThreadPool();
		
		for(int i = 0; i< 10; i++) {
			final int threadNum = i;
			Thread.sleep(1000);
			executor.execute(() -> {
				try {
					race(threadNum);
				} catch (Exception e) {
					logger.error("exception:", e);
				}
			});
		}
		executor.shutdown();
	}
	
	private static void race(int threadNum) throws InterruptedException {
		Thread.sleep(1000);
		logger.info(threadNum+" is ready.");
		
		try {
			barrier.await();
		} catch (BrokenBarrierException e) {
			logger.error("exception:", e);
		} 
		logger.info(threadNum+" is continue.");
	}

}
