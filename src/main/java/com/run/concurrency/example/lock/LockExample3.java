package com.run.concurrency.example.lock;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




/**
 * @author user
 * ReentrantReadWriteLock读写分离锁，当读锁较多时，可能会导致写操作饥饿
 */
public class LockExample3 {
	
	private static Logger logger = LoggerFactory.getLogger(LockExample3.class);
	
	//自己定义一个内部的map，通过写自己的get，put等方法，并用读写所控制后将方法暴露给外面
	private final Map<String,Data> map = new TreeMap<>();
	//使用ReentrantReadWriteLock，首先定义一个锁的实例
	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	//读锁  ReentrantReadWriteLock可以做到锁的读写分离，悲观读写锁
	private final Lock readLock = lock.readLock();
	private final Lock writeLock = lock.writeLock();//写锁
	
	//使用读锁
	public Data get(String key) {
		readLock.lock();
		try {
			return map.get(key);
		} finally {
			readLock.unlock();
		}
	}
	
	//读锁
	public Set<String> getAllKeys(){
		readLock.lock();
		try {
			return map.keySet();
		} finally {
			readLock.unlock();
		}
	}
	
	//写锁，悲观锁
	public Data put(String key,Data value) {
		writeLock.lock();
		try {
			//Map的put方法返回值类型为对应的value的类型
			//Map的putAll方法的返回值类型为Void
			return map.put(key, value);
		} finally {
			writeLock.unlock();
		}
	}
	
	class Data{
		
	}

}
