package com.run.concurrency.example.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.run.concurrency.annoations.ThreadSafe;




@ThreadSafe
public class CountExample5 {
	
	private static Logger logger = LoggerFactory.getLogger(CountExample5.class);
	
	//要在AtomicIntegerFieldUpdater.newUpdater中新建时，提供的第二个参数为当前存在的一个用volatile来修饰的变量名称
	//注意，必须用volatile来修饰
	public volatile int count = 100;
	
	public int getCount() {
		return count;
	}

	//AtomicIntegerFieldUpdater 的作用主要是用来对指定类中的指定字段的修改，必须有volatile和非static描述的字段
	private static AtomicIntegerFieldUpdater<CountExample5> updater = AtomicIntegerFieldUpdater.newUpdater(CountExample5.class, "count");
	
	private static CountExample5 example5 = new CountExample5();
	
	public static void main(String[] args) throws Exception {
		if(updater.compareAndSet(example5, 100, 120)) {
			logger.info("success:"+example5.getCount());
		}
		if(updater.compareAndSet(example5, 100, 120)) {
			logger.info("success2:"+example5.getCount());
		}else {
			logger.info("fail:"+example5.getCount());

		}
			
	}

}
