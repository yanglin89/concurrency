package com.run.concurrency.example.threadPool;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadPoolExample4 {
	
	private static Logger logger = LoggerFactory.getLogger(ThreadPoolExample4.class);
	
	public static void main(String[] args) {
		
		//单线程化的线程执行流程，类似于单线程，所以执行结果是按照顺序执行的
		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
		
		/*executorService.schedule(new Runnable() {
			
			@Override
			public void run() {
				logger.warn("schedule run");
			}
		}, 3, TimeUnit.SECONDS); //调度延迟3秒执行
		executorService.shutdown();
*/		
		
		//使用scheduleAtFixedRate，因为是按照频率不断执行，所以不能关闭线程池了executorService.shutdown()
		executorService.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				logger.warn("schedule run");
			}
		}, 1, 3, TimeUnit.SECONDS);//第一次延迟1秒，之后每一次延迟3秒
//		executorService.shutdown();
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				logger.info("timer run");
			}
		}, 2000); //2秒后执行
		
		/*timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				logger.info("timer run");
			}
		}, new Date(),3000); //每隔三秒执行
*/	}

}
