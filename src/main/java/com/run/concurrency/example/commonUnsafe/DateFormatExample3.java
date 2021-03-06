package com.run.concurrency.example.commonUnsafe;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.run.concurrency.ConcurrencyTest;
import com.run.concurrency.annoations.Recommend;
import com.run.concurrency.annoations.ThreadSafe;

/**
 * @author user
 * 使用joda-time实现日期转换线程安全
 * 推荐使用joda-time
 */
@ThreadSafe
@Recommend
public class DateFormatExample3 {
	private static Logger logger = LoggerFactory.getLogger(ConcurrencyTest.class);

	private static DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyyMMdd");
	
	//请求总数
	public static int clientTotal = 50;
	//并发线程数量
	public static int threadTotal = 2;
	
	public static void main(String[] args) throws Exception {
		ExecutorService executorService = Executors.newCachedThreadPool();
		final Semaphore semaphore = new Semaphore(threadTotal);
		final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
		for(int i = 0; i<clientTotal; i++) {
			 int count = i;
			executorService.execute(()-> {
				try {
					semaphore.acquire();
					update(count);
					semaphore.release();
				} catch (Exception e) {
					logger.error("exception",e);
				}
				countDownLatch.countDown();
				
			});
		}
		countDownLatch.await();
		executorService.shutdown();
	}
	
	private static void update(int i) {
		Date date = DateTime.parse("20180518",dateTimeFormatter).toDate();
		logger.info("i的值:"+i+"date:"+date);
	}
	
}
