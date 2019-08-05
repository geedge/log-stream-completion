package cn.ac.iie.utils.kafka;

import cn.ac.iie.common.FlowWriteConfig;
import cn.ac.iie.utils.influxdb.InfluxDbUtils;
import org.apache.kafka.clients.producer.*;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * NTC系统配置产生日志写入数据中心类
 *
 * @author Administrator
 * @create 2018-08-13 15:11
 */

public class KafkaLogNtc {
    private static Logger logger = Logger.getLogger(KafkaLogNtc.class);

    /**
     * kafka生产者，用于向kafka中发送消息
     */
    private static Producer<String, String> kafkaProducer;

    /**
     * kafka生产者适配器（单例），用来代理kafka生产者发送消息
     */
    private static KafkaLogNtc kafkaLogNtc;

    private KafkaLogNtc() {
        initKafkaProducer();
    }

    public static KafkaLogNtc getInstance() {
        if (kafkaLogNtc == null) {
            kafkaLogNtc = new KafkaLogNtc();
        }
        return kafkaLogNtc;
    }


    public void sendMessage(List<String> list) {
        final int[] errorSum = {0};
        for (String value : list) {
            kafkaProducer.send(new ProducerRecord<>(FlowWriteConfig.RESULTS_OUTPUT_TOPIC, value), new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception != null) {
                        logger.error("写入" + FlowWriteConfig.RESULTS_OUTPUT_TOPIC + "出现异常", exception);
                        errorSum[0]++;
                    }
                }
            });
            if (errorSum[0] > FlowWriteConfig.MAX_FAILURE_NUM) {
                logger.error("超出最大容忍错误数抛弃数据条数:" + list.size());
                list.clear();
            }
        }
        kafkaProducer.flush();
        logger.warn("Log sent to National Center successfully!!!!!");
    }

    /**
     * 根据kafka生产者配置信息初始化kafka消息生产者,只初始化一次
     */
    private void initKafkaProducer() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", FlowWriteConfig.BOOTSTRAP_SERVERS);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("acks", "1");
        properties.put("linger.ms", "2");
        properties.put("request.timeout.ms", 60000);
        properties.put("batch.size", 262144);
        properties.put("buffer.memory", 33554432);
//        properties.put("compression.type", "snappy");
        kafkaProducer = new KafkaProducer<>(properties);
    }


}
