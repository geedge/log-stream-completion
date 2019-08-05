package cn.ac.iie.utils.general;

import cn.ac.iie.bean.SessionRecordLog;
import cn.ac.iie.common.FlowWriteConfig;
import cn.ac.iie.utils.redis.RedisPollUtils;
import cn.ac.iie.utils.system.SnowflakeId;
import com.alibaba.fastjson.JSONObject;
import com.zdjizhi.utils.IpLookup;
import com.zdjizhi.utils.StringUtil;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;

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
    private static Pattern WEB_PATTERN = Pattern.compile("[^\\\\.]+(\\.com\\.cn|\\.net\\.cn|\\.org\\.cn|\\.gov\\.cn|\\.com|\\.net|\\.cn|\\.org|\\.cc|\\.me|\\.tel|\\.mobi|\\.asia|\\.biz|\\.info|\\.name|\\.tv|\\.hk|\\.公司|\\.中国|\\.网络)");
    private static IpLookup ipLookup = new IpLookup.Builder(false)
            .loadDataFileV4(FlowWriteConfig.IP_LIBRARY + "Kazakhstan.mmdb")
            .loadDataFileV6(FlowWriteConfig.IP_LIBRARY + "Kazakhstan.mmdb")
            .loadAsnDataFileV4(FlowWriteConfig.IP_LIBRARY + "asn_v4.mmdb")
            .loadAsnDataFileV6(FlowWriteConfig.IP_LIBRARY + "asn_v6.mmdb")
            .build();

    /**
     * 解析日志，并补全
     *
     * @param message 原始日志
     * @return 补全后的日志
     */
    public static String getJsonMessage(String message) {
        SessionRecordLog sessionRecordLog = JSONObject.parseObject(message, SessionRecordLog.class);
        String serverIp = sessionRecordLog.getServer_ip();
        String clientIp = sessionRecordLog.getClient_ip();
        try {
            sessionRecordLog.setUid(SnowflakeId.generateId());
            sessionRecordLog.setServer_location(ipLookup.countryLookup(serverIp));
            sessionRecordLog.setClient_location(ipLookup.cityLookupDetail(clientIp));
            sessionRecordLog.setClient_asn(ipLookup.asnLookup(clientIp, true));
            sessionRecordLog.setServer_asn(ipLookup.asnLookup(serverIp, true));
            sessionRecordLog.setDomain(getTopDomain(sessionRecordLog.getSni(), sessionRecordLog.getHost()));
            sessionRecordLog.setRecv_time(System.currentTimeMillis() / 1000);
//            sessionRecordLog.setSubscribe_id(getSubscribeId(clientIp));
            return JSONObject.toJSONString(sessionRecordLog);
        } catch (Exception e) {
            logger.error("日志解析过程出现异常", e);
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
        if (StringUtil.isNotBlank(sni)) {
            return getDomain(sni);
        } else if (StringUtil.isNotBlank(host)) {
            return getDomain(host);
        } else {
            return "";
        }
    }

    /**
     * 获取用户名
     *
     * @param key Sip
     * @return SubscribeId
     */
    private static String getSubscribeId(String key) {
        String sub = "";
        try (Jedis jedis = RedisPollUtils.getJedis()) {
            if (jedis != null) {
                sub = jedis.get(key);
            }
        } catch (Exception e) {
            logger.error("通过Redis获取用户名出现异常", e);
        }
        return sub;
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


}
