package cn.ac.iie.test;

import org.apache.log4j.Logger;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ZookeeperTest implements Watcher {
    private static Logger logger = Logger.getLogger(ZookeeperTest.class);
    private static ZooKeeper zookeeper;

    private static final int SESSION_TIME_OUT = 2000;

//    private static Stat stat = new Stat();

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public void process(WatchedEvent event) {
        if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
            countDownLatch.countDown();
        }
    }

    /**
     * 连接zookeeper
     *
     * @param host 地址
     */
    private void connectZookeeper(String host) {
        try {
            zookeeper = new ZooKeeper(host, SESSION_TIME_OUT, this);
            countDownLatch.await();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param path 路径
     * @return 子节点
     */
    private List<String> getChildren(String path) {
        try {
            return zookeeper.getChildren(path, false);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param path 节点创建的路径
     * @param date 节点所存储的数据的byte[]
     * @param acls 控制权限策略
     */
    private void createNode(String path, byte[] date, List<ACL> acls) {
        try {
            Stat exists = zookeeper.exists(path, true);
            if (exists == null) {
                zookeeper.create(path, date, acls, CreateMode.PERSISTENT);
            } else {
                logger.warn("Node already exists!,Don't need to create");
            }
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改节点信息
     *
     * @param path 节点路径
     * @param date 修改的数据
     */
    private void modifyNode(String path, byte[] date) {
        try {
            Stat stat = zookeeper.exists(path, true);
            if (stat != null) {
                zookeeper.setData(path, date, stat.getVersion());
            } else {
                logger.error("Node does not exist!,Can't modify");
            }
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取节点内容
     *
     * @param path 节点路径
     * @return 内容/异常null
     */
    private String getNodeDate(String path) {
        String result = null;
        Stat stat = new Stat();
        try {
            byte[] resByte = zookeeper.getData(path, true, stat);
            result = new String(resByte);
        } catch (KeeperException | InterruptedException e) {
            logger.error("Get node information exception");
            e.printStackTrace();
        }
        return result;
    }


    public static void main(String[] args) {
        ZookeeperTest zookeeperTest = new ZookeeperTest();
        try {
            zookeeperTest.connectZookeeper("192.168.40.207:2181");
            zookeeperTest.createNode("/Snowflake", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE);
//            System.out.println(zookeeperTest.getNodeDate("/testNode/UID-TEST"));
//            zookeeperTest.modifyNode("/testNode/UID-TEST", "2".getBytes());
//            System.out.println(zookeeperTest.getNodeDate("/testNode/UID-TEST"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
