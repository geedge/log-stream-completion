package cn.ac.iie.utils.general;

import cn.ac.iie.bean.*;
import cn.ac.iie.common.FlowWriteConfig;
import cn.ac.iie.utils.hbase.HbaseUtils;
import cn.ac.iie.utils.system.SnowflakeId;
import com.alibaba.fastjson.JSONObject;
import com.zdjizhi.utils.IpLookup;
import com.zdjizhi.utils.StringUtil;
import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 描述:转换或补全工具类
 *
 * @author qidaijie
 * @create 2018-08-13 15:11
 */
@SuppressWarnings("all")
public class TransFormUtils {
    private static Logger logger = Logger.getLogger(TransFormUtils.class);
    private static Pattern WEB_PATTERN = Pattern.compile("[^\\\\.]+(\\.com\\.cn|\\.net\\.cn|\\.org\\.cn|\\.gov\\.cn|\\.com|\\.net|\\.cn|\\.org|\\.cc|\\.me|\\.tel|\\.mobi|\\.asia|\\.biz|\\.info|\\.name|\\.tv|\\.hk|\\.公司|\\.中国|\\.网络)");
    private static IpLookup ipLookup = new IpLookup.Builder(false)
            .loadDataFileV4(FlowWriteConfig.IP_LIBRARY + "Kazakhstan.mmdb")
            .loadDataFileV6(FlowWriteConfig.IP_LIBRARY + "Kazakhstan.mmdb")
            .loadAsnDataFileV4(FlowWriteConfig.IP_LIBRARY + "asn_v4.mmdb")
            .loadAsnDataFileV6(FlowWriteConfig.IP_LIBRARY + "asn_v6.mmdb")
            .build();


    /**
     * 解析日志，并补全
     * 补subscriber_id，不补domain
     * @param message radius原始日志
     * @return 补全后的日志
     */
    public static String getRadiusMessage(String message) {
        RadiusSessionRecordLog radiusSessionRecordLog = JSONObject.parseObject(message, RadiusSessionRecordLog.class);
        String serverIp = radiusSessionRecordLog.getCommon_server_ip();
        String clientIp = radiusSessionRecordLog.getCommon_client_ip();
        try {
            radiusSessionRecordLog.setCommon_log_id(SnowflakeId.generateId());
            radiusSessionRecordLog.setCommon_recv_time((System.currentTimeMillis() / 1000));
            radiusSessionRecordLog.setCommon_server_location(ipLookup.countryLookup(serverIp));
            radiusSessionRecordLog.setCommon_client_location(ipLookup.cityLookupDetail(clientIp));
            radiusSessionRecordLog.setCommon_client_asn(ipLookup.asnLookup(clientIp, true));
            radiusSessionRecordLog.setCommon_server_asn(ipLookup.asnLookup(serverIp, true));
            return JSONObject.toJSONString(radiusSessionRecordLog);
        } catch (Exception e) {
            logger.error(FlowWriteConfig.KAFKA_TOPIC + "日志解析过程出现异常", e);
            return "";
        }
    }






    /**
     * 解析日志，并补全
     * 补domain,补subscriber_id
     * @param message Proxy原始日志
     * @return 补全后的日志
     */
    public static String getProxyMessage(String message) {
        ProxySessionRecordLog proxySessionRecordLog = JSONObject.parseObject(message, ProxySessionRecordLog.class);
        String serverIp = proxySessionRecordLog.getCommon_server_ip();
        String clientIp = proxySessionRecordLog.getCommon_client_ip();
        try {
            proxySessionRecordLog.setCommon_log_id(SnowflakeId.generateId());
            proxySessionRecordLog.setCommon_recv_time((System.currentTimeMillis() / 1000));
            proxySessionRecordLog.setCommon_server_location(ipLookup.countryLookup(serverIp));
            proxySessionRecordLog.setCommon_client_location(ipLookup.cityLookupDetail(clientIp));
            proxySessionRecordLog.setCommon_client_asn(ipLookup.asnLookup(clientIp, true));
            proxySessionRecordLog.setCommon_server_asn(ipLookup.asnLookup(serverIp, true));
            //TODO 集成AAA数据subscribe_id -数据端补全
            proxySessionRecordLog.setCommon_subscriber_id(HbaseUtils.getAccount(clientIp));
//            proxySessionRecordLog.setCommon_subscriber_id("aaaaaaa");
            proxySessionRecordLog.setHttp_domain(getTopDomain(null, proxySessionRecordLog.getHttp_host()));
            return JSONObject.toJSONString(proxySessionRecordLog);
        } catch (Exception e) {
            logger.error(FlowWriteConfig.KAFKA_TOPIC + "日志解析过程出现异常", e);
            return "";
        }
    }



