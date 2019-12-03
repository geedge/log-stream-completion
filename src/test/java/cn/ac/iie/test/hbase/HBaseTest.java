package cn.ac.iie.test.hbase;

import cn.ac.iie.common.FlowWriteConfig;
import cn.ac.iie.utils.hbase.HBaseUtils;
import cn.ac.iie.utils.system.IpUtils;
import com.zdjizhi.utils.StringUtil;
import io.netty.util.collection.IntObjectHashMap;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class HBaseTest {
    private final static Logger logger = Logger.getLogger(HBaseTest.class);
//    private static Map<Integer, String> subIdMap = new ConcurrentHashMap<Integer, String>(13333334);
    private static Map<String, String> subIdMap = new HashMap<>(13333334);
    private static Connection connection;
    private static Long time;


    static {
        // 管理Hbase的配置信息
        Configuration configuration = HBaseConfiguration.create();
        // 设置zookeeper节点
//        configuration.set("hbase.zookeeper.quorum", FlowWriteConfig.HBASE_ZOOKEEPER_SERVERS);
        configuration.set("hbase.zookeeper.quorum", "192.168.40.119:2181,192.168.40.122:2181,192.168.40.123:2181");
        configuration.set("hbase.client.retries.number", "3");
        configuration.set("hbase.bulkload.retries.number", "3");
        configuration.set("zookeeper.recovery.retry", "3");
        try {
            connection = ConnectionFactory.createConnection(configuration);
            time = System.currentTimeMillis();
        } catch (IOException e) {
            logger.error("获取HBase连接失败" + e);
            e.printStackTrace();
        }
    }

    @Test
    public void change() {
//        Long begin = System.currentTimeMillis();
//        getAll();
//        System.out.println(System.currentTimeMillis() - begin);
    }

    /**
     * 获取变更内容
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    private static void timestampsFilter(Long startTime, Long endTime) {
        Long begin = System.currentTimeMillis();
        Table table = null;
        ResultScanner scanner = null;
        Scan scan2 = new Scan();
        try {
            table = connection.getTable(TableName.valueOf("sub:" + FlowWriteConfig.HBASE_TABLE_NAME));
            scan2.setTimeRange(startTime, endTime);
            scanner = table.getScanner(scan2);
            for (Result result : scanner) {
                Cell[] cells = result.rawCells();
                for (Cell cell : cells) {
//                    int key = Integer.parseInt(Bytes.toString(CellUtil.cloneRow(cell)));
                    String key = Bytes.toString(CellUtil.cloneRow(cell));
                    String value = Bytes.toString(CellUtil.cloneValue(cell));
                    if (subIdMap.containsKey(key)) {
                        if (!value.equals(subIdMap.get(key))) {
                            subIdMap.put(key, value);
                        }
                    } else {
                        subIdMap.put(key, value);
                    }
                }
            }
            Long end = System.currentTimeMillis();
            logger.warn("当前集合长度" + subIdMap.keySet().size());
            logger.warn("更新缓存耗时：" + (end - begin) + "开始时间：" + begin + "结束时间：" + end);
            time = endTime;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
            if (table != null) {
                try {
                    table.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取所有的 key value
     */
    private static void getAll() {
        Long begin = System.currentTimeMillis();
        try {
            Table table = connection.getTable(TableName.valueOf("sub:" + FlowWriteConfig.HBASE_TABLE_NAME));
            Scan scan2 = new Scan();
            ResultScanner scanner = table.getScanner(scan2);
            for (Result result : scanner) {
                Cell[] cells = result.rawCells();
                for (Cell cell : cells) {
//                    subIdMap.put(Integer.valueOf(Bytes.toString(CellUtil.cloneRow(cell))), Bytes.toString(CellUtil.cloneValue(cell)));
                    subIdMap.put(Bytes.toString(CellUtil.cloneRow(cell)), Bytes.toString(CellUtil.cloneValue(cell)));
                }
            }
            logger.warn("获取全量后集合长度：" + subIdMap.size());
            logger.warn("获取全量耗时：" + (System.currentTimeMillis() - begin));
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取 account
     *
     * @param clientIp client_ip
     * @return account
     */
    public static String getAccount(String clientIp) {
        int ipType = cn.ac.iie.utils.system.IpUtils.validIPAddress(clientIp);
        String account = "";
        if (ipType == FlowWriteConfig.IPV4_TYPE) {
            account = subIdMap.get(IpUtils.ipChangeInt(clientIp));
        } else if (ipType == FlowWriteConfig.IPV6_TYPE) {
            account = subIdMap.get(clientIp);
        }
        return account;
    }
}
