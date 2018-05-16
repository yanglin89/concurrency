package com.run.concurrency.example.singleton;

import com.run.concurrency.annoations.NotThreadSafe;

/**
 * @author user
 * 懒汉模式，双重同步锁单例模式,线程不安全
 */
@NotThreadSafe
public class SingletonExample4 {
	
	//私有的构造函数
	private SingletonExample4() {}
	
	//单例对象，每次返回对象都为同一个
	private static SingletonExample4 instance = null;
	
	//提供一个静态的方法返回单例对象
	/**
	 * 注意这个类并不是一个线程安全的
	 * 原因分析：
	 * instance = new SingletonExample4(); 执行三步操作：
	 * 1. memory = allocate() 分配对象的内存空间
	 * 2. ctorInstance() 初始化对象
	 * 3. instance = memory 设置instance指向刚分配的内存
	 * 
	 * JVM和cpu优化，可能会导致2、3两步发生指令重排，可能执行顺序为1、3、2
	 * 若有A、B两个线程，A执行到了第二个null值判断，并执行第三步设置指向内存，此时尚未初始化
	 * 此时B线程进行第一次null值判断，判断结果不为null，直接返回instance，会导致B拿到错误的结果
	 */
	public static SingletonExample4 getInstance() {
		if(instance == null) {//双重检测机制                 //B
			synchronized (SingletonExample4.class) {//同步锁
				if(instance == null) {
					instance = new SingletonExample4();   //A - 3
				}
			}
		}
		return instance;
	}
	
	
}
