package com.run.concurrency.example.singleton;

import com.run.concurrency.annoations.ThreadSafe;

/**
 * @author user
 * 饿汉模式,若构造方法中逻辑较多，类加载慢，若加载后没有被调用，则资源浪费
 * 恶汉模式是线程安全的
 */
@ThreadSafe
public class SingletonExample6 {
	
	//私有的构造函数
	private SingletonExample6() {}
	
	//单例对象，每次返回对象都为同一个
	private static SingletonExample6 instance = null;
	
	//通过静态块方式来实现饿汉模式
	static {
		instance = new SingletonExample6();
	}
	
	//静态的工厂方法
	public static SingletonExample6 getInstance() {
		return instance;
	}
	
	
}
