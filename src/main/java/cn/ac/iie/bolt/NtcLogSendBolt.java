package cn.ac.iie.bolt;

import cn.ac.iie.common.FlowWriteConfig;
import cn.ac.iie.utils.influxdb.InfluxDbUtils;
import cn.ac.iie.utils.system.TupleUtils;
import cn.ac.iie.utils.kafka.KafkaLogNtc;
import com.zdjizhi.utils.StringUtil;
import org.apache.log4j.Logger;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author qidaijie
 * @date 2018/8/14
 */
public class NtcLogSendBolt extends BaseBasicBolt {
    private static final long serialVersionUID = -3663610927224396615L;
    private static Logger logger = Logger.getLogger(NtcLogSendBolt.class);
    private List<String> list;
    private KafkaLogNtc kafkaLogNtc;
//    private static long successfulSum = 0;


    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        list = new LinkedList<>();
        kafkaLogNtc = KafkaLogNtc.getInstance();
    }

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        try {
            if (TupleUtils.isTick(tuple)) {
                if (list.size() != 0) {
                    kafkaLogNtc.sendMessage(list);
//                    successfulSum += list.size();
                    list.clear();
                }
//                basicOutputCollector.emit(new Values(successfulSum));
//                successfulSum = 0L;
            } else {
                String message = tuple.getString(0);
                if (StringUtil.isNotBlank(message)) {
                    list.add(message);
                }
                if (list.size() == FlowWriteConfig.BATCH_INSERT_NUM) {
                    kafkaLogNtc.sendMessage(list);
//                    successfulSum += list.size();
                    list.clear();
                }
            }
        } catch (Exception e) {
            logger.error(FlowWriteConfig.KAFKA_TOPIC + "日志发送Kafka过程出现异常");
            e.printStackTrace();
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
//        outputFieldsDeclarer.declare(new Fields("suc"));
    }

}
