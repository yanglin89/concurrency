package com.run.concurrency.example.singleton;

import com.run.concurrency.annoations.NotRecommend;
import com.run.concurrency.annoations.ThreadSafe;

/**
 * @author user
 * 懒汉模式
 */
@ThreadSafe
@NotRecommend
public class SingletonExample3 {
	
	//私有的构造函数
	private SingletonExample3() {}
	
	//单例对象，每次返回对象都为同一个
	private static SingletonExample3 instance = null;
	
	//提供一个静态的方法返回单例对象
	//此方法在单线程下没有问题，多线程下线程不安全
	//通过添加synchronized关键字来修饰对象生成，可以保证线程安全
	//不推荐，性能开销较大
	public static synchronized SingletonExample3 getInstance() {
		if(instance == null) {
			instance = new SingletonExample3();
		}
		return instance;
	}
	
	
}
