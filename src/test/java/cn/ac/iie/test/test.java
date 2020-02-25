package cn.ac.iie.test;

import cn.ac.iie.common.FlowWriteConfig;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.net.InternetDomainName;
import com.zdjizhi.utils.*;
import org.apache.log4j.Logger;
import org.apache.storm.shade.com.google.common.collect.Lists;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

public class test {
    private static Logger logger = Logger.getLogger(test.class);

    static ZookeeperUtils zookeeperUtils = new ZookeeperUtils();

//
    @Test
        public void test() throws InterruptedException {
            File file = new File("D:\\123\\test.txt");
            String zookeeperIp ="192.168.40.224:2181";
            String kafkaTopic ="CONNECTION-RECORD-LOG";

        System.out.println(zookeeperUtils.modifyNode("/Snowflake/" + kafkaTopic, zookeeperIp));
        System.out.println(zookeeperUtils.modifyNode("/Snowflake/" + kafkaTopic, zookeeperIp));

//            ArrayList<Long> list = Lists.newArrayList();
//            for (int i = 1; i <= 500; i++) {
//                ZooKeeperLock lock = new ZooKeeperLock(zookeeperIp, "/locks", "disLocks1");
//                if (lock.lock()) {
//                    int tmpWorkerId = zookeeperUtils.modifyNode("/Snowflake/" + kafkaTopic, zookeeperIp);
//                    Long generateId = SnowflakeId.generateId(tmpWorkerId, 12);
//                    System.err.println(generateId);
//                    list.add(generateId);
//                    lock.unlock();
//                }
//                if(i%5==0) {
////                    fileWrite(list, file);
//                    Thread.sleep(1000);
//                }
//            }
//            System.err.println("第2个进程结束");

//  FormatUtils build = new FormatUtils.Builder(false).build();
//   long snowflakeId = build.getSnowflakeId("192.168.40.224:2181", "CONNECTION-RECORD-LOG", 12);
//   System.err.println(snowflakeId);


    }
}
