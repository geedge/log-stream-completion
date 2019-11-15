package cn.ac.iie.test.hbase;

import cn.ac.iie.common.FlowWriteConfig;
import cn.ac.iie.utils.hbase.HBaseUtils;
import com.zdjizhi.utils.StringUtil;
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

public class HBaseTest {
    private final static Logger logger = Logger.getLogger(HBaseTest.class);
    private static Map<String, String> subIdMap = new HashMap<>(16);
    private static Connection connection;
    private static Long time;


    static {
        // 管理Hbase的配置信息
        Configuration configuration = HBaseConfiguration.create();
        // 设置zookeeper节点
        configuration.set("hbase.zookeeper.quorum", FlowWriteConfig.HBASE_ZOOKEEPER_SERVERS);
//        configuration.set("hbase.zookeeper.quorum", FlowWriteConfig.HBASE_ZOOKEEPER_SERVERS);
//        configuration.setInt("zookeeper.session.timeout", 2000);
        try {
            connection = ConnectionFactory.createConnection(configuration);
            time = System.currentTimeMillis();
        } catch (IOException e) {
            logger.error("获取HBase连接失败" + e);
            e.printStackTrace();
        }
    }

//    @Before
//    public void connHBase() {
//
//    }

    @Test
    public void change() {
        Long begin = System.currentTimeMillis();
        System.gc();
        Long start = Runtime.getRuntime().freeMemory();
        System.out.println("开始内存"+start);
        getAll();
        System.gc();
        Long end = Runtime.getRuntime().freeMemory();
        System.out.println("结束内存"+end);
        System.out.println( "一个HashMap对象占内存: " + (end - start));
        System.out.println(System.currentTimeMillis() - begin);
    }

    /**
     * 获取变更内容
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    private static void timestampsFilter(Long startTime, Long endTime) {
        Table table = null;
        TableName tableName = TableName.valueOf("sub:" + FlowWriteConfig.HBASE_TABLE_NAME);
        Admin admin = null;
        try {
            table = connection.getTable(TableName.valueOf("sub:" + FlowWriteConfig.HBASE_TABLE_NAME));
            Scan scan2 = new Scan();
//            scan2.setCaching(500);
            scan2.setTimeRange(startTime, endTime);
            ResultScanner scanner = table.getScanner(scan2);
            scanner.next().isEmpty();
            for (Result result : scanner) {
                Cell[] cells = result.rawCells();
                for (Cell cell : cells) {
                    subIdMap.put(Bytes.toString(CellUtil.cloneRow(cell)), Bytes.toString(CellUtil.cloneValue(cell)));
                }
            }
            admin = connection.getAdmin();
            admin.flush(tableName);
            logger.warn("当前集合长度" + subIdMap.keySet().size());
            logger.warn("更新后集合keys：" + subIdMap.keySet());
            time = endTime;
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
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
    public static void getAll() {
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
            logger.warn("获取全量后集合size：" + subIdMap.size());
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取 account
     *
     * @param ip client_ip
     * @return account
     */
    public static String getAccount(String ip) {
        if (StringUtil.isNotBlank(ip)) {
            return subIdMap.get(ip);
        } else {
            return "";
        }
    }
}
