package cn.ac.iie.utils.influxdb;


import cn.ac.iie.common.FlowWriteConfig;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.function.LongFunction;

/**
 * 写入influxDB工具类
 *
 * @author antlee
 * @date 2018/8/17
 */
public class InfluxDbUtils {
    /**
     * 原始日志写入数据中心kafka失败标识
     */
    public static void sendKafkaFail(int discarded) {
        InfluxDB client = InfluxDBFactory.connect(FlowWriteConfig.INFLUX_IP, FlowWriteConfig.INFLUX_USERNAME, FlowWriteConfig.INFLUX_PASSWORD);
        Point point1 = Point.measurement("SendKafkaFail")
                .tag("topology", FlowWriteConfig.KAFKA_TOPIC)
                .tag("hostname", getIp())
                .field("discarded", discarded)
                .build();
        client.write("BusinessMonitor", "", point1);
    }

    /**
     * 原始日志写入数据中心kafka失败标识
     */
    public static void sendKafkaSuccess(Long complete) {
        if (complete != 0) {
            InfluxDB client = InfluxDBFactory.connect(FlowWriteConfig.INFLUX_IP, FlowWriteConfig.INFLUX_USERNAME, FlowWriteConfig.INFLUX_PASSWORD);
            Point point1 = Point.measurement("SendKafkaSuccess")
                    .tag("topology", FlowWriteConfig.KAFKA_TOPIC)
                    .tag("hostname", getIp())
                    .field("complete", complete)
                    .build();
            client.write("BusinessMonitor", "", point1);
        }
    }

    /**
     * 获取本机IP
     *
     * @return IP地址
     */
    private static String getIp() {
        InetAddress addr;
        try {
            addr = InetAddress.getLocalHost();
            return addr.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        sendKafkaFail(100);
//        sendKafkaSuccess(100L);
    }

}
