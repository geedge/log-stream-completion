package cn.ac.iie.bolt;

import cn.ac.iie.common.FlowWriteConfig;
import cn.ac.iie.utils.influxdb.InfluxDbUtils;
import cn.ac.iie.utils.system.TupleUtils;
import org.apache.log4j.Logger;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;

import java.util.HashMap;
import java.util.Map;

/**
 * 统计总数bolt，用于将统计后的数入influxDB
 *
 * @author antlee
 * @date 2018/8/14
 */
public class SummaryBolt extends BaseBasicBolt {
    private static final long serialVersionUID = 4614020687381536301L;
    private static Logger logger = Logger.getLogger(SummaryBolt.class);
    private static long sum = 0L;

    @Override
    public void prepare(Map stormConf, TopologyContext context) {

    }

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        try {
            if (TupleUtils.isTick(tuple)) {
                InfluxDbUtils.sendKafkaSuccess(sum);
                sum = 0L;
            } else {
                long successfulSum = tuple.getLong(0);
                sum += successfulSum;
            }
        } catch (Exception e) {
            logger.error("计数写入influxDB出现异常 ", e);
            e.printStackTrace();
        }
    }


    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        Map<String, Object> conf = new HashMap<String, Object>(16);
        conf.put(org.apache.storm.Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, 60);
        return conf;
    }


}
