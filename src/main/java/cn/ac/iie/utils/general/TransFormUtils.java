package cn.ac.iie.utils.general;


import cn.ac.iie.common.FlowWriteConfig;
import cn.ac.iie.utils.hbase.HBaseUtils;
import cn.ac.iie.utils.json.JsonParseUtil;
import cn.ac.iie.utils.system.SnowflakeId;
import com.alibaba.fastjson.JSONObject;
import com.google.common.net.InternetDomainName;
import com.zdjizhi.utils.IpLookup;
import com.zdjizhi.utils.StringUtil;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.regex.Pattern;


/**
 * 描述:转换或补全工具类
 *
 * @author qidaijie
 * @create 2018-08-13 15:11
 */

public class TransFormUtils {
    private static Logger logger = Logger.getLogger(TransFormUtils.class);
    private final static Set<String> PUBLIC_SUFFIX_SET = new HashSet<String>(
            Arrays.asList("com|org|net|gov|edu|co|tv|mobi|info|asia|xxx|onion|cc|cn|com.cn|edu.cn|gov.cn|net.cn|org.cn|jp|kr|tw|com.hk|hk|com.hk|org.hk|se|com.se|org.se"
                    .split("\\|")));
    private static Pattern IP_PATTERN = Pattern.compile("(\\d{1,3}\\.){3}(\\d{1,3})");
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
     * 补domain,补subscriber_id
     *
     * @param message Security原始日志
     * @return 补全后的日志
     * <p>
     * current_timestamp
     * snowflake_id
     * geo_ip_detail
     * geo_asn
     * radius_match
     * geo_ip_country
     * geo_asn
     * sub_domain
     * sub_domain
     */
    public static String dealCommonMessage(String message) {

        Object object = JSONObject.parseObject(message, mapObject.getClass());
//        System.out.println("补全之前 ===》 "+JSON.toJSONString(object));
        try {
            for (String[] strings : jobList) {

                if (strings[2].equals("current_timestamp")) {
                    JsonParseUtil.setValue(object, strings[1], getCurrentTime());
                } else if (strings[2].equals("snowflake_id")) {
                    JsonParseUtil.setValue(object, strings[1], getSnowflakeId());
                } else if (strings[2].equals("geo_ip_detail")) {
                    JsonParseUtil.setValue(object, strings[1], getGeoIpDetail(JsonParseUtil.getValue(object, strings[0]).toString()));
                } else if (strings[2].equals("geo_asn")) {
                    JsonParseUtil.setValue(object, strings[1], getGeoIpDetail(JsonParseUtil.getValue(object, strings[0]).toString()));
                } else if (strings[2].equals("radius_match")) {
                    JsonParseUtil.setValue(object,strings[1],HBaseUtils.getAccount(JsonParseUtil.getValue(object,strings[0]).toString()));
//                    JsonParseUtil.setValue(object, strings[1], "aaaaaaaaa");
                } else if (strings[2].equals("geo_ip_country")) {
                    JsonParseUtil.setValue(object, strings[1], getGeoIpCountry(JsonParseUtil.getValue(object, strings[0]).toString()));
                } else if (strings[0].equals("http_host") && strings[2].equals("sub_domain")) {
                    if (JsonParseUtil.getValue(object, strings[1]) == null || StringUtil.isBlank(JsonParseUtil.getValue(object, strings[1]).toString())) {
//                        JsonParseUtil.setValue(object, strings[1], "baidu.com");
                        JsonParseUtil.setValue(object, strings[1], getTopDomain(null, JsonParseUtil.getValue(object, strings[0]).toString()));
                    }
                } else if (strings[0].equals("ssl_sni") && strings[2].equals("sub_domain")) {
                    if (JsonParseUtil.getValue(object, strings[1]) == null || StringUtil.isBlank(JsonParseUtil.getValue(object, strings[1]).toString())) {
                        JsonParseUtil.setValue(object,strings[1],getTopDomain(JsonParseUtil.getValue(object,strings[0]).toString(),null));
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
    private static long getCurrentTime() {
        return (System.currentTimeMillis() / 1000);
    }

    /**
     * 雪花模型生成id
     *
     * @return
     */
    private static long getSnowflakeId() {

        return SnowflakeId.generateId();
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

    /**
     * switch 匹配合适的方法
     * current_timestamp
     * snowflake_id
     * geo_ip_detail
     * geo_asn
     * radius_match
     * geo_ip_country
     * geo_asn
     * sub_domain
     * sub_domain
     * @param func
     */
//TODO 行不通的原因是无法统一一个确定的返回值类型
 /*   private static String switchFunc(String func){
        switch (func){
            case "current_timestamp":
                return String.valueOf(getCurrentTime());
            case "snowflake_id":
            case "geo_ip_detail":
            case "geo_asn":
            case "radius_match":
            case "geo_ip_country":
            case "sub_domain":
        }
        return func;
    }*/
}
