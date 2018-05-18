package com.run.concurrency.example.syncContailner;

import java.util.Iterator;
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
 * @author user 
 * 同步容器使用synchronized来保证线程同步
 * 同步容器并不能保证线程安全
 */
@ThreadSafe
public class VectorExample3 {

	private static Logger logger = LoggerFactory.getLogger(ConcurrencyTest.class);


	public static void main(String[] args) throws Exception {

		Vector<Integer> vector = new Vector<Integer>();
		vector.add(1);
		vector.add(2);
		vector.add(3);
		vector.add(4);
		
		test2(vector);
	}
	
	//java.util.ConcurrentModificationException
	/**
	 * @param vector
	 * 使用foreach循环和Iterator来循环数组的时候，不要在里面做删除操作
	 * 删除操作会导致抛出ConcurrentModificationException
	 * 建议是要在foreach循环和Iterator来循环数组的时候做删除操作，先做好标记，循环之后再做删除
	 * 正常的for循环可以做删除操作，推荐使用
	 */
	private static void test1(Vector<Integer> vector) {
		for (Integer i : vector) {
			if(i.equals(4)) {
				vector.remove(i);
			}
		}
	}
	
	//java.util.ConcurrentModificationException
	private static void test2(Vector<Integer> vector) {
		Iterator<Integer> iterator = vector.iterator();
		while(iterator.hasNext()) {
			Integer i = iterator.next();
			vector.remove(i);
		}
	}
	
	//success
	private static void test3(Vector<Integer> vector) {
		for(int i = 0;i< vector.size();i++) {
			if(vector.get(i).equals(3)) {
				vector.remove(i);
			}
		}
	}
}
