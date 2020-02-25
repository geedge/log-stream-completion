package cn.ac.iie.utils.general;


import cn.ac.iie.common.FlowWriteConfig;
import cn.ac.iie.utils.hbase.HBaseUtils;
import cn.ac.iie.utils.json.JsonParseUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.net.InternetDomainName;
import com.zdjizhi.utils.FormatUtils;
import com.zdjizhi.utils.IpLookup;
import com.zdjizhi.utils.StringUtil;
import org.apache.log4j.Logger;

import java.util.*;



/**
 * 描述:转换或补全工具类
 *
 * @author qidaijie
 * @create 2018-08-13 15:11
 */

public class TransFormUtils {
    private static Logger logger = Logger.getLogger(TransFormUtils.class);

    //在内存中加载反射类用的map
    private static HashMap<String, Class> map = JsonParseUtil.getMapFromhttp(FlowWriteConfig.SCHEMA_HTTP);
    //反射成一个类
    private static Object mapObject = JsonParseUtil.generateObject(map);
    //获取任务列表
    private static ArrayList<String[]> jobList = JsonParseUtil.getJobListFromHttp(FlowWriteConfig.SCHEMA_HTTP);
    //补全工具类
    private static FormatUtils build = new FormatUtils.Builder(false).build();
    //IP定位库工具类
    private static IpLookup ipLookup = new IpLookup.Builder(false)
            .loadDataFileV4(FlowWriteConfig.IP_LIBRARY + "Kazakhstan.mmdb")
            .loadDataFileV6(FlowWriteConfig.IP_LIBRARY + "Kazakhstan.mmdb")
            .loadAsnDataFileV4(FlowWriteConfig.IP_LIBRARY + "asn_v4.mmdb")
            .loadAsnDataFileV6(FlowWriteConfig.IP_LIBRARY + "asn_v6.mmdb")
            .build();
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
                    JsonParseUtil.setValue(object, strings[1], getCurrentTime());
                } else if (function.equals("snowflake_id")) {
                    JsonParseUtil.setValue(object, strings[1], build.getSnowflakeId(FlowWriteConfig.ZOOKEEPER_SERVERS,FlowWriteConfig.KAFKA_TOPIC,FlowWriteConfig.DATA_CENTER_ID_NUM));
                } else if (function.equals("geo_ip_detail")) {
                    JsonParseUtil.setValue(object, strings[1], getGeoIpDetail(name.toString()));
                } else if (function.equals("geo_asn")) {
                    JsonParseUtil.setValue(object, strings[1], getGeoAsn(name.toString()));
                } else if (function.equals("radius_match")) {
                    JsonParseUtil.setValue(object, strings[1], radiusMatch(name.toString()));
                } else if (function.equals("geo_ip_country")) {
                    JsonParseUtil.setValue(object, strings[1], getGeoIpCountry(name.toString()));
                } else if (function.equals("decode_of_base64") && param != null){
                    JsonParseUtil.setValue(object, strings[1], FormatUtils.base64Str(name.toString(),param.toString()));
                } else if (name.equals("http_host") && function.equals("sub_domain")) {
                    if (appendTo == null || StringUtil.isBlank(appendTo.toString())) {
                        JsonParseUtil.setValue(object, strings[1], getTopDomain(null, name.toString()));
                    }
                } else if (name.equals("ssl_sni") && strings[2].equals("sub_domain")) {
                    if (appendTo == null || StringUtil.isBlank(appendTo.toString())) {
                        JsonParseUtil.setValue(object, strings[1], getTopDomain(name.toString(), null));
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


//    @Test
//    public void aaa() {
//        String sni = "www.baidu.com";
//        System.out.println(getTopDomain(sni, null));
//        System.out.println(getTopDomain(null,sni));
//
//    }

    /**
     * 有sni通过sni获取域名，有host根据host获取域名
     *
     * @param sni  sni
     * @param host host
     * @return 顶级域名
     */
    private static String getTopDomain(String sni, String host) {
        if (StringUtil.isNotBlank(host)) {
            return getDomainName(host);
        } else if (StringUtil.isNotBlank(sni)) {
            return getDomainName(sni);
        } else {
            return "";
        }
    }


    /**
     * 根据url截取顶级域名
     *
     * @param host 网站url
     * @return 顶级域名
     */
    private static String getDomainName(String host) {
        String domain = "";
        try {
            domain = InternetDomainName.from(host).topPrivateDomain().toString();
        } catch (Exception e) {
            logger.error("host解析顶级域名异常: " + e.getMessage());
        }
        return domain;
    }

    /**
     * 生成当前时间戳的操作
     */
    private static int getCurrentTime() {
        return (int)(System.currentTimeMillis() / 1000);
    }


    /**
     * 根据clientIp获取location信息
     *
     * @param ip
     * @return
     */
    private static String getGeoIpDetail(String ip) {

        return ipLookup.cityLookupDetail(ip);
    }

    /**
     * 根据ip获取asn信息
     *
     * @param ip
     * @return
     */
    private static String getGeoAsn(String ip) {

        return ipLookup.asnLookup(ip, true);
    }

    /**
     * 根据ip获取country信息
     *
     * @param ip
     * @return
     */
    private static String getGeoIpCountry(String ip) {

        return ipLookup.countryLookup(ip);
    }

    /**
     * radius借助hbase补齐
     *
     * @param ip
     * @return
     */
    private static String radiusMatch(String ip) {
        return HBaseUtils.getAccount(ip);
    }
}