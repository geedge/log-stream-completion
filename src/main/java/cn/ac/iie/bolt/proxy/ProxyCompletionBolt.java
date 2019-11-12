package cn.ac.iie.bolt.proxy;

import cn.ac.iie.common.FlowWriteConfig;
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

import static cn.ac.iie.utils.general.TransFormUtils.getProxyMessage;
import static cn.ac.iie.utils.hbase.HbaseUtils.change;

/**
 * 通联关系日志补全
 *
 * @author qidaijie
 */
@SuppressWarnings("all")
public class ProxyCompletionBolt extends BaseBasicBolt {
    private static final long serialVersionUID = -1059151670838465894L;
    private final static Logger logger = Logger.getLogger(ProxyCompletionBolt.class);

    @Override
    public void prepare(Map stormConf, TopologyContext context) {

    }

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        try {
            if (TupleUtils.isTick(tuple)) {
                change();
            }else {
                String message = tuple.getString(0);
                if (StringUtil.isNotBlank(message)) {
                    basicOutputCollector.emit(new Values(getProxyMessage(message)));
                }
            }
        } catch (Exception e) {
            logger.error("接收解析过程出现异常", e);
        }
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        Map<String, Object> conf = new HashMap<String, Object>(16);
        conf.put(org.apache.storm.Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS,
                FlowWriteConfig.TOPOLOGY_TICK_TUPLE_FREQ_SECS);
        return conf;
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("connLog"));
    }

}
