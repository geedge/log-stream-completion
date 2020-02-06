package cn.ac.iie.utils.general;

import cn.ac.iie.common.FlowWriteConfig;
import cn.ac.iie.utils.hbase.HBaseUtils;
import cn.ac.iie.utils.system.SnowflakeId;
import com.google.common.net.InternetDomainName;
import com.zdjizhi.utils.IpLookup;
import com.zdjizhi.utils.StringUtil;
import org.apache.log4j.Logger;

import java.util.Base64;


public final class CompleteUtil {

    private static Logger logger = Logger.getLogger(TransFormUtils.class);
    private static IpLookup ipLookup = new IpLookup.Builder(false)
            .loadDataFileV4(FlowWriteConfig.IP_LIBRARY + "Kazakhstan.mmdb")
            .loadDataFileV6(FlowWriteConfig.IP_LIBRARY + "Kazakhstan.mmdb")
            .loadAsnDataFileV4(FlowWriteConfig.IP_LIBRARY + "asn_v4.mmdb")
            .loadAsnDataFileV6(FlowWriteConfig.IP_LIBRARY + "asn_v6.mmdb")
            .build();

    /**
     * 有sni通过sni获取域名，有host根据host获取域名
     *
     * @param sni  sni
     * @param host host
     * @return 顶级域名
     */
    public static String getTopDomain(String sni, String host) {
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
    public static String getDomainName(String host) {
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
    public static int getCurrentTime() {
        return (int)(System.currentTimeMillis() / 1000);
    }

    /**
     * 雪花模型生成id
     *
     * @return
     */
    public static long getSnowflakeId(String zookeeperIp, String kafkaTopic, long dataCenterIdNum) {

        return SnowflakeId.generateId(zookeeperIp,kafkaTopic,dataCenterIdNum);
    }

    /**
     * 根据clientIp获取location信息
     *
     * @param ip
     * @return
     */
    public static String getGeoIpDetail(String ip) {

        return ipLookup.cityLookupDetail(ip);
    }

    /**
     * 根据ip获取asn信息
     *
     * @param ip
     * @return asn
     */
    public static String getGeoAsn(String ip) {

        return ipLookup.asnLookup(ip, true);
    }

    /**
     * 根据ip获取country信息
     *
     * @param ip
     * @return country
     */
    public static String getGeoIpCountry(String ip) {

        return ipLookup.countryLookup(ip);
    }

    /**
     * 根据ip去hbase中匹配对应的用户名
     * @param clientIp
     * @param hbaseZookeeper
     * @param hbaseTable
     * @return 用户名 subscriber_id
     */
    public static String radiusMatch(String clientIp, String hbaseZookeeper, String hbaseTable) {
        return HBaseUtils.getAccount(clientIp,hbaseZookeeper,hbaseTable);
    }


    /**
     * base64 解码
     *
     * @param encodedText    mail subject
     * @param subjectCharset 编码格式
     * @return 解码内容 / 空
     */
    public static String base64Str(String encodedText, String subjectCharset) {
        Base64.Decoder decoder = Base64.getDecoder();
        String sub;
        try {
            if (StringUtil.isNotBlank(subjectCharset)) {
                sub = new String(decoder.decode(encodedText), subjectCharset);
            } else {
                sub = new String(decoder.decode(encodedText), "UTF-8");
            }
            return sub;
        } catch (Exception e) {
            logger.error("transform base64 String failed! base64Str = " + encodedText + "Charset = " + subjectCharset + "error :" + e);
            return "";
        }
    }

}
