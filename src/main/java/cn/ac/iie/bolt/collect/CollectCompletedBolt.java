package cn.ac.iie.bolt.collect;

import cn.ac.iie.common.FlowWriteConfig;
import cn.ac.iie.utils.hbase.HBaseUtils;
import cn.ac.iie.utils.system.TupleUtils;
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
import java.util.Map;


import static cn.ac.iie.utils.general.TransFormUtils.dealCommonMessage;



/**
 * 通联关系日志补全
 *
 * @author qidaijie
 */
public class CollectCompletedBolt extends BaseBasicBolt {
    private final static Logger logger = Logger.getLogger(CollectCompletedBolt.class);
    private static final long serialVersionUID = 4682827168247333522L;

    @Override
    public void prepare(Map stormConf, TopologyContext context) {
    }

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        try {
            if (TupleUtils.isTick(tuple)) {
                HBaseUtils.change();
            } else {
                String message = tuple.getString(0);
                if (StringUtil.isNotBlank(message)) {
                    basicOutputCollector.emit(new Values(dealCommonMessage(message)));
                }
            }
        } catch (Exception e) {
            logger.error(FlowWriteConfig.KAFKA_TOPIC + "接收/解析过程出现异常");
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        Map<String, Object> conf = new HashMap<String, Object>(16);
        conf.put(org.apache.storm.Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS,
                FlowWriteConfig.HBASE_TICK_TUPLE_FREQ_SECS);
        return conf;
    }


    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("connLog"));
    }

}
