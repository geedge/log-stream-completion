package cn.ac.iie.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.net.InternetDomainName;
import org.apache.log4j.Logger;

public class test {
    private static Logger logger = Logger.getLogger(test.class);

    public static void main(String[] args) {
        String message = "{\"common_schema_type\":\"SSL\",\"ssl_sni\":\"www.myssl.cn\",\"common_server_ip\":\"101.37.81.250\",\"common_client_ip\":\"192.168.50.12\",\"common_server_port\":443,\"common_client_port\":51398,\"common_stream_dir\":3,\"common_address_type\":4,\"common_s2c_pkt_num\":1,\"common_s2c_byte_num\":0,\"common_c2s_pkt_num\":3,\"common_c2s_byte_num\":517,\"common_stream_trace_id\":7629423224647,\"common_l4_protocol\":\"IPv4_TCP\",\"common_address_list\":\"51398-443-192.168.50.12-101.37.81.250\",\"common_sled_ip\":\"192.168.40.161\",\"common_start_time\":1575008799,\"common_end_time\":1575008799,\"common_con_duration_ms\":0,\"common_policy_id\":282,\"common_service\":0,\"common_action\":128,\"common_user_region\":\"{\\\"protocol\\\":\\\"SSL\\\"}\"}";
        JSONObject jsonObject = JSON.parseObject(message);
        String common_server_ip = jsonObject.getString("common_server_ip");
        String common_client_ip = jsonObject.getString("common_client_ip");

        jsonObject.put("common__recv_time",(System.currentTimeMillis() / 1000));

        System.out.println(jsonObject.toString());

    }
}
