package cn.ac.iie.test;

import cn.ac.iie.common.FlowWriteConfig;
import cn.ac.iie.utils.json.JsonParseUtil;

import java.util.ArrayList;
import java.util.HashMap;

public class UtilTest {


    private static HashMap<String, Class> map = JsonParseUtil.getMapFromhttp(FlowWriteConfig.SCHEMA_HTTP);
    //反射成一个类
    private static Object mapObject = JsonParseUtil.generateObject(map);
    //获取任务列表
    private static ArrayList<String[]> jobList = JsonParseUtil.getJobListFromHttp(FlowWriteConfig.SCHEMA_HTTP);

    public static void main(String[] args) {


        for (String[] strings : jobList) {
            System.out.println(strings[0]);
            System.out.println(strings[1]);
            System.out.println(strings[2]);
        }
    }
}
