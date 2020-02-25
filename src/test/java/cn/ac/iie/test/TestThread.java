package cn.ac.iie.test;


import cn.ac.iie.utils.system.SnowflakeId;
import cn.ac.iie.utils.zookeeper.ZookeeperUtils;

class RunnableDemo implements Runnable {
    private Thread t;

    private static ZookeeperUtils zookeeperUtils = new ZookeeperUtils();

    static {
        zookeeperUtils.connectZookeeper("192.168.40.207:2181");
    }

    @Override
    public void run() {
        zookeeperUtils.modifyNode("/testNode/UID-TEST");
        System.out.println(zookeeperUtils.getNodeDate("/testNode/UID-TEST"));
//        zookeeperUtils.closeConn();

    }

    public void start() {
        if (t == null) {
            t = new Thread(this);
            t.start();
        }
    }
}

public class TestThread {
    public static void main(String[] args) {
        RunnableDemo R1 = new RunnableDemo();
        RunnableDemo R2 = new RunnableDemo();
//        RunnableDemo R3 = new RunnableDemo();
//        RunnableDemo R4 = new RunnableDemo();
        R1.start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        R2.start();
//        R3.start();
//        R4.start();

    }
}
