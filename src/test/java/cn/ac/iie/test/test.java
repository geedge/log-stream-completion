package cn.ac.iie.test;

import cn.ac.iie.test.bean.Student;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

public class test {
    private static Logger logger = Logger.getLogger(test.class);

    public static void main(String[] args) {
        String message = "{\"name\":\"aaa\"}";
        Student student = JSONObject.parseObject(message, Student.class);
        System.out.println(JSONObject.toJSONString(student));
    }

}
