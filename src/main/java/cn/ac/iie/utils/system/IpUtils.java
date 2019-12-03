package cn.ac.iie.utils.system;

/**
 * IP工具类
 *
 * @author qidaijie
 */
public class IpUtils {
    /**
     * IPV4 正则
     */
    private static final String IPV4 = "^((\\d|[1-9]\\d|1\\d\\d|2([0-4]\\d|5[0-5]))\\.){4}$";
    /**
     * IPV6正则
     */
    private static final String IPV6 = "^(([\\da-fA-F]{1,4}):){8}$";


    /**
     * 判断IP类型 v4 or v6
     *
     * @param ip IP
     * @return 1:v4 2:v6 3:abnormal
     */
    public static int validIPAddress(String ip) {
        return String.format("%s.", ip).matches(IPV4) ? 1 : String.format("%s:", ip).matches(IPV6) ? 2 : 3;
    }

    /**
     * ip字符串转整数
     * ip是.分割的整数字符串,按照r进制转十进制的规律,按权相加求和,这里的权是256.
     *
     * @param ip IP
     * @return ip(int)
     */
    public static int ipChangeInt(String ip) {
        //分割ip
        String[] ipSplit = ip.split("\\.");
        int result = 0;
        for (int i = 0; i < 4; i++) {
            Integer ipSubInteger = Integer.parseInt(ipSplit[i]);
            //正则验证不能为负数
            if (ipSubInteger > 255) {
                result = 0;
                break;
            }
            result += (ipSubInteger << (24 - i * 8));
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(validIPAddress("192.254.254.254"));
        System.out.println(ipChangeInt("254.254.254.254"));
    }
}
