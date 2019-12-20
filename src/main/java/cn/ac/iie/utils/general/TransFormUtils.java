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
import org.junit.jupiter.api.Test;

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



//        message="{\"ssl_sni\":\"pos.baidu.com\",\"ssl_version\":\"v3\",\"ssl_cn\":\"baidu.com\",\"ssl_san\":\"baidu.com;click.hm.baidu.com;cm.pos.baidu.com;log.hm.baidu.com;update.pan.baidu.com;wn.pos.baidu.com;*.91.com;*.aipage.cn;*.aipage.com;*.apollo.auto;*.baidu.com;*.baidubce.com;*.baiducontent.com;*.baidupcs.com;*.baidustatic.com;*.baifubao.com;*.bce.baidu.com;*.bcehost.com;*.bdimg.com;*.bdstatic.com;*.bdtjrcv.com;*.bj.baidubce.com;*.chuanke.com;*.dlnel.com;*.dlnel.org;*.dueros.baidu.com;*.eyun.baidu.com;*.fanyi.baidu.com;*.gz.baidubce.com;*.hao123.baidu.com;*.hao123.com;*.hao222.com;*.haokan.com;*.im.baidu.com;*.map.baidu.com;*.mbd.baidu.com;*.mipcdn.com;*.news.baidu.com;*.nuomi.com;*.safe.baidu.com;*.smartapps.cn;*.su.baidu.com;*.trustgo.com;*.xueshu.baidu.com;apollo.auto;baifubao.com;dwz.cn;mct.y.nuomi.com;www.baidu.cn;www.baidu.com.cn\",\"common_schema_type\":\"SSL\",\"common_server_ip\":\"182.61.200.109\",\"common_client_ip\":\"192.168.50.144\",\"common_server_port\":443,\"common_client_port\":50529,\"common_stream_dir\":3,\"common_address_type\":4,\"common_s2c_pkt_num\":46,\"common_s2c_byte_num\":33149,\"common_c2s_pkt_num\":23,\"common_c2s_byte_num\":6147,\"common_start_time\":1576744784,\"common_end_time\":1576744799,\"common_con_duration_ms\":15000,\"common_stream_trace_id\":7686307990192,\"common_l4_protocol\":\"IPv4_TCP\",\"common_address_list\":\"50529-443-192.168.50.144-182.61.200.109\",\"common_sled_ip\":\"192.168.40.21\",\"common_policy_id\":172,\"common_service\":0,\"common_action\":2,\"common_user_region\":\"{\\\"protocol\\\":\\\"SSL\\\",\\\"protocol_version\\\":{\\\"allow_http2\\\":1,\\\"min\\\":\\\"ssl3\\\",\\\"max\\\":\\\"tls13\\\",\\\"mirror_client\\\":1},\\\"dynamic_bypass\\\":{\\\"mutual_authentication\\\":1,\\\"cert_pinning\\\":1,\\\"cert_transparency\\\":0,\\\"protocol_errors\\\":1,\\\"ev_cert\\\":0},\\\"decrypt_mirror\\\":{\\\"enable\\\":0},\\\"certificate_checks\\\":{\\\"fail_action\\\":\\\"pass-through\\\",\\\"approach\\\":{\\\"self-signed\\\":1,\\\"expiration\\\":1,\\\"cn\\\":1,\\\"issuer\\\":1}},\\\"keyring\\\":1}\"}";
        Object object = JSONObject.parseObject(message, mapObject.getClass());

//        System.out.println("补全之前 ===》 "+JSON.toJSONString(object));
        try {
            for (String[] strings : jobList) {
                //参数的值
                Object use = JsonParseUtil.getValue(object, strings[0]);
                //补全的字段的值
                Object appendTo = JsonParseUtil.getValue(object, strings[1]);

                if (strings[2].equals("current_timestamp")) {
                    JsonParseUtil.setValue(object, strings[1], getCurrentTime());
                } else if (strings[2].equals("snowflake_id")) {
                    JsonParseUtil.setValue(object, strings[1], getSnowflakeId());
                } else if (strings[2].equals("geo_ip_detail")) {
                    JsonParseUtil.setValue(object, strings[1], getGeoIpDetail(use.toString()));
                } else if (strings[2].equals("geo_asn")) {
                    JsonParseUtil.setValue(object, strings[1], getGeoAsn(use.toString()));
                } else if (strings[2].equals("radius_match")) {
                    JsonParseUtil.setValue(object, strings[1], radiusMatch(use.toString()));
                } else if (strings[2].equals("geo_ip_country")) {
                    JsonParseUtil.setValue(object, strings[1], getGeoIpCountry(use.toString()));
                } else if (strings[0].equals("http_host") && strings[2].equals("sub_domain") && use != null) {
                    if (appendTo == null || StringUtil.isBlank(appendTo.toString())) {
                        JsonParseUtil.setValue(object, strings[1], getTopDomain(null, use.toString()));
                    }
                } else if (strings[0].equals("ssl_sni") && strings[2].equals("sub_domain") && use != null) {
                    if (appendTo == null || StringUtil.isBlank(appendTo.toString())) {
                        JsonParseUtil.setValue(object, strings[1], getTopDomain(use.toString(), null));
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
        String sni = "203.187.160.131:9011";
        System.out.println(getTopDomain(sni, null));
        System.out.println(getTopDomain(null,sni));

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
    private static int getCurrentTime() {
        return (int)(System.currentTimeMillis() / 1000);
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
