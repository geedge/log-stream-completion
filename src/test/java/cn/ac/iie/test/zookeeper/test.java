package cn.ac.iie.test.zookeeper;

import cn.ac.iie.common.FlowWriteConfig;
import com.zdjizhi.utils.IPUtil;
import com.zdjizhi.utils.IpLookup;

public class test {
    private static IpLookup ipLookup = new IpLookup.Builder(false)
            .loadDataFileV4("Kazakhstan.mmdb")
            .loadDataFileV6("Kazakhstan.mmdb")
            .build();

    public static void main(String[] args) {
        System.out.println(ipLookup.cityLookupDetail("256.5.5.5"));
    }
}
