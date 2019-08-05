package cn.ac.iie.test;

import com.zdjizhi.utils.StringUtil;

import javax.xml.bind.SchemaOutputResolver;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DomainUtils {

    private static Pattern pattern = Pattern.compile("[^\\\\.]+(\\.com\\.cn|\\.net\\.cn|\\.org\\.cn|\\.gov\\.cn|\\.com|\\.net|\\.cn|\\.org|\\.cc|\\.me|\\.tel|\\.mobi|\\.asia|\\.biz|\\.info|\\.name|\\.tv|\\.hk|\\.公司|\\.中国|\\.网络)");

    public static void main(String[] args) {
        System.out.println(getTopDomain("agoo-report.m.taobao.com"));

    }

    private static String getTopDomain(String url) {
//        try {
            //获取值转换为小写
//            String host = new URL(url).getHost().toLowerCase();//news.hexun.com
//            Pattern pattern = Pattern.compile("[^\\\\.]+(\\.com\\.cn|\\.net\\.cn|\\.org\\.cn|\\.gov\\.cn|\\.com|\\.net|\\.cn|\\.org|\\.cc|\\.me|\\.tel|\\.mobi|\\.asia|\\.biz|\\.info|\\.name|\\.tv|\\.hk|\\.公司|\\.中国|\\.网络)");
            Matcher matcher = pattern.matcher(url);
            if (matcher.find()){
                return matcher.group();
            }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
        return null;
    }

}
