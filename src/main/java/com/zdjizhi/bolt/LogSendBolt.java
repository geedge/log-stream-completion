package com.zdjizhi.bolt;

import com.zdjizhi.common.FlowWriteConfig;
import com.zdjizhi.utils.kafka.KafkaLogSend;
import com.zdjizhi.utils.system.TupleUtils;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.zdjizhi.utils.StringUtil;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author qidaijie
 * @date 2018/8/14
 */
public class LogSendBolt extends BaseBasicBolt {
    private static final long serialVersionUID = -3663610927224396615L;
    private static final Log logger = LogFactory.get();
    private List<String> list = new LinkedList<>();
    private KafkaLogSend kafkaLogSend;


    @Override
    public void prepare(Map stormConf, TopologyContext context) {
//        list = new LinkedList<>();
        kafkaLogSend = KafkaLogSend.getInstance();
    }

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        try {
            if (TupleUtils.isTick(tuple)) {
                if (list.size() != 0) {
                    kafkaLogSend.sendMessage(list);
                    list.clear();
                }
            } else {
                String message = tuple.getValue(0).toString();
                if (StringUtil.isNotBlank(message)) {
                    list.add(message);
                }
                if (list.size() == FlowWriteConfig.BATCH_INSERT_NUM) {
                    kafkaLogSend.sendMessage(list);
                    list.clear();
                }
            }
        } catch (RuntimeException e) {
            logger.error("补全日志发送Kafka过程出现异常,异常信息:" + e);
        }
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        Map<String, Object> conf = new HashMap<>(16);
        conf.put(org.apache.storm.Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, FlowWriteConfig.TOPOLOGY_TICK_TUPLE_FREQ_SECS);
        return conf;
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
    }

}
