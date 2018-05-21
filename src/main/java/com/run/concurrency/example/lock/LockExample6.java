package com.run.concurrency.example.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.run.concurrency.annoations.ThreadSafe;




/**
 * @author user
 * condition
 * Condition提供了通过await方法将一个线程加入等待，然后通过signalAll或者signal方法来满足条件重新使线程加入AQS队列
 * 本例中若不使用Condition，则应该线程1全部执行完毕后再执行线程2，或者先2后1
 * 使用了Condition后，线程1执行一部分后，通过await方法将其移除AQS队列，加入Condition队列并等待信号
 * 此时线程1倍挂起后，线程2执行，在线程2中发送了Condition信号，将线程1后半段再次添加到AQS队列
 * 在线程2执行完毕后，激活线程1后半段执行
 */
@ThreadSafe
public class LockExample6 {
	
	private static Logger logger = LoggerFactory.getLogger(LockExample6.class);
	
	public static void main(String[] args) {
		//获取一个reentrantLock实例
		ReentrantLock reentrantLock = new ReentrantLock();
		//从reentrantLock实例中获取Condition
		Condition condition = reentrantLock.newCondition();
		
		//线程1
		new Thread(() -> {
			try {
				reentrantLock.lock();//调用reentrantLock.lock方法，线程1加入AQS的等待队列
				logger.info("wait single"); //1
				condition.await();//调用Condition.await方法，线程1从AQS的等待队列中移除，相当于释放锁，紧接着加入Condition的等待队列中
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("get single");//4 线程1被唤醒后，执行操作
			reentrantLock.unlock();//线程1释放锁
		}).start();
		
		//线程2
		new Thread(() -> {
			reentrantLock.lock();//线程2加入到AQS的等待队列中
			logger.info("get lock"); //2
			try {
				Thread.sleep(3000);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			condition.signalAll();//Condition 提供的发送信号的方法，Condition提供了通过await方法将一个线程加入等待，然后通过signalAll或者signal方法来满足条件重新使线程加入AQS队列
			logger.info("send single");//3  此时Condition的等待队列中有线程1的一个节点，于是它被取出并加入到AQS的等待队列中，但是线程1并没有被唤醒，只是加入到了队列中
			reentrantLock.unlock();//此时线程2释放锁，然后AQS中剩余线程1，此动作后唤醒线程1
		}).start();
	}
}















