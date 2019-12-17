package cn.ac.iie.test;

import com.google.common.net.InternetDomainName;
import com.zdjizhi.utils.StringUtil;

import javax.xml.bind.SchemaOutputResolver;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DomainUtils {
    private final static Set<String> PublicSuffixSet = new HashSet<String>(
            Arrays.asList("com|edu|gov|int|mil|net|org|biz|info|pro|name|museum|coop|aero|xxx|idv|top|xyz|xin|vip|win|red|wang|co|mobi|travel|club|post|rec|asia"
                    .split("\\|")));

    public static void main(String[] args) {

// InternetDomainName.from("foo.co.uk").topPrivateDomain().toString();
        String host = "www.aaa.co.uk";
//        if (host.contains(":")){
//            String s = host.split(":")[0];
//            System.out.println(InternetDomainName.from(s));
//            System.out.println(InternetDomainName.from(s).topPrivateDomain());
//        }else {
            System.out.println(InternetDomainName.from(host).topDomainUnderRegistrySuffix());
            System.out.println(InternetDomainName.from(host).topPrivateDomain());

//        }
//        System.out.println(InternetDomainName.from("shence.hupu.com").topPrivateDomain());
    }

    /**
     * 获取url的顶级域名
     * //     * @param url
     *
     * @return
     */
    public static String getDomainName(String host) {
        if (host.endsWith(".") || host.contains("/")) {
            host = host.substring(0, host.length() - 1);
        }
        int index = 0;
        String candidate = host;
        for (; index >= 0; ) {
            index = candidate.indexOf('.');
            String subCandidate = candidate.substring(index + 1);
            if (PublicSuffixSet.contains(subCandidate)) {
                return candidate;
            }
            candidate = subCandidate;
        }
        return candidate;
    }


    public static String getSourceDomain(String host) {
        if (host.endsWith(".")) {
            host = host.substring(0, host.length() - 1);
        }
        String[] hostStr = host.split("\\.");
        int length = hostStr.length;
        if (hostStr.length >= 2) {
            if (PublicSuffixSet.contains(hostStr[length - 2])) {
                return hostStr[length - 3] + "." + hostStr[length - 2] + "." + hostStr[length - 1];
            } else {
                return hostStr[length - 2] + "." + hostStr[length - 1];
            }
        } else {
            return host;
        }
    }


    // 定义正则表达式，域名的根需要自定义，这里不全
    private static final String RE_TOP = "[\\w-]+\\.(com.cn|net.cn|gov.cn|org\\.nz|org.cn|com|net|org|gov|cc|biz|info|cn|co)\\b()*";
    private static Pattern pattern = Pattern.compile(RE_TOP, Pattern.CASE_INSENSITIVE);

    public static String getTopDomain(String url) {
        String result = url;
        try {
            Matcher matcher = pattern.matcher(url);
            matcher.find();
            result = matcher.group();
        } catch (Exception e) {
            System.out.println("[getTopDomain ERROR]====>");
            e.printStackTrace();
        }
        return result;
    }
}