    /**
     * 解析日志，并补全
     * 补domain,补subscriber_id
     * @param message Security原始日志
     * @return 补全后的日志
     */
    public static String getSecurityMessage(String message) {
        SecurityPolicyLog securitySessionRecordLog = JSONObject.parseObject(message, SecurityPolicyLog.class);
        String serverIp = securitySessionRecordLog.getCommon_server_ip();
        String clientIp = securitySessionRecordLog.getCommon_client_ip();
        try {
            securitySessionRecordLog.setCommon_log_id(SnowflakeId.generateId());
            securitySessionRecordLog.setCommon_recv_time((System.currentTimeMillis() / 1000));
            securitySessionRecordLog.setCommon_server_location(ipLookup.countryLookup(serverIp));
            securitySessionRecordLog.setCommon_client_location(ipLookup.cityLookupDetail(clientIp));
            securitySessionRecordLog.setCommon_client_asn(ipLookup.asnLookup(clientIp, true));
            securitySessionRecordLog.setCommon_server_asn(ipLookup.asnLookup(serverIp, true));
            //TODO 集成AAA数据subscribe_id -数据端补全
            securitySessionRecordLog.setCommon_subscriber_id(HbaseUtils.getAccount(clientIp));
//            securitySessionRecordLog.setCommon_subscriber_id("aaaaa");
            securitySessionRecordLog.setHttp_domain(getTopDomain(securitySessionRecordLog.getSsl_sni(), securitySessionRecordLog.getHttp_host()));
            return JSONObject.toJSONString(securitySessionRecordLog);
        } catch (Exception e) {
            logger.error(FlowWriteConfig.KAFKA_TOPIC + "日志解析过程出现异常", e);
            return "";
        }
    }

    /**
     * 解析日志，并补全
     * 补domain,补subscriber_id
     * @param message Security原始日志
     * @return 补全后的日志
     */
    public static String getCollectProtocolMessage(String message) {
        CollectProtocolRecordLog securitySessionRecordLog = JSONObject.parseObject(message, CollectProtocolRecordLog.class);
        String serverIp = securitySessionRecordLog.getCommon_server_ip();
        String clientIp = securitySessionRecordLog.getCommon_client_ip();
        try {
            securitySessionRecordLog.setCommon_log_id(SnowflakeId.generateId());
            securitySessionRecordLog.setCommon_recv_time((System.currentTimeMillis() / 1000));
            securitySessionRecordLog.setCommon_server_location(ipLookup.countryLookup(serverIp));
            securitySessionRecordLog.setCommon_client_location(ipLookup.cityLookupDetail(clientIp));
            securitySessionRecordLog.setCommon_client_asn(ipLookup.asnLookup(clientIp, true));
            securitySessionRecordLog.setCommon_server_asn(ipLookup.asnLookup(serverIp, true));
            //TODO 集成AAA数据subscribe_id -数据端补全
            securitySessionRecordLog.setCommon_subscriber_id(HbaseUtils.getAccount(clientIp));
//            securitySessionRecordLog.setCommon_subscriber_id("aaaaa");
            securitySessionRecordLog.setHttp_domain(getTopDomain(securitySessionRecordLog.getSsl_sni(), securitySessionRecordLog.getHttp_host()));
            return JSONObject.toJSONString(securitySessionRecordLog);
        } catch (Exception e) {
            logger.error(FlowWriteConfig.KAFKA_TOPIC + "日志解析过程出现异常", e);
            return "";
        }
    }

    /**
     * 有sni通过sni获取域名，有hots根据host获取域名
     *
     * @param sni  sni
     * @param host host
     * @return 顶级域名
     */
    public static String getTopDomain(String sni, String host) {
        if (StringUtil.isNotBlank(sni)) {
            return getDomain(sni);
        } else if (StringUtil.isNotBlank(host)) {
            return getDomain(host);
        } else {
            return "";
        }
    }


    /**
     * 根据url截取顶级域名
     *
     * @param url 网站url
     * @return 顶级域名
     */
    private static String getDomain(String url) {
        try {
            Matcher matcher = WEB_PATTERN.matcher(url);
            if (matcher.find()) {
                return matcher.group();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        String s = ipLookup.cityLookupDetail("2001:470:19:790::24");
        System.out.println(s);
    }


}
