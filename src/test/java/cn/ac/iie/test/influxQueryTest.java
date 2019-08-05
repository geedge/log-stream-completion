package cn.ac.iie.test;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class influxQueryTest {
    private static InfluxDB client;

    public static void main(String[] args) {

        Query query = new Query("select * from test", "deltest");
        QueryResult a = getClient().query(query);

//        List<dataInfo> lists = new ArrayList<dataInfo>();
        for (QueryResult.Result result : a.getResults()) {
            List<QueryResult.Series> series = result.getSeries();
            for (QueryResult.Series serie : series) {
                List<List<Object>> values = serie.getValues();//字段字集合
                List<String> colums = serie.getColumns();//字段名
                System.out.println("colums:" + colums);
                for (List<Object> n : values) {
                    System.out.println("value:" + n);
                }
//lists.addAll(getQueryData(colums,values));
            }
            System.out.println("数据长度：" + series.size());
        }
    }

    /**
     * 链接时序数据库，获得InfluDB
     */
    public static InfluxDB getClient() {
        client = InfluxDBFactory.connect("http://192.168.40.207:8086", "test", "123456");
        Pong pong = client.ping();
        if (pong != null) {
            System.out.println("Influx数据库连接成功");
        } else {
            return null;
        }
//        client.createDatabase("testDB");
        return client;
    }
}
