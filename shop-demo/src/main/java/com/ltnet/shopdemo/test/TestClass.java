package com.ltnet.shopdemo.test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestClass {

    private static final Lock lock = new ReentrantLock();

    public static void main(String[] args) {
//        new Thread(() -> test(), "线程A").start();
//        new Thread(() -> test(), "线程B").start();

        MyThread thread = new MyThread();
        Thread thread1 = new Thread(thread);
        Thread thread2 = new Thread(thread);
        thread1.start();
        thread2.start();
    }

    public static void test() {
        try {
            //lock.lock() 会阻塞直到锁被释放，当前线程拿到锁
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " 获取了锁");
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + " 释放了锁");
            lock.unlock();
        }
    }

//    public static void main(String[] args) {
//        MyThread thread = new MyThread();
//        Thread thread1 = new Thread(thread);
//        Thread thread2 = new Thread(thread);
//        thread1.start();
//        thread2.start();
//    }
}

class MyThread extends Thread {
    ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println(name + " - 开始执行");

        try {
            //lock.tryLock() 如果锁被占有，则返回false继续向下执行；否则返回true得到锁
            //timeout设置时间，表示等待该时长还未获取到锁，则继续执行
            if (lock.tryLock(0, TimeUnit.SECONDS)) {
                System.out.println(name + " - 获取了锁");
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(name + " - 释放了锁");
            lock.unlock();
        }
    }
}