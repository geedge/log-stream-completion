package cn.ac.iie.test;

import com.zdjizhi.utils.FormatUtils;
import org.junit.Test;

import java.util.Date;


/**
 * @author qidaijie
 * @Package com.zdjizhi.flume
 * @Description:
 * @date 2020/11/2212:06
 */
public class DomainTest {

    @Test
    public void getDomainTest() {
        String url = "array808.prod.do.dsp.mp.microsoft.com";
        System.out.println(FormatUtils.getTopPrivateDomain(url));

    }
}
