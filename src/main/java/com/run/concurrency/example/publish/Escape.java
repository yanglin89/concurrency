package com.run.concurrency.example.publish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.run.concurrency.annoations.NotRecommend;
import com.run.concurrency.annoations.NotThreadSafe;

@NotThreadSafe
@NotRecommend
public class Escape {
	
	private static Logger logger = LoggerFactory.getLogger(UnsafePublish.class);

	private int thisCanBeEscape = 3;

	//构造函数默认创建内部类
	public Escape() {
		new InnerClass();
	}
	
	//内部类
	private class InnerClass{
		public InnerClass() {
			//注意写法， Escape.this.xxx 则可以不用xxx为静态static修饰，可以直接调用
			//但是此时可能会导致this对象的溢出，也就是在new InnerClass();还没有完成的时候，已经先看到了this，可能会出错
			logger.info("Escape.thisCanBeEscape:"+Escape.this.thisCanBeEscape);
		}
	}
	
	public static void main(String[] args) {
		new Escape();
	}
	
	

}
