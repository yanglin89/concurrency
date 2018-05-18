package com.run.concurrency.example.aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CountDownLatchExample2 {
	
	private static Logger logger = LoggerFactory.getLogger(CountDownLatchExample2.class);
	
	private final static int threadCount = 200;
	
	public static void main(String[] args) throws InterruptedException {
		
		ExecutorService exec = Executors.newCachedThreadPool();
		
		final CountDownLatch countDownLatch = new CountDownLatch(threadCount);
		
		for(int i = 0; i < threadCount; i++) {
			final int threadNum = i;
//			Thread.sleep(1); //此处放置sleep对countDownLatch.await(10,TimeUnit.MILLISECONDS);方法的时间卡控没有意义
			exec.execute(() -> {
				try {
					test(threadNum);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					countDownLatch.countDown();
				}
			});
		}
		//此处的10毫秒等待时间是指test(threadNum)方法的执行时间，在本例中是指for循环把请求放进去之后才开始计时
		//如果test(threadNum)方法执行时间大于await配置时间，则直接执行await方法后面的代码，但是之前给定的线程还是会继续执行，本例中的for循环继续执行
		countDownLatch.await(10,TimeUnit.MILLISECONDS);
		logger.info("finish");
		exec.shutdown();//调用shutdown，对先把当前已有的线程执行完，在销毁线程池
	}
	
	private static void test(int threadNum) throws InterruptedException {
		Thread.sleep(1);//如果此处sleep(100)>await(10,TimeUnit.MILLISECONDS),则先打印finish
		logger.info("threadNum:-->"+threadNum);
	}

}
