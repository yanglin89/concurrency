package com.run.concurrency.example.singleton;

import com.run.concurrency.annoations.ThreadSafe;

/**
 * @author user
 * 饿汉模式,若构造方法中逻辑较多，类加载慢，若加载后没有被调用，则资源浪费
 * 恶汉模式是线程安全的
 */
@ThreadSafe
public class SingletonExample2 {
	
	//私有的构造函数
	private SingletonExample2() {}
	
	//单例对象，每次返回对象都为同一个
	private static SingletonExample2 instance = new SingletonExample2();
	
	//静态的工厂方法
	public static SingletonExample2 getInstance() {
		return instance;
	}
	
	
}
