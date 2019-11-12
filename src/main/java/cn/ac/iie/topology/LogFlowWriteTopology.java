package cn.ac.iie.topology;


import cn.ac.iie.bolt.NtcLogSendBolt;
import cn.ac.iie.bolt.collectProtocol.CollectCompletedBolt;
import cn.ac.iie.bolt.radius.RadiusCompletionBolt;
import cn.ac.iie.bolt.security.SecurityCompletionBolt;

import cn.ac.iie.bolt.proxy.ProxyCompletionBolt;

import cn.ac.iie.common.FlowWriteConfig;
import cn.ac.iie.spout.CustomizedKafkaSpout;
import org.apache.log4j.Logger;
import org.apache.storm.Config;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;

/**
 * Storm程序主类
 *
 * @author Administrator
 */

public class LogFlowWriteTopology {
    private static Logger logger = Logger.getLogger(LogFlowWriteTopology.class);
    private final String topologyName;
    private final Config topologyConfig;
    private TopologyBuilder builder;

    private LogFlowWriteTopology() {
        this(LogFlowWriteTopology.class.getSimpleName());
    }

    private LogFlowWriteTopology(String topologyName) {
        this.topologyName = topologyName;
        topologyConfig = createTopologConfig();
    }

    private Config createTopologConfig() {
        Config conf = new Config();
        conf.setDebug(false);
        conf.setMessageTimeoutSecs(60);
        conf.setMaxSpoutPending(FlowWriteConfig.TOPOLOGY_CONFIG_MAX_SPOUT_PENDING);
        conf.setNumAckers(FlowWriteConfig.TOPOLOGY_NUM_ACKS);
        return conf;
    }

    private void runLocally() throws InterruptedException {
        topologyConfig.setMaxTaskParallelism(1);
        StormRunner.runTopologyLocally(builder, topologyName, topologyConfig, 600);
    }

    private void runRemotely() throws AlreadyAliveException, InvalidTopologyException, AuthorizationException {
        topologyConfig.setNumWorkers(FlowWriteConfig.TOPOLOGY_WORKERS);
        //设置过高会导致很多问题，如心跳线程饿死、吞吐量大幅下跌
        topologyConfig.put(Config.TOPOLOGY_EXECUTOR_RECEIVE_BUFFER_SIZE, 8);
        StormRunner.runTopologyRemotely(builder, topologyName, topologyConfig);
    }

    private void buildTopology() {
        builder = new TopologyBuilder();
        builder.setSpout("LogFlowWriteSpout", new CustomizedKafkaSpout(), FlowWriteConfig.SPOUT_PARALLELISM);

        String topic_name = FlowWriteConfig.KAFKA_TOPIC;

        switch(topic_name){
            case "PROXY-POLICY-LOG":
                builder.setBolt("ProxyCompletionBolt", new ProxyCompletionBolt(), FlowWriteConfig.DATACENTER_BOLT_PARALLELISM).localOrShuffleGrouping("LogFlowWriteSpout");
                builder.setBolt("NtcLogSendBolt", new NtcLogSendBolt(), FlowWriteConfig.KAFKA_BOLT_PARALLELISM).localOrShuffleGrouping("ProxyCompletionBolt");
                break;

            case "COLLECT-RADIUS-RECORD-LOG":
                builder.setBolt("RadiusCompletionBolt", new RadiusCompletionBolt(), FlowWriteConfig.DATACENTER_BOLT_PARALLELISM).localOrShuffleGrouping("LogFlowWriteSpout");
                builder.setBolt("NtcLogSendBolt", new NtcLogSendBolt(), FlowWriteConfig.KAFKA_BOLT_PARALLELISM).localOrShuffleGrouping("RadiusCompletionBolt");
                break;

            case "COLLECT-CONNECTION-RECORD-LOG":
                builder.setBolt("CollectCompletedBolt", new CollectCompletedBolt(), FlowWriteConfig.DATACENTER_BOLT_PARALLELISM).localOrShuffleGrouping("LogFlowWriteSpout");
                builder.setBolt("NtcLogSendBolt", new NtcLogSendBolt(), FlowWriteConfig.KAFKA_BOLT_PARALLELISM).localOrShuffleGrouping("CollectCompletedBolt");
                break;

            case "SECURITY-POLICY-LOG":
                builder.setBolt("SecurityCompletionBolt", new SecurityCompletionBolt(), FlowWriteConfig.DATACENTER_BOLT_PARALLELISM).localOrShuffleGrouping("LogFlowWriteSpout");
                builder.setBolt("NtcLogSendBolt", new NtcLogSendBolt(), FlowWriteConfig.KAFKA_BOLT_PARALLELISM).localOrShuffleGrouping("SecurityCompletionBolt");
                break;


            }

            /*if ("PROXY_POLICY_LOG".equals(FlowWriteConfig.KAFKA_TOPIC) || "COLLECT_HTTP_META_LOG".equals(FlowWriteConfig.KAFKA_TOPIC)) {
                builder.setBolt("HttpCompletionBolt", new HttpCompletionBolt(), FlowWriteConfig.DATACENTER_BOLT_PARALLELISM).localOrShuffleGrouping("LogFlowWriteSpout");
                builder.setBolt("NtcLogSendBolt", new NtcLogSendBolt(), FlowWriteConfig.KAFKA_BOLT_PARALLELISM).localOrShuffleGrouping("HttpCompletionBolt");
            } else if ("COLLECT_RADIUS_RECORD_LOG".equals(FlowWriteConfig.KAFKA_TOPIC)) {
                builder.setBolt("RadiusCompletionBolt", new ConnCompletionBolt(), FlowWriteConfig.DATACENTER_BOLT_PARALLELISM).localOrShuffleGrouping("LogFlowWriteSpout");
                builder.setBolt("NtcLogSendBolt", new NtcLogSendBolt(), FlowWriteConfig.KAFKA_BOLT_PARALLELISM).localOrShuffleGrouping("RadiusCompletionBolt");
            } else {
                builder.setBolt("ConnCompletionBolt", new ConnCompletionBolt(), FlowWriteConfig.DATACENTER_BOLT_PARALLELISM).localOrShuffleGrouping("LogFlowWriteSpout");
                builder.setBolt("NtcLogSendBolt", new NtcLogSendBolt(), FlowWriteConfig.KAFKA_BOLT_PARALLELISM).localOrShuffleGrouping("ConnCompletionBolt");
            }*/
//        builder.setBolt("SummaryBolt", new SummaryBolt(), 1).localOrShuffleGrouping("NtcLogSendBolt");
        }

    public static void main(String[] args) throws Exception {
        LogFlowWriteTopology csst = null;
        boolean runLocally = true;
        String parameter = "remote";
        int size = 2;
        if (args.length >= size && parameter.equalsIgnoreCase(args[1])) {
            runLocally = false;
            csst = new LogFlowWriteTopology(args[0]);
        } else {
            csst = new LogFlowWriteTopology();
        }

        csst.buildTopology();

        if (runLocally) {
            logger.info("执行本地模式...");
            csst.runLocally();
        } else {
            logger.info("执行远程部署模式...");
            csst.runRemotely();
        }
    }
}
