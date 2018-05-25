package com.run.concurrency.example.deadLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author user
 * 一个简单的死锁类
 * 当DeadLock类的对象flag==1时，先锁定o1，睡眠500毫秒
 * 而thread1睡眠的时候另一个flag==0的对象（thread2）线程启动，先锁定o2，睡眠500毫秒
 * thread1睡眠结束后需要锁定o2才能继续执行，而此时o2已经被thread2锁定
 * thread2睡眠醒来需要锁定o1才能继续执行，而此时o1已经被thread1锁定
 * thread1、thread2互相等待，都需要得到对方锁定的资源才能继续执行，从而产生死锁
 *
 */
public class DeadLock implements Runnable{
	
	private static Logger logger = LoggerFactory.getLogger(DeadLock.class);
	
	public int flag = 1;
	private static Object o1 = new Object(), o2 = new Object();

	@Override
	public void run() {
		logger.info("flag--->"+flag);
		if(flag == 1) {
			synchronized (o1) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (o2) {
					logger.info("finish1");
				}
			}
		}
		
		if(flag == 0) {
			synchronized (o2) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (o1) {
					logger.info("finish2");
				}
			}
		}
	}
	
	public static void main(String[] args) {
		DeadLock thread1 = new DeadLock();
		DeadLock thread2 = new DeadLock();
		
		thread1.flag = 1;
		thread1.flag = 0;
		
		//刚开始thread1和thread2都处于可执行状态，但是JVM线程调度先执行哪个线程是不确定的
		//thread2的run()方法可能在thread1的run()方法之前执行
		//这儿开2个线程是因为一个线程因为共有flag的值，只有一个if条件可以进入
		new Thread(thread1).start();
		new Thread(thread2).start();
	}
	
	

}
