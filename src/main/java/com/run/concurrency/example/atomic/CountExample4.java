package com.run.concurrency.example.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.run.concurrency.annoations.ThreadSafe;




@ThreadSafe
public class CountExample4 {
	
	private static Logger logger = LoggerFactory.getLogger(CountExample4.class);
	
	private static AtomicReference<Integer> count = new AtomicReference<Integer>(0);
	
	
	public static void main(String[] args) throws Exception {
		
		//通过compare是否是一个预期值，来决定是够执行Set操作
		count.compareAndSet(0, 2);
		count.compareAndSet(0, 1);
		count.compareAndSet(1, 3);
		count.compareAndSet(2, 4);
		count.compareAndSet(3, 5);
		
		logger.info("count:"+count.get());
		
	}

}
