package com.run.concurrency.example.aqs;

import java.util.ArrayList;  
import java.util.List;  
import java.util.Random;  
import java.util.concurrent.Callable;  
import java.util.concurrent.ExecutionException;  
import java.util.concurrent.ExecutorService;  
import java.util.concurrent.Executors;  
import java.util.concurrent.Future;  
  
public class ExecutorServiceTest {  
    public static void main(String[] args) {  
        ExecutorService executorService = Executors.newCachedThreadPool();  
        List<Future<String>> resultList = new ArrayList<Future<String>>();  
  
        // 创建10个任务并执行  
        for (int i = 0; i < 10; i++) {
            // 使用ExecutorService执行Callable类型的任务，并将结果保存在future变量中  
        	//submit和execute都可以在线程池中调用线程，区别是：submit有返回值，而execute没有
//            Future<String> future = executorService.submit(new TaskWithResult(i));  
            Future<String> future = (Future<String>) executorService.submit(new TaskWithResult1(i));  
            // 将任务执行结果存储到List中  
            resultList.add(future);  
        }  
        executorService.shutdown();  
  
        // 遍历任务的结果  
        for (Future<String> fs : resultList) {
            try {  
            	// 打印各个线程（任务）执行的结果 ，就是future，通过Callable可以get()到具体的值，如果是Runnable接口传入executorService.sumbit()，也可以得到一个future对象，但是get后得到一个null
                ////此处通过实现了Runnable接口，则可以fs.get()只能得到null
            	System.out.println("123123----->"+fs.get()); 
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            } catch (ExecutionException e) {  
                executorService.shutdownNow();  
                e.printStackTrace();  
                return;  
            }  
        }  
    }  
}  
  
/**
 * @author user
 * 同一个包下类名不能相同
 */
class TaskWithResult1 implements Runnable {
    private int id;  
  
    public TaskWithResult1(int id) {  
        this.id = id;  
    }  
  
    /** 
     * 任务的具体过程，一旦任务传给ExecutorService的submit方法，则该方法自动在一个线程上执行。 
     *  
     * @return 
     * @throws Exception 
     */  
    public String call() throws Exception {  
        System.out.println("call()方法被自动调用,干活！！！             " + Thread.currentThread().getName());  
//        if (new Random().nextBoolean())    //return next(1) != 0;
//            throw new TaskException1("Meet error in task." + Thread.currentThread().getName());  
        // 一个模拟耗时的操作  
        for (int i = 999999999; i > 0; i--)  
            ;  
        return "call()方法被自动调用，任务的结果是：" + id + "    " + Thread.currentThread().getName();  
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("call()方法被自动调用,干活！！！             " + Thread.currentThread().getName());  
//      if (new Random().nextBoolean())    //return next(1) != 0;
//          throw new TaskException("Meet error in task." + Thread.currentThread().getName());  
	}  
}  
  
/**
 * @author user
 * 同一个包下类名不能相同
 */
class TaskException1 extends Exception {  
    public TaskException1(String message) {  
        super(message);  
    }  
}