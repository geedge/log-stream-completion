package com.zdjizhi.utils.kafka;

import com.zdjizhi.common.FlowWriteConfig;
import com.zdjizhi.common.DefaultProConfig;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.apache.kafka.clients.producer.*;

import java.util.List;
import java.util.Properties;

/**
 * NTC系统配置产生日志写入数据中心类
 *
 * @author Administrator
 * @create 2018-08-13 15:11
 */

public class KafkaLogSend {
    private static final Log logger = LogFactory.get();

    /**
     * kafka生产者，用于向kafka中发送消息
     */
    private static Producer<String, String> kafkaProducer;

    /**
     * kafka生产者适配器（单例），用来代理kafka生产者发送消息
     */
    private static KafkaLogSend kafkaLogSend;

    private KafkaLogSend() {
        initKafkaProducer();
    }

    public static KafkaLogSend getInstance() {
        if (kafkaLogSend == null) {
            kafkaLogSend = new KafkaLogSend();
        }
        return kafkaLogSend;
    }


    public void sendMessage(List<String> list) {
        for (String value : list) {
            kafkaProducer.send(new ProducerRecord<>(FlowWriteConfig.RESULTS_OUTPUT_TOPIC, value), new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception != null) {
                        logger.error("写入" + FlowWriteConfig.RESULTS_OUTPUT_TOPIC + "出现异常", exception);
                    }
                }
            });
        }
//        kafkaProducer.flush();
//        list.clear();
        logger.debug("Log sent to National Center successfully!!!!!");
    }

    /**
     * 根据kafka生产者配置信息初始化kafka消息生产者,只初始化一次
     */
    private void initKafkaProducer() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", FlowWriteConfig.OUTPUT_KAFKA_SERVERS);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("acks", FlowWriteConfig.PRODUCER_ACK);
        properties.put("retries", DefaultProConfig.RETRIES);
        properties.put("linger.ms", DefaultProConfig.LINGER_MS);
        properties.put("request.timeout.ms", DefaultProConfig.REQUEST_TIMEOUT_MS);
        properties.put("batch.size", DefaultProConfig.BATCH_SIZE);
        properties.put("buffer.memory", DefaultProConfig.BUFFER_MEMORY);
        properties.put("max.request.size", DefaultProConfig.MAX_REQUEST_SIZE);

        /*
         * kafka限流配置-20201117
         */
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, FlowWriteConfig.PRODUCER_CLIENT_ID);
        properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, FlowWriteConfig.PRODUCER_KAFKA_COMPRESSION_TYPE);


        kafkaProducer = new KafkaProducer<>(properties);
    }


}
