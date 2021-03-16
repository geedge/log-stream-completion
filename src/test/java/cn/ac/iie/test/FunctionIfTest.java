package cn.ac.iie.test;

import cn.ac.iie.common.FlowWriteConfig;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.zdjizhi.utils.IpLookup;
import org.junit.Test;

/**
 * @author qidaijie
 * @Package cn.ac.iie.test
 * @Description:
 * @date 2020/12/2717:33
 */
public class FunctionIfTest {
    private static final Log log = LogFactory.get();
    private static IpLookup ipLookup = new IpLookup.Builder(false)
            .loadDataFilePrivateV4(FlowWriteConfig.IP_LIBRARY + "ip_v4.mmdb")
            .loadDataFilePrivateV6(FlowWriteConfig.IP_LIBRARY + "ip_v4.mmdb")
            .build();

    @Test
    public void Test() {
        String ip = "192.168.50.65";
        System.out.println(ipLookup.cityLookupDetail(ip));
        System.out.println(ipLookup.latLngLookup(ip));
        System.out.println(ipLookup.provinceLookup(ip));
        System.out.println(ipLookup.countryLookup(ip));
        System.out.println(ipLookup.cityLookup(ip));

    }
}
