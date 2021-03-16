package com.zdjizhi.topology;


import com.zdjizhi.bolt.CompletionBolt;
import com.zdjizhi.bolt.kafka.LogSendBolt;
import com.zdjizhi.common.FlowWriteConfig;
import com.zdjizhi.spout.CustomizedKafkaSpout;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
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
    private static final Log logger = LogFactory.get();
    private final String topologyName;
    private final Config topologyConfig;
    private TopologyBuilder builder;

    private LogFlowWriteTopology() {
        this(LogFlowWriteTopology.class.getSimpleName());
    }

    private LogFlowWriteTopology(String topologyName) {
        this.topologyName = topologyName;
        topologyConfig = createTopologyConfig();
    }

    private Config createTopologyConfig() {
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
        String need = "yes";
        builder = new TopologyBuilder();
        builder.setSpout("LogFlowWriteSpout", new CustomizedKafkaSpout(), FlowWriteConfig.SPOUT_PARALLELISM);
        if (need.equals(FlowWriteConfig.LOG_NEED_COMPLETE)) {
            builder.setBolt("LogCompletionBolt", new CompletionBolt(),
                    FlowWriteConfig.COMPLETION_BOLT_PARALLELISM).localOrShuffleGrouping("LogFlowWriteSpout");
            builder.setBolt("CompletionLogSendBolt", new LogSendBolt(),
                    FlowWriteConfig.KAFKA_BOLT_PARALLELISM).localOrShuffleGrouping("LogCompletionBolt");
        } else {
            builder.setBolt("LogSendBolt", new LogSendBolt(),
                    FlowWriteConfig.KAFKA_BOLT_PARALLELISM).localOrShuffleGrouping("LogFlowWriteSpout");
        }
    }

    public static void main(String[] args) throws Exception {
        LogFlowWriteTopology flowWriteTopology;
        boolean runLocally = true;
        int size = 2;
        if (args.length >= size && FlowWriteConfig.MODEL.equalsIgnoreCase(args[1])) {
            runLocally = false;
            flowWriteTopology = new LogFlowWriteTopology(args[0]);
        } else {
            flowWriteTopology = new LogFlowWriteTopology();
        }

        flowWriteTopology.buildTopology();

        if (runLocally) {
            logger.info("执行本地模式...");
            flowWriteTopology.runLocally();
        } else {
            logger.info("执行远程部署模式...");
            flowWriteTopology.runRemotely();
        }
    }
}
