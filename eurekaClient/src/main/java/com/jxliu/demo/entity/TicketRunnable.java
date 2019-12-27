package com.jxliu.demo.entity;

import com.jxliu.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class TicketRunnable implements Runnable{

    private CountDownLatch count;
    private CyclicBarrier barrier;
    private String url = "jdbc:mysql://localhost:3306/demoTest?useUnicode=true&characterEncoding=utf8";
    private String username = "root";
    private String password = "root";
    Random random = new Random();
    public TicketRunnable(CountDownLatch count, CyclicBarrier barrier) {
        this.count = count;
        this.barrier = barrier;
    }

//    private int num = 10;  // 总数
    Object lock = new Object();

    public void insertData() throws SQLException {
        synchronized (lock) {
                int num = 0;
                Connection conn = null;
                PreparedStatement preStmt = null;
                try {
                    Class.forName("com.mysql.jdbc.Driver");

                    conn = DriverManager.getConnection(url, username, password);
                    String sql = "insert into tb_emp(name, sex,age,email,address) values(?,?,?,?,?)";
                    preStmt = conn.prepareStatement(sql);
                    for (int i = 0; i < 100000; i++) {

                        System.out.println(Thread.currentThread().getName() + "插入" + num++ +"条");

                        int r = (int)((Math.random()*9+1)*10000000);
                        preStmt.setString(1, "test0"+i);//姓名
                        preStmt.setString(2, (i+1)%2 == 0 ? "1":"0");//性别
                        preStmt.setString(3, String.valueOf(random.nextInt(20)+20));//年龄
                        preStmt.setString(4, String.valueOf(r)+"@wangliu.com");//邮箱
                        preStmt.setString(5, "上海市南京西路东大街西路北段");//地址

                        preStmt.addBatch();
                        if ((i + 1) % 100000 == 0) {
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




//                for (int i = 0; i < 10000; i++) {
//
//                    System.out.print(Thread.currentThread().getName() + "插入" + num);
//                    num--;
//                    if(num!=0)
//                        System.out.println(",还剩" + num + "条--" );
//                    else
//                        System.out.println("，一千万插入完!--");
//                }
            }

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"到达,等待中...");
        try{
            //barrier.await();    // 此处阻塞  等所有线程都到位后做插入
            if(Thread.currentThread().getName().equals("pool-1-thread-1")){
                System.out.println("---------------全部线程准备就绪,开始----------------");
            }else {
                Thread.sleep(10);
            }
           insertData();//插入的方法
//            insertData2();//提交事务插入
        }catch (Exception e){}

        count.countDown();  //当前线程结束后，计数器-1
    }






    public void insertData2() throws SQLException {
        Connection conn = null;
        PreparedStatement pst =  null;
        long beginTime = 0;
        long endTime = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
            if(conn != null) {
                System.out.println("获取连接成功");
                beginTime = new Date().getTime();//开始计时
                String sqlPrefix = "insert into tb_emp(name, sex,age,email,address) values";
                // 保存sql后缀
                StringBuffer suffix = new StringBuffer();
                // 设置事务为非自动提交
                conn.setAutoCommit(false);
                // 比起st，pst会更好些
                pst = (PreparedStatement) conn.prepareStatement("");//准备执行语句
                // 外层循环，总提交事务次数
                int num = 0;
                for (int i = 1; i <= 100; i++) {
                    suffix = new StringBuffer();
                    // 第j次提交步长
                    for (int j = 1; j <= 100000; j++) {

                        int r = (int)((Math.random()*9+1)*10000000);
                        String name = "test0"+i;
                        String sex = (i+1)%2 == 0 ? "1":"0";
                        String age = String.valueOf(random.nextInt(20)+20);
                        String email = String.valueOf(r)+"@wangliu.com";
                        String address = "上海市南京西路东大街西路北段";

                        // 构建SQL后缀
                        suffix.append("('"+ name +"','" + sex +"','"+ age + "','" + email +"','"+ address + "'),");
                    System.out.println("构造了"+ num++ +"条sql");
                    }
                    // 构建完整SQL
                    String sql = sqlPrefix + suffix.substring(0, suffix.length() - 1);
//                    System.out.println("构造的完整sql:"+sql);
                    // 添加执行SQL
                    pst.addBatch(sql);
                    // 执行操作
                    pst.executeBatch();
                    // 提交事务
                    conn.commit();
                    // 清空上一次添加的数据
                    suffix = new StringBuffer();
                }
                endTime = new Date().getTime();//开始计时
            }else {
                System.out.println("数据库连接失败");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("com.mysql.jdbc.Driver驱动类没有找到");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库地址错误");
        }finally {//释放资源
            System.out.println("插入成功，所有时间："+ (endTime-beginTime));
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }



}
