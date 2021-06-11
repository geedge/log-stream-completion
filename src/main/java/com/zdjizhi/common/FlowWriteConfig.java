package com.zdjizhi.common;


import com.zdjizhi.utils.system.FlowWriteConfigurations;

/**
 * @author Administrator
 */
public class FlowWriteConfig {

    public static final int IF_PARAM_LENGTH = 3;
    public static final String VISIBILITY = "disabled";
    public static final String FORMAT_SPLITTER = ",";
    public static final String IS_JSON_KEY_TAG = "$.";
    public static final String IF_CONDITION_SPLITTER = "=";
    public static final String MODEL = "remote";
    public static final String PROTOCOL_SPLITTER = "\\.";
    /**
     * System
     */
    public static final Integer SPOUT_PARALLELISM = FlowWriteConfigurations.getIntProperty(0, "spout.parallelism");
    public static final Integer COMPLETION_BOLT_PARALLELISM = FlowWriteConfigurations.getIntProperty(0, "completion.bolt.parallelism");
    public static final Integer TOPOLOGY_WORKERS = FlowWriteConfigurations.getIntProperty(0, "topology.workers");
    public static final Integer KAFKA_BOLT_PARALLELISM = FlowWriteConfigurations.getIntProperty(0, "kafka.bolt.parallelism");
    public static final Integer TOPOLOGY_TICK_TUPLE_FREQ_SECS = FlowWriteConfigurations.getIntProperty(0, "topology.tick.tuple.freq.secs");
    public static final Integer HBASE_TICK_TUPLE_FREQ_SECS = FlowWriteConfigurations.getIntProperty(0, "hbase.tick.tuple.freq.secs");
    public static final Integer APP_TICK_TUPLE_FREQ_SECS = FlowWriteConfigurations.getIntProperty(0, "app.tick.tuple.freq.secs");
    public static final Integer TOPOLOGY_CONFIG_MAX_SPOUT_PENDING = FlowWriteConfigurations.getIntProperty(0, "topology.config.max.spout.pending");
    public static final Integer TOPOLOGY_NUM_ACKS = FlowWriteConfigurations.getIntProperty(0, "topology.num.acks");
    public static final Integer TOPOLOGY_SPOUT_SLEEP_TIME = FlowWriteConfigurations.getIntProperty(0, "topology.spout.sleep.time");
    public static final Integer BATCH_INSERT_NUM = FlowWriteConfigurations.getIntProperty(0, "batch.insert.num");
    public static final Integer DATA_CENTER_ID_NUM = FlowWriteConfigurations.getIntProperty(0, "data.center.id.num");
    public static final Integer MAX_FAILURE_NUM = FlowWriteConfigurations.getIntProperty(0, "max.failure.num");
    public static final String MAIL_DEFAULT_CHARSET = FlowWriteConfigurations.getStringProperty(0, "mail.default.charset");
    public static final String LOG_NEED_COMPLETE = FlowWriteConfigurations.getStringProperty(0, "log.need.complete");

    /**
     * kafka
     */
    public static final String INPUT_KAFKA_SERVERS = FlowWriteConfigurations.getStringProperty(0, "input.kafka.servers");
    public static final String OUTPUT_KAFKA_SERVERS = FlowWriteConfigurations.getStringProperty(0, "output.kafka.servers");
    public static final String ZOOKEEPER_SERVERS = FlowWriteConfigurations.getStringProperty(0, "zookeeper.servers");
    public static final String HBASE_ZOOKEEPER_SERVERS = FlowWriteConfigurations.getStringProperty(0, "hbase.zookeeper.servers");
    public static final String HBASE_TABLE_NAME = FlowWriteConfigurations.getStringProperty(0, "hbase.table.name");
    public static final String GROUP_ID = FlowWriteConfigurations.getStringProperty(0, "group.id");
    public static final String RESULTS_OUTPUT_TOPIC = FlowWriteConfigurations.getStringProperty(0, "results.output.topic");
    public static final String KAFKA_TOPIC = FlowWriteConfigurations.getStringProperty(0, "kafka.topic");
    public static final String AUTO_OFFSET_RESET = FlowWriteConfigurations.getStringProperty(0, "auto.offset.reset");
    public static final String PRODUCER_ACK = FlowWriteConfigurations.getStringProperty(0, "producer.ack");

    public static final String IP_LIBRARY = FlowWriteConfigurations.getStringProperty(0, "ip.library");

    /**
     * kafka限流配置-20201117
     */
    public static final String PRODUCER_KAFKA_COMPRESSION_TYPE = FlowWriteConfigurations.getStringProperty(0, "producer.kafka.compression.type");
    public static final String CONSUMER_CLIENT_ID = FlowWriteConfigurations.getStringProperty(0, "consumer.client.id");
    public static final String PRODUCER_CLIENT_ID = FlowWriteConfigurations.getStringProperty(0, "producer.client.id");
    /**
     * http
     */
    public static final String SCHEMA_HTTP = FlowWriteConfigurations.getStringProperty(0, "schema.http");
    public static final String APP_ID_HTTP = FlowWriteConfigurations.getStringProperty(0, "app.id.http");


}