package com.run.concurrency.example.singleton;

import com.run.concurrency.annoations.NotThreadSafe;

/**
 * @author user
 * 懒汉模式
 */
@NotThreadSafe
public class SingletonExample1 {
	
	//私有的构造函数
	private SingletonExample1() {}
	
	//单例对象，每次返回对象都为同一个
	private static SingletonExample1 instance = null;
	
	//提供一个静态的方法返回单例对象
	//此方法在单线程下没有问题，多线程下线程不安全
	public static SingletonExample1 getInstance() {
		if(instance == null) {
			instance = new SingletonExample1();
		}
		return instance;
	}
	
	
}
