package com.run.concurrency.example.syncContailner;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.run.concurrency.ConcurrencyTest;
import com.run.concurrency.annoations.ThreadSafe;

/**
 * @author user 使用Vector做多线程，线程同步
 * 线程同步单并不一定是线程安全的
 * 本例中，2个线程存在线程不安全
 */
@ThreadSafe
public class VectorExample2 {

	private static Logger logger = LoggerFactory.getLogger(ConcurrencyTest.class);

	private static Vector<Integer> list = new Vector<Integer>(); // 上下两种方法创建list都是没有问题的，都是同步容器下创建的
	private static List<Integer> list1 = new Vector<Integer>();

	public static void main(String[] args) throws Exception {

		while (true) {
			for (int i = 0; i < 10; i++) {
				list.add(i);
			}

			Thread thread = new Thread() {
				public void run() {
					for (int i = 0; i < list.size(); i++) {
						list.remove(i);
					}
				}
			};

			Thread thread2 = new Thread() {
				public void run() {
					for (int i = 0; i < list.size(); i++) {
						list.get(i);
					}
				}
			};

			//2个线程存在线程不安全，可能线程2执行到for循环识别集合长度为10，此时要get最后一个
			//但是此时线程1正好执行到了list.remove(9)，则会导致线程2读取的时候数组越界
			thread.start();
			thread2.start();
		}
	}
}
