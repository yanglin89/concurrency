package com.run.concurrency.example.aqs;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yangwork
 * CyclicBarrier
 */
public class CyclicBarrierExample {
	
	private static Logger logger = LoggerFactory.getLogger(CyclicBarrierExample.class);
	
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
				} catch (InterruptedException e) {
					logger.error("exception:", e.getMessage());
				} catch (BrokenBarrierException e) {
					logger.error("exception:", e);
				}
			});
		}
	}
	
	private static void race(int threadNum) throws InterruptedException, BrokenBarrierException {
		Thread.sleep(1000);
		logger.info(threadNum+" is ready.");
		
		//通常来说是当一个线程准备好之后调用await（）方法
		barrier.await(); //每一个线程都要调用一个await()方法来告诉CyclicBarrier贮备好了，然后开始计数，增加到指定值之后可以大家一起开始继续执行
		logger.info(threadNum+" is continue.");
	}

}
