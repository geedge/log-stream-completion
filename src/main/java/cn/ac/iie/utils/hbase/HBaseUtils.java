package cn.ac.iie.utils.hbase;

import cn.ac.iie.common.FlowWriteConfig;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * HBase 工具类
 *
 * @author qidaijie
 */

public class HBaseUtils {
    private final static Logger logger = Logger.getLogger(HBaseUtils.class);
    private static Map<String, String> subIdMap = new HashMap<>(333334);
    //    private static Map<String, String> subIdMap = new ConcurrentSkipListMap<>();
    private static Connection connection;
    private static Long time;

    static {
        // 管理Hbase的配置信息
        Configuration configuration = HBaseConfiguration.create();
        // 设置zookeeper节点
        configuration.set("hbase.zookeeper.quorum", FlowWriteConfig.HBASE_ZOOKEEPER_SERVERS);
        configuration.set("hbase.client.retries.number", "3");
        configuration.set("hbase.bulkload.retries.number", "3");
        configuration.set("zookeeper.recovery.retry", "3");
        try {
            connection = ConnectionFactory.createConnection(configuration);
            time = System.currentTimeMillis();
            getAll();
        } catch (IOException e) {
            logger.error("获取HBase连接失败");
            e.printStackTrace();
        }
    }

    /**
     * 更新变量
     */
    public static void change() {
        Long nowTime = System.currentTimeMillis();
        timestampsFilter(time - 1000, nowTime + 500);
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
        return subIdMap.get(clientIp);
    }

}
