package com.run.concurrency.example.threadlocal;

/**
 * @author user
 * ThreadLocal类，一般有三个方法，添加数据，获取数据，移除数据
 * 一般通过filter来添加数据，通过Interceptor来移除数据
 */
public class RequestHolder {
	
	//通过把一个变量存储在threadlocal中，随取随用，
	//用web三层框架举例，则可以不用每一层都带着从页面拿到的变量往下传
	private final static ThreadLocal<Long> requestHolder = new ThreadLocal();
	
	//提供一个写入ThreadLocal的方法
	//在接口处理之前调用添加方法，本案例中通过filter拦截后添加了ThreadLocal对象
	public static void add(Long id) {
		requestHolder.set(id);
		//threadlocal底层默认存储为一个map结构，key为当前线程的id，value为我们要存储的值
		//本例中，该set方法在底层的map中key为线程id，value为传入的long型的id
	}
	
	//提供一个取出ThreadLocal中存储变量的方法
	public static Long getId() {
		return requestHolder.get();
		//直接调用get() 方法，则默认取出map中以当前线程id为key值的对应存储的value值
	}
	
	//提供一个方法移除ThreadLocal中数据，不提供会导致数据溢出
	//在真正处理完毕之后执行remove方法
	public static void remove() {
		requestHolder.remove();
	}

}
