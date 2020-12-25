package cn.ac.iie.utils.general;

import cn.ac.iie.common.FlowWriteConfig;
import cn.ac.iie.utils.hbase.HBaseUtils;
import cn.ac.iie.utils.json.JsonParseUtil;
import com.jayway.jsonpath.InvalidPathException;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.zdjizhi.utils.Encodes;
import com.zdjizhi.utils.FormatUtils;
import com.zdjizhi.utils.IpLookup;
import com.zdjizhi.utils.StringUtil;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author qidaijie
 */
class TransFunction {
    private static Logger logger = Logger.getLogger(TransFunction.class);

    /**
     * 生成当前时间戳的操作
     */
    static long getCurrentTime() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 根据clientIp获取location信息
     *
     * @param ip client IP
     * @return ip地址详细信息
     */
    static String getGeoIpDetail(IpLookup ipLookup, String ip) {

        return ipLookup.cityLookupDetail(ip);
    }

    /**
     * 根据ip获取asn信息
     *
     * @param ip client/server IP
     * @return ASN
     */
    static String getGeoAsn(IpLookup ipLookup, String ip) {

        return ipLookup.asnLookup(ip);
    }

    /**
     * 根据ip获取country信息
     *
     * @param ip server IP
     * @return 国家
     */
    static String getGeoIpCountry(IpLookup ipLookup, String ip) {

        return ipLookup.countryLookup(ip);
    }


    /**
     * radius借助HBase补齐
     *
     * @param ip client IP
     * @return account
     */
    static String radiusMatch(String ip) {
        String account = HBaseUtils.getAccount(ip.trim());
        if (StringUtil.isBlank(account)) {
            logger.warn("HashMap get Account is null,IP is :" + ip);
        }
        return account;
    }

    /**
     * 解析顶级域名
     *
     * @param domain 初始域名
     * @return 顶级域名
     */
    static String getTopDomain(String domain) {
        try {
            return FormatUtils.getTopPrivateDomain(domain);
        } catch (StringIndexOutOfBoundsException outException) {
            logger.error("解析顶级域名异常,异常域名:" + domain, outException);
            return "";
        }
    }

    /**
     * 根据编码解码base64
     *
     * @param message base64
     * @param charset 编码
     * @return 解码字符串
     */
    static String decodeBase64(String message, String charset) {
        String result = "";
        try {
            if (StringUtil.isNotBlank(message)) {
                if (StringUtil.isNotBlank(charset)) {
                    result = Encodes.decodeBase64String(message, charset);
                } else {
                    result = Encodes.decodeBase64String(message, FlowWriteConfig.MAIL_DEFAULT_CHARSET);
                }
            }
        } catch (Exception e) {
            logger.error("解析 Base64 异常,异常信息:" + e);
        }
        return result;
    }

    /**
     * 根据表达式解析json
     *
     * @param message json
     * @param expr    解析表达式
     * @return 解析结果
     */
    static String flattenSpec(String message, String expr) {
        String flattenResult = "";
        try {
            if (StringUtil.isNotBlank(expr)) {
                ArrayList<String> read = JsonPath.parse(message).read(expr);
                flattenResult = read.get(0);
            }
        } catch (ClassCastException | InvalidPathException e) {
            logger.error("设备标签解析异常，[ " + expr + " ]解析表达式错误", e);
        }
        return flattenResult;
    }


    /**
     * 判断是否为日志字段,是则返回对应value，否则返回原始字符串
     *
     * @param object 内存实体类
     * @param param  字段名/普通字符串
     * @return JSON.Value or String
     */
    static String isJsonValue(Object object, String param) {
        if (param.contains(FlowWriteConfig.IS_JSON_KEY_TAG)) {
            Object value = JsonParseUtil.getValue(object, param.substring(2));
            if (value != null) {
                return value.toString();
            } else {
                return "";
            }
        } else {
            return param;
        }
    }

    /**
     * IF函数实现，解析日志构建三目运算
     *
     * @param object  内存实体类
     * @param ifParam 字段名/普通字符串
     * @return resultA or resultB or ""
     */
    static String condition(Object object, String ifParam) {
        String result = "";
        try {
            String[] split = ifParam.split(FlowWriteConfig.FORMAT_SPLITTER);
            String[] norms = split[0].split(FlowWriteConfig.IF_CONDITION_SPLITTER);
            String direction = isJsonValue(object, norms[0]);
            if (StringUtil.isNotBlank(direction)) {
                if (split.length == FlowWriteConfig.IF_PARAM_LENGTH) {
                    String resultA = isJsonValue(object, split[1]);
                    String resultB = isJsonValue(object, split[2]);
                    result = (Integer.parseInt(direction) == Integer.parseInt(norms[1])) ? resultA : resultB;
                }
            }
        } catch (Exception e) {
            logger.error("IF 函数执行异常,异常信息:" + e);
            e.printStackTrace();
        }
        return result;
    }
}
