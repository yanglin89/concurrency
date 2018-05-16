package com.run.concurrency.example.singleton;

import com.run.concurrency.annoations.Recommend;
import com.run.concurrency.annoations.ThreadSafe;

/**
 * @author user
 * 枚举模式：最安全
 * 枚举模式在使用时调用
 */
@ThreadSafe
@Recommend
public class SingletonExample7 {
	
	//私有的构造函数
	private SingletonExample7() {}
	
	public static SingletonExample7 getInstance() {
		return Singleton.INSTANCE.getInstance();
	}
	
	private enum Singleton{
		INSTANCE;
		
		private SingletonExample7 singleton;
		
		//JVM保证这个方法绝对只被调用一次
		Singleton(){
			singleton = new SingletonExample7();
		}
		
		public SingletonExample7 getInstance() {
			return singleton;
		}
	}
	
}
