package cn.ac.iie.spout;

import cn.ac.iie.common.FlowWriteConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.log4j.Logger;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;

/**
 * kafkaSpout
 *
 * @author Administrator
 */
public class CustomizedKafkaSpout extends BaseRichSpout {
    private static final long serialVersionUID = -3363788553406229592L;
    private KafkaConsumer<String, String> consumer;
    private SpoutOutputCollector collector = null;
    private TopologyContext context = null;
    private final static Logger logger = Logger.getLogger(CustomizedKafkaSpout.class);


    private static Properties createConsumerConfig() {
        Properties props = new Properties();
        props.put("bootstrap.servers", FlowWriteConfig.INPUT_KAFKA_SERVERS);
        props.put("group.id", FlowWriteConfig.GROUP_ID);
        props.put("session.timeout.ms", "60000");
        props.put("max.poll.records", 3000);
        props.put("max.partition.fetch.bytes", 31457280);
        props.put("auto.offset.reset", FlowWriteConfig.AUTO_OFFSET_RESET);
//        switch (FlowWriteConfig.KAFKA_TOPIC) {
//            case "PROXY-EVENT-LOG":
//                props.put("client.id", "proxy");
//                break;
//            case "RADIUS-RECORD-LOG":
//                props.put("client.id", "radius");
//                break;
//            case "CONNECTION-RECORD-LOG":
//                props.put("client.id", "connection");
//                break;
//            case "SECURITY-EVENT-LOG":
//                props.put("client.id", "security");
//                break;
//            case "CONNECTION-SKETCH":
//                props.put("client.id", "sketch");
//                break;
//            case "ACTIVE-DEFENCE-EVENT-LOG":
//                props.put("client.id", "active");
//                break;
//            case "SYS-PACKET-CAPTURE-LOG":
//                props.put("client.id", "packet");
//                break;
//
//            default:
//        }
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        /**
         * kafka限流配置-20201117
         */
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, FlowWriteConfig.CONSUMER_CLIENT_ID);

        return props;
    }

    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        // TODO Auto-generated method stub
        this.collector = collector;
        this.context = context;
        Properties prop = createConsumerConfig();
        this.consumer = new KafkaConsumer<>(prop);
        this.consumer.subscribe(Collections.singletonList(FlowWriteConfig.KAFKA_TOPIC));
    }

    @Override
    public void close() {
        consumer.close();
    }

    @Override
    public void nextTuple() {
        try {
            // TODO Auto-generated method stub
            ConsumerRecords<String, String> records = consumer.poll(10000L);
            Thread.sleep(FlowWriteConfig.TOPOLOGY_SPOUT_SLEEP_TIME);
            for (ConsumerRecord<String, String> record : records) {
                this.collector.emit(new Values(record.value()));
            }
        } catch (Exception e) {
            logger.error("KafkaSpout发送消息出现异常!", e);
            e.printStackTrace();
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        // TODO Auto-generated method stub
        declarer.declare(new Fields("source"));
    }
}
