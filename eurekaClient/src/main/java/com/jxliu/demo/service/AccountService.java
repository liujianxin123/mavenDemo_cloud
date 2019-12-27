package com.jxliu.demo.service;

import com.jxliu.demo.entity.Account;
import com.jxliu.demo.entity.MyThread;
import com.jxliu.demo.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class AccountService {
    private String url = "jdbc:mysql://localhost:3306/demoTest?useUnicode=true&characterEncoding=utf8";
    private String username = "root";
    private String password = "root";

    @Autowired
    private AccountMapper accountMapper;

    public int add(String name, double money) {
        return accountMapper.add(name, money);
    }
    public int update(String name, double money, int id) {
        return accountMapper.update(name, money, id);
    }
    public int delete(int id) {
        return accountMapper.delete(id);
    }
    public Account findAccount(int id) {
        return accountMapper.findAccount(id);
    }
    public List<Account> findAccountList() {
        return accountMapper.findAccountList();
    }



    public int insertUserBehaviour(int size) throws SQLException {

        Connection conn = null;
        PreparedStatement preStmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");


            conn = DriverManager.getConnection(url, username, password);
//https://blog.csdn.net/oldbai001/article/details/51693139
            //https://blog.csdn.net/xiyang_1990/article/details/78771828
            String sql = "insert into account(name, money) values(?, ?)";

            preStmt = conn.prepareStatement(sql);
            for (int i = 0; i < size; i++) {
                preStmt.setString(1, "test00"+i);
                preStmt.setString(2, String.valueOf(i+1));
                preStmt.addBatch();
                if ((i + 1) % 10000 == 0) {
                    preStmt.executeBatch();
                    conn.commit();
                    preStmt.clearBatch();
                }
            }
            //执行批处理sql
            preStmt.executeBatch();
            conn.commit();


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return 1;
    }

    public static void exec(List<String> list) throws InterruptedException{

        int count = 1000;                   //一个线程处理1000条数据
        int listSize = list.size();        //数据集合大小
        int runSize = (listSize/count)+1;  //开启的线程数
        List<String> newlist = null;       //存放每个线程的执行数据
        ExecutorService executor = Executors.newFixedThreadPool(runSize);      //创建一个线程池，数量和开启线程的数量一样

        //创建两个计数器
        CountDownLatch begin = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(runSize);


        //循环创建线程
        for (int i = 0; i < runSize ; i++) {
            //计算每个线程执行的数据
            if((i+1)==runSize){
                int startIndex = (i*count);
                int endIndex = list.size();
                newlist= list.subList(startIndex, endIndex);
            }else{
                int startIndex = (i*count);
                int endIndex = (i+1)*count;
                newlist= list.subList(startIndex, endIndex);
            }
            //线程类
            MyThread mythead = new MyThread(newlist,begin,end);
            //这里执行线程的方式是调用线程池里的executor.execute(mythead)方法。
            executor.execute(mythead);

        }
        begin.countDown();
        end.await();

        //执行完关闭线程池
        executor.shutdown();
    }


    //测试
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        //数据越大线程越多
        for (int i = 0; i < 10000000; i++) {
            list.add("hello"+i);
        }
        try {
            exec(list);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }







}
