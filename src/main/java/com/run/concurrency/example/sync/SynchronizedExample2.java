package com.run.concurrency.example.sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SynchronizedExample2 {
	
	private static Logger logger = LoggerFactory.getLogger(SynchronizedExample2.class);

	public static void test1(int j) {
		//修饰一个类，作用范围为括号内部分，作用对象为类中的所有对象
		synchronized(SynchronizedExample2.class) {
			for(int i = 0; i< 100; i++) {
				logger.info("test1:"+j+"--"+ i);
			}
		}
	}
	
	//修饰一个静态方法,作用范围为括号内部分，作用对象为类中的所有对象
	public static synchronized void test2(int j){
		for(int i = 0; i< 100; i++) {
			logger.info("test2:"+j+"--"+  i);
		}
	}
	
	public static void main(String[] args) {
		SynchronizedExample2 example1 = new SynchronizedExample2();
		SynchronizedExample2 example2 = new SynchronizedExample2();
		ExecutorService executorService = Executors.newCachedThreadPool();
		
		executorService.execute(() -> {
//			example1.test1();
			example1.test1(666);
		});
		executorService.execute(() -> {
//			example1.test1();
			example2.test1(888);
		});
	}
	
}
