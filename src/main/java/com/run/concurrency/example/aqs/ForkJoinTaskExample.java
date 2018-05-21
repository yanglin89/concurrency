package com.run.concurrency.example.aqs;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author user
 * ForkJoin：将大任务拆分成小任务执行后将结果组合，思想类似于MR
 * 采用工作窃取算法，双端队列，被窃取任务的线程从队列头端开始执行，窃取任务的线程从队列尾部开始执行
 */
public class ForkJoinTaskExample extends RecursiveTask<Integer>{

	private static Logger logger = LoggerFactory.getLogger(ForkJoinTaskExample.class);
	
	public static final int threshold = 10;
	public int start;
	public int end;
	
	public ForkJoinTaskExample(int start, int end) {
		this.start = start;
		this.end = end;
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.RecursiveTask#compute()
	 * compute()方法真正来执行fork和join操作
	 */
	@Override
	protected Integer compute() {
		
		int sum = 0;
		
		//如果任务足够小就计算任务
		boolean canCompute = (end - start) <= threshold;
		if(canCompute) {
			for (int i = start; i<= end; i++) {
				sum += i;
			}
		}else {//如果任务大于阈值，就分裂成两个子任务计算
			int middle = (end + start)/2;
			ForkJoinTaskExample leftTask = new ForkJoinTaskExample(start,middle);
			ForkJoinTaskExample rightTask = new ForkJoinTaskExample(middle+1,end);
			
			//执行子任务
			leftTask.fork();
			rightTask.fork();
			
			//等待任务执行结束后合并其结果
			int leftResult = leftTask.join();
			int rightResult = rightTask.join();
			
			//合并子任务
			sum = leftResult + rightResult;
		}
		return sum;
	}
	
	public static void main(String[] args) {
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		
		//生成一个计算任务，计算1+2+3+4+...
		ForkJoinTaskExample task = new ForkJoinTaskExample(1, 100);
		
		//执行一个任务，将上面生成的task任务提交到forkJoinPool之后会执行RecursiveTask框架底下的我们重写的compute()方法来执行任务
		Future<Integer> result = forkJoinPool.submit(task);
		
		try {
			logger.info("result:"+result.get());
		} catch (Exception e) {
			logger.error("exception", e);
		}
	}

}





