package com.run.concurrency.example.immutable;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.run.concurrency.annoations.ThreadSafe;

/**
 * @author user
 * guava包里面的不可变对象
 */
@ThreadSafe
public class ImmutableExample3 {
	
	private static Logger logger = LoggerFactory.getLogger(ImmutableExample3.class);
	
	//通过of方法可以直接初始化赋值ImmutableList的list
	private final static List list = ImmutableList.of(1, 2, 3);
	private final static ImmutableList list2 = ImmutableList.of(1, 2, 3);
	
	private final static ImmutableSet set = ImmutableSet.copyOf(list);
	
	//map使用of方法赋值时，key value是成对出现的，比如本例中的1和11  2和22
	private final static ImmutableMap<Integer, Integer> map = ImmutableMap.of(1, 11, 2, 22);
	//map还可以使用builder进行赋值，通过不停的put,最后调用build()，注意ImmutableMap.<Integer, Integer>builder()此处的点
	private final static ImmutableMap<Integer, Integer> map2 = ImmutableMap.<Integer, Integer>builder()
			.put(1, 11)
			.put(2, 22)
			.build();
	
	public static void main(String[] args) {
		list.add(4);
		list2.add(4);
		set.add(4);
		map.put(1, 111);
		map2.put(1, 111);
		
		logger.info("map.get(2):"+map.get(2));
	}

}
