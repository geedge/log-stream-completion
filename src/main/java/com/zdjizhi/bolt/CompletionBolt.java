package com.zdjizhi.bolt;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.zdjizhi.utils.StringUtil;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

import static com.zdjizhi.utils.general.TransFormUtils.dealCommonMessage;

/**
 * @author qidaijie
 */

public class CompletionBolt extends BaseBasicBolt {
    private static final long serialVersionUID = 9006119186526123734L;
    private static final Log logger = LogFactory.get();


    @Override
    public void prepare(Map stormConf, TopologyContext context) {

    }


    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        try {
            String message = tuple.getString(0);
            if (StringUtil.isNotBlank(message)) {
                basicOutputCollector.emit(new Values(dealCommonMessage(message)));
            }
        } catch (RuntimeException e) {
            logger.error("处理原始日志下发过程异常,异常信息：" + e);
        }
    }


    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("tsgLog"));
    }

}
