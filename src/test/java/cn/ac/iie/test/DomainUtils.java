package cn.ac.iie.test;

import com.zdjizhi.utils.StringUtil;

import javax.xml.bind.SchemaOutputResolver;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DomainUtils {

    private static Pattern pattern = Pattern.compile("[^\\\\.]+(\\.com\\.cn|\\.net\\.cn|\\.org\\.cn|\\.gov\\.cn|\\.com|\\.net|\\.cn|\\.org|\\.me|\\.cc|\\.tel|\\.mobi|\\.asia|\\.biz|\\.info|\\.name|\\.tv|\\.hk|\\.公司|\\.中国|\\.网络)");

    private final static Set<String> PublicSuffixSet = new HashSet<String>(
            Arrays.asList("com|org|net|gov|edu|co|tv|mobi|info|asia|xxx|onion|cc|cn|com.cn|edu.cn|gov.cn|net.cn|org.cn|jp|kr|tw|com.hk|hk|com.hk|org.hk|se|com.se|org.se"
                    .split("\\|")));
    private static Pattern IP_PATTERN = Pattern.compile("(\\d{1,3}\\.){3}(\\d{1,3})");

    public static void main(String[] args) {
        System.out.println(getTopDomain("http://www.ccb.com"));
            System.out.println(getDomainName("www.comaa.com"));


    }

    private static String getTopDomain(String url) {
        try {
//            获取值转换为小写
            String host = new URL(url).getHost().toLowerCase();//news.hexun.com
//            Pattern pattern = Pattern.compile("[^\\\\.]+(\\.com\\.cn|\\.net\\.cn|\\.org\\.cn|\\.gov\\.cn|\\.com|\\.net|\\.cn|\\.org|\\.cc|\\.me|\\.tel|\\.mobi|\\.asia|\\.biz|\\.info|\\.name|\\.tv|\\.hk|\\.公司|\\.中国|\\.网络)");
            Matcher matcher = IP_PATTERN.matcher(host);
            if (matcher.find()){
                return matcher.group();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }


//    private final static Set<String> PublicSuffixSet = new HashSet<String>(
//            Arrays.asList("com|org|net|gov|edu|co|tv|mobi|info|asia|xxx|onion|cc|cn|com.cn|edu.cn|gov.cn|net.cn|org.cn|jp|kr|tw|com.hk|hk|com.hk|org.hk|se|com.se|org.se"
//                    .split("\\|")));
//    private static Pattern IP_PATTERN = Pattern.compile("(\\d{1,3}\\.){3}(\\d{1,3})");

    /**
     * 获取url的顶级域名
//     * @param url
     * @return
     */
    public static String getDomainName(String host) {
//        String host = url.getHost();
        if (host.endsWith(".")){
            host = host.substring(0, host.length() - 1);
        }
        if (IP_PATTERN.matcher(host).matches()){
            return host;
        }
        int index = 0;
        String candidate = host;
        for (; index >= 0;) {
            index = candidate.indexOf('.');
            String subCandidate = candidate.substring(index + 1);
            if (PublicSuffixSet.contains(subCandidate)) {
                return candidate;
            }
            candidate = subCandidate;
        }
        return candidate;
    }


}
