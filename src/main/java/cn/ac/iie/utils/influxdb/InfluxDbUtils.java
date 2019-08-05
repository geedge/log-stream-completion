package cn.ac.iie.utils.influxdb;


import cn.ac.iie.common.FlowWriteConfig;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;

import java.net.InetAddress;
import java.net.UnknownHostException;

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
    public static void sendKafkaFail() {
        InfluxDB client = InfluxDBFactory.connect(FlowWriteConfig.INFLUX_IP, FlowWriteConfig.INFLUX_USERNAME, FlowWriteConfig.INFLUX_PASSWORD);
        Point point1 = Point.measurement("sendkafkafail")
                .tag("topology", FlowWriteConfig.KAFKA_TOPIC)
                .tag("hostname", getIp())
                .field("state", 1)
                .build();
        client.write("kafka", "", point1);
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

}
