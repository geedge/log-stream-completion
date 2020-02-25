package cn.ac.iie.test;

import cn.ac.iie.bean.SessionRecordLog;
import cn.ac.iie.common.FlowWriteConfig;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zdjizhi.utils.IpLookup;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.Arrays;

public class test {
    public static void main(String[] args) {
        String message = "{\"str_ea_m-t-r-a-ceid\":\"JSON\"}";
        SessionRecordLog sessionRecordLog = JSONObject.parseObject(message, SessionRecordLog.class);
        System.out.println(sessionRecordLog.getStream_trace_id());

        String message2 = "{\"streamtraceid\":\"JSON\"}";
        SessionRecordLog sessionRecordLog2 = JSONObject.parseObject(message2, SessionRecordLog.class);
        System.out.println(sessionRecordLog2.getStream_trace_id());

        JSONObject jsonObject = JSON.parseObject(message);
        System.out.println("\n" + Arrays.toString(jsonObject.keySet().toArray()));

        HttpServletRequest request = null;
        if (request != null) {
            String contextPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        }

        System.out.println(System.currentTimeMillis() / 1000);
    }

    @Test
    public void test2() {
//        String minTimeStampStr = "00000000000000000000000000000000000000000";
        String minTimeStampStr = "000000000000000000000000000000000000000";
        long minTimeStamp = new BigInteger(minTimeStampStr, 2).longValue();
//        String maxTimeStampStr = "11111111111111111111111111111111111111111";
        String maxTimeStampStr = "111111111111111111111111111111111111111";
        long maxTimeStamp = new BigInteger(maxTimeStampStr, 2).longValue();
        long oneYearMills = 1L * 1000 * 60 * 60 * 24 * 365;
        System.out.println((maxTimeStamp - minTimeStamp) / oneYearMills);
    }
}
