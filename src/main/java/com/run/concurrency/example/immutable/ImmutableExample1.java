package com.run.concurrency.example.immutable;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.run.concurrency.annoations.NotThreadSafe;

@NotThreadSafe
public class ImmutableExample1 {
	
	private static Logger logger = LoggerFactory.getLogger(ImmutableExample1.class);
	
	private final static Integer a = 1;
	private final static String b = "2";
	private final static Map<Integer,Integer> map = Maps.newHashMap();
	
	static {
		map.put(1, 2);
		map.put(3, 4);
		map.put(5, 2);
	}
	
	public static void main(String[] args) {
//		a= 2;
//		b= "3";
//		map = Maps.newHashMap(); //对于一个final修饰的引用类型变量，不能允许指向另外一个对象，但是可以修改当前指向的值
		map.put(1, 3);
		logger.info("map.get(1):"+map.get(1));
	}
	
	private void test(final int a) {//final修饰形式参数，基本类型也不允许改变值
//		a = 1;
	}

}
