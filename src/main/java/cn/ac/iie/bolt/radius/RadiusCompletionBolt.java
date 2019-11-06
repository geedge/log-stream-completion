package cn.ac.iie.bolt.radius;

import com.zdjizhi.utils.StringUtil;
import org.apache.log4j.Logger;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

import static cn.ac.iie.utils.general.TransFormUtils.getRadiusMessage;

/**
 * 通联关系日志补全
 *
 * @author qidaijie
 */
public class RadiusCompletionBolt extends BaseBasicBolt {
    private static final long serialVersionUID = -3657802387129063952L;
    private final static Logger logger = Logger.getLogger(RadiusCompletionBolt.class);

    @Override
    public void prepare(Map stormConf, TopologyContext context) {

    }

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        try {
            String message = tuple.getString(0);
            if (StringUtil.isNotBlank(message)) {
                basicOutputCollector.emit(new Values(getRadiusMessage(message)));
            }
        } catch (Exception e) {
            logger.error("接收解析过程出现异常", e);
        }
    }


    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("connLog"));
    }

}
