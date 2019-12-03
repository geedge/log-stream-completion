package cn.ac.iie.common;


import cn.ac.iie.utils.system.FlowWriteConfigurations;

/**
 * @author Administrator
 */
public class FlowWriteConfig {

    public static final int IPV4_TYPE = 1;
    public static final int IPV6_TYPE = 2;
    public static final String DOMAIN_SPLITTER = ".";
    /**
     * System
     */
    public static final Integer SPOUT_PARALLELISM = FlowWriteConfigurations.getIntProperty(0, "spout.parallelism");
    public static final Integer DATACENTER_BOLT_PARALLELISM = FlowWriteConfigurations.getIntProperty(0, "datacenter.bolt.parallelism");
    public static final Integer TOPOLOGY_WORKERS = FlowWriteConfigurations.getIntProperty(0, "topology.workers");
    public static final Integer KAFKA_BOLT_PARALLELISM = FlowWriteConfigurations.getIntProperty(0, "kafka.bolt.parallelism");
    public static final Integer TOPOLOGY_TICK_TUPLE_FREQ_SECS = FlowWriteConfigurations.getIntProperty(0, "topology.tick.tuple.freq.secs");
    public static final Integer HBASE_TICK_TUPLE_FREQ_SECS = FlowWriteConfigurations.getIntProperty(0, "hbase.tick.tuple.freq.secs");
    public static final Integer TOPOLOGY_CONFIG_MAX_SPOUT_PENDING = FlowWriteConfigurations.getIntProperty(0, "topology.config.max.spout.pending");
    public static final Integer TOPOLOGY_NUM_ACKS = FlowWriteConfigurations.getIntProperty(0, "topology.num.acks");
    public static final Integer TOPOLOGY_SPOUT_SLEEP_TIME = FlowWriteConfigurations.getIntProperty(0, "topology.spout.sleep.time");
    public static final Integer BATCH_INSERT_NUM = FlowWriteConfigurations.getIntProperty(0, "batch.insert.num");
    public static final Integer DATA_CENTER_ID_NUM = FlowWriteConfigurations.getIntProperty(0, "data.center.id.num");
    public static final String CHECK_IP_SCOPE = FlowWriteConfigurations.getStringProperty(0, "check.ip.scope");
    public static final Integer MAX_FAILURE_NUM = FlowWriteConfigurations.getIntProperty(0, "max.failure.num");

    /**
     * influxDB
     */
    public static final String INFLUX_IP = FlowWriteConfigurations.getStringProperty(0, "influx.ip");
    public static final String INFLUX_USERNAME = FlowWriteConfigurations.getStringProperty(0, "influx.username");
    public static final String INFLUX_PASSWORD = FlowWriteConfigurations.getStringProperty(0, "influx.password");

    /**
     * kafka
     */
    public static final String BOOTSTRAP_SERVERS = FlowWriteConfigurations.getStringProperty(0, "bootstrap.servers");
    public static final String ZOOKEEPER_SERVERS = FlowWriteConfigurations.getStringProperty(0, "zookeeper.servers");
    public static final String HBASE_ZOOKEEPER_SERVERS = FlowWriteConfigurations.getStringProperty(0, "hbase.zookeeper.servers");
    public static final String HBASE_TABLE_NAME = FlowWriteConfigurations.getStringProperty(0, "hbase.table.name");
    public static final String GROUP_ID = FlowWriteConfigurations.getStringProperty(0, "group.id");
    public static final String RESULTS_OUTPUT_TOPIC = FlowWriteConfigurations.getStringProperty(0, "results.output.topic");
    public static final String KAFKA_TOPIC = FlowWriteConfigurations.getStringProperty(0, "kafka.topic");
    public static final String AUTO_OFFSET_RESET = FlowWriteConfigurations.getStringProperty(0, "auto.offset.reset");

    public static final String IP_LIBRARY = FlowWriteConfigurations.getStringProperty(0, "ip.library");



}