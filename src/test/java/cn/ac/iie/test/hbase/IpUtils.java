package cn.ac.iie.test.hbase;

import cn.ac.iie.common.FlowWriteConfig;
import cn.ac.iie.utils.general.EncryptionUtils;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;


public class IpUtils {
    private static Logger logger = Logger.getLogger(IpUtils.class);

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        System.out.println(System.currentTimeMillis() - 60000);
    }

    /**
     * ip字符串转整数
     * ip是.分割的整数字符串,按照r进制转十进制的规律,按权相加求和,这里的权是256.
     *
     * @param ip
     * @return
     */
    public static int ip2Int(String ip) {
        String[] ipStrs = ip.split("\\.");//分割ip
        int result = 0;
        for (int i = 0; i < 4; i++) {
            Integer ipSubInteger = Integer.parseInt(ipStrs[i]);
            if (ipSubInteger > 255) {//正则验证不能为负数
                result = 0;
                break;
            }
            result += (ipSubInteger << (24 - i * 8));
        }
        return result;
    }


    /**
     * 整数转ip
     *
     * @param ip
     * @return
     */
    public static String int2Ip(int ip) {
        StringBuilder builder = new StringBuilder(String.valueOf(ip >>> 24));
        builder.append(".");
        builder.append(String.valueOf((ip & 0X00FFFFFF) >>> 16));
        builder.append(".");
        builder.append(String.valueOf((ip & 0X0000FFFF) >>> 8));
        builder.append(".");
        builder.append(String.valueOf(ip & 0X000000FF));
        return builder.toString();
    }


    public static int validIPAddress(String ip) {
        String ipv4 = "^((\\d|[1-9]\\d|1\\d\\d|2([0-4]\\d|5[0-5]))\\.){4}$";
        //8个1-4位+:
        String ipv6 = "^(([\\da-fA-F]{1,4}):){8}$";
        return String.format("%s.", ip).matches(ipv4) ? 1 : String.format("%s:", ip).matches(ipv6) ? 2 : 3;
    }
}
