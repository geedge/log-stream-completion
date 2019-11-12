package cn.ac.iie.utils.hbase;

import cn.ac.iie.common.FlowWriteConfig;
import com.zdjizhi.utils.StringUtil;
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
 * @author qidaijie
 */
@SuppressWarnings("all")
public class HbaseUtils {
    private final static Logger logger = Logger.getLogger(HbaseUtils.class);
    private static Map<String, String> subIdMap = new HashMap<>(16);
    private static Connection connection;
    private static Long time;

    static {
        // 管理Hbase的配置信息
        Configuration configuration = HBaseConfiguration.create();
        // 设置zookeeper节点
        configuration.set("hbase.zookeeper.quorum", FlowWriteConfig.HBASE_ZOOKEEPER_SERVERS);
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
        timestampsFilter(time - 1500, nowTime + 500);
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
            scan2.setTimeRange(startTime, endTime);
            ResultScanner scanner = table.getScanner(scan2);
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
    private static void getAll() {
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
            logger.warn("获取全量后集合keys：" + subIdMap.keySet());
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取 account
     *
     * @param ip
     * @return
     */
    public static String getAccount(String ip) {
        if (StringUtil.isNotBlank(ip)) {
            return subIdMap.get(ip);
        } else {
            return "";
        }
    }
}
