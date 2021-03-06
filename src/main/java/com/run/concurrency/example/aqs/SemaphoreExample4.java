package com.run.concurrency.example.aqs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SemaphoreExample4 {
	
	private static Logger logger = LoggerFactory.getLogger(SemaphoreExample4.class);
	
	private final static int threadCount = 20;
	
	public static void main(String[] args) throws InterruptedException {
		
		ExecutorService exec = Executors.newCachedThreadPool();
		
		final Semaphore semaphore = new Semaphore(3);
		
		for(int i = 0; i < threadCount; i++) {
			final int threadNum = i;
			exec.execute(() -> {
				try {
					//尝试获取许可，获取到了执行，获取不到后直接丢弃后面的请求，本例中200个线程只有20个可以获取到
					//但是后面因为test方法内等待了1秒，导致后面的180个线程请求直接被丢弃了
					//如果把test方法内等待了1秒去掉，则所有的200个线程都可以执行
					if(semaphore.tryAcquire(2000, TimeUnit.MILLISECONDS)) {//此处的等待时间是总的等待时间为2秒，不是每次2秒，test中等待1秒，则此处还可以多输出2轮test方法
						test(threadNum);
						semaphore.release();
					}
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
			});
		}
		logger.info("finish");
		exec.shutdown();
	}
	
	private static void test(int threadNum) throws InterruptedException {
		logger.info("threadNum:-->"+threadNum);
		Thread.sleep(1000);
	}

}
