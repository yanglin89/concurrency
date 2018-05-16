package com.run.concurrency.example.sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SynchronizedExample1 {
	
	private static Logger logger = LoggerFactory.getLogger(SynchronizedExample1.class);

	public void test1(int j) {
		//修饰代码块，作用于同一个对象,不同调用对象之间不影响
		synchronized(this) {
			for(int i = 0; i< 100; i++) {
				logger.info("test1:"+j+"--"+ i);
			}
		}
	}
	
	//修饰一个方法，作用于当前对象,不同调用对象之间不影响，跟调用代码块一样
	//如果该类是父类，当有子类调用test2()方法的时候，是不带同步效果的
	public synchronized void test2(int j){
		for(int i = 0; i< 100; i++) {
			logger.info("test2:"+j+"--"+  i);
		}
	}
	
	public static void main(String[] args) {
		SynchronizedExample1 example1 = new SynchronizedExample1();
		SynchronizedExample1 example2 = new SynchronizedExample1();
		ExecutorService executorService = Executors.newCachedThreadPool();
		
		executorService.execute(() -> {
//			example1.test1();
			example1.test2(666);
		});
		executorService.execute(() -> {
//			example1.test1();
			example2.test2(888);
		});
	}
	
}
