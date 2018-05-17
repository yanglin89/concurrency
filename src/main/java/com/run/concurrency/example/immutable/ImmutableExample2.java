package com.run.concurrency.example.immutable;

import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.run.concurrency.annoations.ThreadSafe;

/**
 * @author user
 * unmodifiableXXX的不可变对象
 */
@ThreadSafe
public class ImmutableExample2 {
	
	private static Logger logger = LoggerFactory.getLogger(ImmutableExample2.class);
	
	private static Map<Integer,Integer> map = Maps.newHashMap();
	
	static {
		map.put(1, 2);
		map.put(3, 4);
		map.put(5, 2);
		//使用Collections.unmodifiableMap(map)处理过之后的map不能被修改
		//可以编译通过put，但是会抛出异常
		//源码是创建一个新的map对象，并在初始化的时候都赋值，然后把修改的方法都直接抛出异常
		map = Collections.unmodifiableMap(map);
	}
	
	public static void main(String[] args) {
		map.put(1, 3);
		logger.info("map.get(1):"+map.get(1));
	}
	

}
