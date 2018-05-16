package com.run.concurrency.example.publish;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.run.concurrency.annoations.NotThreadSafe;

@NotThreadSafe
public class UnsafePublish {
	
	private static Logger logger = LoggerFactory.getLogger(UnsafePublish.class);

	private String[] states= {"a","b","c"};
	
	public String[] getStates() {
		return states;
	}
	
	
	public static void main(String[] args) {
		UnsafePublish unsafePublish = new UnsafePublish();
		logger.info("states arrays:"+Arrays.toString(unsafePublish.getStates()));
		
		unsafePublish.getStates()[0] = "d";
		logger.info("states arrays:"+Arrays.toString(unsafePublish.getStates()));

	}
}
