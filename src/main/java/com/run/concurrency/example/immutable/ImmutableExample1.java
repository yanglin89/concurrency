package com.run.concurrency.example.immutable;

import java.util.Map;

import com.google.common.collect.Maps;

public class ImmutableExample1 {
	
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
//		map = Maps.newHashMap();
	}

}
