package cn.ac.iie.test;

import cn.ac.iie.common.FlowWriteConfig;
import cn.ac.iie.utils.general.TransFormUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class test {
    private static Logger logger = Logger.getLogger(test.class);
    public static void main(String[] args) {
        String message = "{\"str_ea_m-t-r-a-ceid\":\"JSON\",\"uid\":\"0\"}";
//        SessionRecordLog sessionRecordLog = JSONObject.parseObject(message, SessionRecordLog.class);
//        System.out.println(JSONObject.toJSONString(sessionRecordLog));
        JSONObject obj = JSONObject.parseObject(message);
        obj.put("abc","bca");
        System.out.println(obj.toString());

    }

    @Test
    public void test2() {
        logger.error("{} 日志解析过程出现异常" + FlowWriteConfig.KAFKA_TOPIC);
    }
}
