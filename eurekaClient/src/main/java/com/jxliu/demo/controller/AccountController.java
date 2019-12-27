package com.jxliu.demo.controller;

import com.jxliu.demo.entity.Account;
import com.jxliu.demo.entity.TicketRunnable;
import com.jxliu.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.*;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountService accountService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Account> getAccounts() {
        return accountService.findAccountList();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Account getAccountById(@PathVariable("id") int id) {
        return accountService.findAccount(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String updateAccount(@PathVariable("id") int id, @RequestParam(value = "name", required = true) String name,
                                @RequestParam(value = "money", required = true) double money) {
        int t= accountService.update(name,money,id);
        if(t==1) {
            return "success";
        }else {
            return "fail";
        }

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable(value = "id")int id) {
        int t= accountService.delete(id);
        if(t==1) {
            return "success";
        }else {
            return "fail";
        }

    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String postAccount(@RequestParam(value = "name") String name,
                              @RequestParam(value = "money") double money) {

        int t= accountService.add(name,money);
        if(t==1) {
            return "success";
        }else {
            return "fail";
        }

    }


    /**
     * 利用线程池批量插入一千万条数据
     */

    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    public void insert(){
        int threadNum = 100;
        final CyclicBarrier barrier = new CyclicBarrier(threadNum);
        final CountDownLatch count = new CountDownLatch(threadNum);  // 用于统计 执行时长
        long start = System.currentTimeMillis();

        TicketRunnable doInsert = new TicketRunnable(count,barrier);

        //利用线程池跑数据
        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
        for (int i = 0; i < threadNum; i++) {   //此处 设置数值  受限于 线程池中的数量
            executorService.submit(doInsert);
        }


        try {
            count.await();
            executorService.shutdown();
            long end = System.currentTimeMillis();
            System.out.println("耗 时:" +(end-start) + "毫秒");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * 利用单线程批量插入一千万条数据
     */

    @RequestMapping(value = "/insertBySimlpe", method = RequestMethod.GET)
    public void insertBySimlpe(){
        int threadNum = 100;
        final CyclicBarrier barrier = new CyclicBarrier(threadNum);
        final CountDownLatch count = new CountDownLatch(threadNum);  // 用于统计 执行时长
        long start = System.currentTimeMillis();

        TicketRunnable doInsert = new TicketRunnable(count,barrier);

        new Thread(doInsert).start();

//        try {
//            count.await();
//
//            long end = System.currentTimeMillis();
//            System.out.println("耗 时:" +(end-start) + "毫秒");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }


    @RequestMapping(value = "/insertBySimlpe2", method = RequestMethod.GET)
    public void insertBySimlpe2(){
        int threadNum = 100;
        final CyclicBarrier barrier = new CyclicBarrier(threadNum);
        final CountDownLatch count = new CountDownLatch(threadNum);  // 用于统计 执行时长
        long start = System.currentTimeMillis();

        TicketRunnable doInsert = new TicketRunnable(count,barrier);
        try {
            doInsert.insertData2();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
