package cn.ac.iie.utils.general;

import cn.ac.iie.bean.connection.ConnectionRecordLog;
import cn.ac.iie.bean.proxy.ProxySessionRecordLog;
import cn.ac.iie.bean.radius.RadiusSessionRecordLog;
import cn.ac.iie.bean.security.SecurityPolicyLog;
import cn.ac.iie.common.FlowWriteConfig;
import cn.ac.iie.utils.hbase.HBaseUtils;
import cn.ac.iie.utils.system.SnowflakeId;
import com.alibaba.fastjson.JSONObject;
import com.zdjizhi.utils.IpLookup;
import com.zdjizhi.utils.StringUtil;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
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


    /**
     * 解析日志，并补全
     * 补subscriber_id，不补domain
     *
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
            logger.error("{} 日志解析过程出现异常" + FlowWriteConfig.KAFKA_TOPIC);
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 解析日志，并补全
     * 补domain,补subscriber_id
     *
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
            proxySessionRecordLog.setCommon_subscriber_id(HBaseUtils.getAccount(clientIp));
            proxySessionRecordLog.setHttp_domain(getTopDomain(null, proxySessionRecordLog.getHttp_host()));
            return JSONObject.toJSONString(proxySessionRecordLog);
        } catch (Exception e) {
            logger.error(FlowWriteConfig.KAFKA_TOPIC + "日志解析过程出现异常");
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 解析日志，并补全
     * 补domain,补subscriber_id
     *
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
            securitySessionRecordLog.setCommon_subscriber_id(HBaseUtils.getAccount(clientIp));
            securitySessionRecordLog.setHttp_domain(getTopDomain(securitySessionRecordLog.getSsl_sni(), securitySessionRecordLog.getHttp_host()));
            return JSONObject.toJSONString(securitySessionRecordLog);
        } catch (Exception e) {
            logger.error(FlowWriteConfig.KAFKA_TOPIC + "日志解析过程出现异常");
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 解析日志，并补全
     * 补domain,补subscriber_id
     *
     * @param message CollectProtocol原始日志
     * @return 补全后的日志
     */
    public static String getCollectProtocolMessage(String message) {
        ConnectionRecordLog collectProtocolRecordLog = JSONObject.parseObject(message, ConnectionRecordLog.class);
        String serverIp = collectProtocolRecordLog.getCommon_server_ip();
        String clientIp = collectProtocolRecordLog.getCommon_client_ip();
        try {
            collectProtocolRecordLog.setCommon_log_id(SnowflakeId.generateId());
            collectProtocolRecordLog.setCommon_recv_time((System.currentTimeMillis() / 1000));
            collectProtocolRecordLog.setCommon_server_location(ipLookup.countryLookup(serverIp));
            collectProtocolRecordLog.setCommon_client_location(ipLookup.cityLookupDetail(clientIp));
            collectProtocolRecordLog.setCommon_client_asn(ipLookup.asnLookup(clientIp, true));
            collectProtocolRecordLog.setCommon_server_asn(ipLookup.asnLookup(serverIp, true));
            //TODO 集成AAA数据subscribe_id -数据端补全
            collectProtocolRecordLog.setCommon_subscriber_id(HBaseUtils.getAccount(clientIp));
            collectProtocolRecordLog.setHttp_domain(getTopDomain(collectProtocolRecordLog.getSsl_sni(), collectProtocolRecordLog.getHttp_host()));
            return JSONObject.toJSONString(collectProtocolRecordLog);
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
        if (host.endsWith(FlowWriteConfig.DOMAIN_SPLITTER)) {
            host = host.substring(0, host.length() - 1);
        }
        if (IP_PATTERN.matcher(host).matches()) {
            return host;
        }
        int index = 0;
        String candidate = host;
        for (; index >= 0; ) {
            index = candidate.indexOf(FlowWriteConfig.DOMAIN_SPLITTER);
            String subCandidate = candidate.substring(index + 1);
            if (PUBLIC_SUFFIX_SET.contains(subCandidate)) {
                return candidate;
            }
            candidate = subCandidate;
        }
        return candidate;
    }


}
