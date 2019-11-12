package cn.ac.iie.test;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class test {
    public static void main(String[] args) {
        String message = "{\"str_ea_m-t-r-a-ceid\":\"JSON\",\"uid\":\"0\"}";
//        SessionRecordLog sessionRecordLog = JSONObject.parseObject(message, SessionRecordLog.class);
//        System.out.println(JSONObject.toJSONString(sessionRecordLog));
    }

    @Test
    public void test2() {
        Map<String,String> map = new HashMap<>();
        map.put("a","a");
        map.put("b","a");
        map.put("c","a");

        System.out.println(map.keySet());
    }
}
