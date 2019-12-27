package com.jxliu.demo.entity;

import com.jxliu.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MyThread implements Runnable{

    @Autowired
    private AccountService accountService;

    private List<String> list;
    private CountDownLatch begin;
    private CountDownLatch end;
    Object lock = new Object();
    static int tick = 10000000;//一千万条数据

    //创建个构造函数初始化 list,和其他用到的参数
    public MyThread(List<String> list, CountDownLatch begin, CountDownLatch end) {
        this.list = list;
        this.begin = begin;
        this.end = end;
    }


    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName()+"到达,等待中...");
            synchronized (lock){
                for (int i = 0; i < list.size(); i++) {
                    //这里还要说一下，，由于在实质项目中，当处理的数据存在等待超时和出错会使线程一直处于等待状态
                    //这里只是处理简单的，
                    //分批 批量插入
                    System.out.println(Thread.currentThread().getName()+"正在执行---->"+i);
                }
//                System.out.println(Thread.currentThread().getName() + " 线程正在执行");
//                if (tick > 0) {
//                    System.out.println(Thread.currentThread() + "卖出了第" + tick + "张票");
//                    try {
//                        Thread.sleep(20);//休息一秒
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    tick--;
//                } else {
//                    System.out.println("票卖完了");
//                }
                System.out.println(Thread.currentThread().getName() + " 线程运行结束!");
                //执行完让线程直接进入等待
                begin.await();
            }

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            //这里要主要了，当一个线程执行完 了计数要减一不然这个线程会被一直挂起
            // ，end.countDown()，这个方法就是直接把计数器减一的
            end.countDown();
        }

    }
}
