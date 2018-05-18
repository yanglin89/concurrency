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
 */
public class CyclicBarrierExample2 {
	
	private static Logger logger = LoggerFactory.getLogger(CyclicBarrierExample2.class);
	
	//指定有多少个线程进入等待
	private static CyclicBarrier barrier = new CyclicBarrier(5);
	
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
			barrier.await(2000,TimeUnit.MILLISECONDS);//当await方法添加了时间参数之后，可能会出现异常，要想程序继续往下执行，此时一定要try catch捕捉异常，不能直接抛出
		} catch (BrokenBarrierException e) {
			logger.error("exception:", e);
		} catch (TimeoutException e) {
			logger.error("exception:", e);
		} 
		logger.info(threadNum+" is continue.");
	}

}
