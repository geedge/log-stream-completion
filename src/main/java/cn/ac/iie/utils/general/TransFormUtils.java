package cn.ac.iie.utils.general;


import cn.ac.iie.common.FlowWriteConfig;
import cn.ac.iie.utils.json.JsonParseUtil;
import com.alibaba.fastjson.JSONObject;
import com.zdjizhi.utils.FormatUtils;
import com.zdjizhi.utils.IpLookup;
import com.zdjizhi.utils.StringUtil;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.util.*;



/**
 * 描述:转换或补全工具类
 *
 * @author qidaijie
 * @create 2018-08-13 15:11
 */

public class TransFormUtils {
    private static Logger logger = Logger.getLogger(TransFormUtils.class);

//    private static IpLookup ipLookup = FormatUtils.getIpLookup(FlowWriteConfig.IP_LIBRARY);

    private static IpLookup ipLookup = new IpLookup.Builder(false)
            .loadDataFileV4(FlowWriteConfig.IP_LIBRARY + "Kazakhstan.mmdb")
            .loadDataFileV6(FlowWriteConfig.IP_LIBRARY + "Kazakhstan.mmdb")
            .loadAsnDataFileV4(FlowWriteConfig.IP_LIBRARY + "asn_v4.mmdb")
            .loadAsnDataFileV6(FlowWriteConfig.IP_LIBRARY + "asn_v6.mmdb")
            .build();


    //在内存中加载反射类用的map
    private static HashMap<String, Class> map = JsonParseUtil.getMapFromhttp(FlowWriteConfig.SCHEMA_HTTP);
    //反射成一个类
    private static Object mapObject = JsonParseUtil.generateObject(map);
    //获取任务列表
    private static ArrayList<String[]> jobList = JsonParseUtil.getJobListFromHttp(FlowWriteConfig.SCHEMA_HTTP);

    /**
     * 解析日志，并补全
     *
     * @param message kafka Topic原始日志
     * @return 补全后的日志
     */
    public static String dealCommonMessage(String message) {

        Object object = JSONObject.parseObject(message, mapObject.getClass());

//        System.out.println("补全之前 ===》 "+JSON.toJSONString(object));
        try {
            for (String[] strings : jobList) {
                //用到的参数的值
                Object name = JsonParseUtil.getValue(object, strings[0]);
                //需要补全的字段的值
                Object appendTo = JsonParseUtil.getValue(object, strings[1]);
                //匹配操作函数的字段
                String function=strings[2];
                //额外的参数的值
                Object param = null;
                if (strings[3] != null){
                    param=JsonParseUtil.getValue(object, strings[3]);
                }


                if (function.equals("current_timestamp")) {
                    JsonParseUtil.setValue(object, strings[1], FormatUtils.getCurrentTime());
                } else if (function.equals("snowflake_id")) {
                    JsonParseUtil.setValue(object, strings[1], FormatUtils.getSnowflakeId(FlowWriteConfig.ZOOKEEPER_SERVERS,FlowWriteConfig.KAFKA_TOPIC,FlowWriteConfig.DATA_CENTER_ID_NUM));
                } else if (function.equals("geo_ip_detail")) {
                    JsonParseUtil.setValue(object, strings[1], FormatUtils.getGeoIpDetail(name.toString(),ipLookup));
                } else if (function.equals("geo_asn")) {
                    JsonParseUtil.setValue(object, strings[1], FormatUtils.getGeoAsn(name.toString(),ipLookup));
                } else if (function.equals("radius_match")) {
                    JsonParseUtil.setValue(object, strings[1], FormatUtils.radiusMatch(name.toString(),FlowWriteConfig.HBASE_ZOOKEEPER_SERVERS,FlowWriteConfig.HBASE_TABLE_NAME));
                } else if (function.equals("geo_ip_country")) {
                    JsonParseUtil.setValue(object, strings[1], FormatUtils.getGeoIpCountry(name.toString(),ipLookup));
                } else if (function.equals("decode_of_base64") && param != null){
                    JsonParseUtil.setValue(object, strings[1], FormatUtils.base64Str(name.toString(),param.toString()));
                } else if (name.equals("http_host") && function.equals("sub_domain")) {
                    if (appendTo == null || StringUtil.isBlank(appendTo.toString())) {
                        JsonParseUtil.setValue(object, strings[1], FormatUtils.getTopDomain(null, name.toString()));
                    }
                } else if (name.equals("ssl_sni") && strings[2].equals("sub_domain")) {
                    if (appendTo == null || StringUtil.isBlank(appendTo.toString())) {
                        JsonParseUtil.setValue(object, strings[1], FormatUtils.getTopDomain(name.toString(), null));
                    }

                }

            }


            return JSONObject.toJSONString(object);
//            System.out.println("补全之后 ===》 "+JSON.toJSONString(object));

        } catch (Exception e) {
            logger.error(FlowWriteConfig.KAFKA_TOPIC + "日志解析过程出现异常");
            e.printStackTrace();
            return "";
        }
    }


    @Test
    public void aaa() {
        String sni = "www.baidu.com";
        System.out.println(FormatUtils.getTopDomain(sni, null));
        System.out.println(FormatUtils.getTopDomain(null,sni));

    }
}