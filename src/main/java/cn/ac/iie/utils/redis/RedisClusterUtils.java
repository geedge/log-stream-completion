package cn.ac.iie.utils.redis;

import cn.ac.iie.common.FlowWriteConfig;
import org.apache.log4j.Logger;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

/**
 * 预用于对准IP对应的用户名的 Redis连接池
 *
 * @author my
 * @date 2018-07-04
 */
public final class RedisClusterUtils {
    private static final Logger logger = Logger.getLogger(RedisClusterUtils.class);
    private static JedisCluster jedisCluster;
    private static Properties props = new Properties();

    static {
        try {
            String redisConfigFile = "redis_config.properties";
            props.load(RedisClusterUtils.class.getClassLoader().getResourceAsStream(redisConfigFile));
        } catch (IOException e) {
            props = null;
            logger.error("加载Redis配置文件失败！", e);
        }
    }

    /**
     * 不允许通过new创建该类的实例
     */
    private RedisClusterUtils() {
    }

    /**
     * 初始化Redis连接池
     */
    private static JedisCluster getJedisCluster() {
        if (jedisCluster == null) {
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxTotal(Integer.valueOf(props.getProperty(FlowWriteConfig.REDIS_POOL_MAXACTIVE)));
            poolConfig.setMaxIdle(Integer.valueOf(props.getProperty(FlowWriteConfig.REDIS_POOL_MAXIDLE)));
            poolConfig.setMaxWaitMillis(Long.valueOf(props.getProperty(FlowWriteConfig.REDIS_POOL_MAXWAIT)));
            poolConfig.setTestOnReturn(Boolean.valueOf(props.getProperty(FlowWriteConfig.REDIS_POOL_TESTONRETURN)));
            poolConfig.setTestOnBorrow(Boolean.valueOf(props.getProperty(FlowWriteConfig.REDIS_POOL_TESTONBORROW)));
            Set<HostAndPort> nodes = new LinkedHashSet<HostAndPort>();
            for (String port : props.getProperty(FlowWriteConfig.REDIS_PORT).split(FlowWriteConfig.SEGMENTATION)) {
                for (String ip : props.getProperty(FlowWriteConfig.REDIS_IP).split(FlowWriteConfig.SEGMENTATION)) {
                    nodes.add(new HostAndPort(ip, Integer.parseInt(port)));
                }
            }
            jedisCluster = new JedisCluster(nodes, poolConfig);
        }
        return jedisCluster;
    }

    /**
     * 获取用户名
     *
     * @param key service_ip
     * @return Subscribe_id
     */
    public static String get(String key) {
        String s = key.split("\\.")[0];
        if (!FlowWriteConfig.CHECK_IP_SCOPE.contains(s)) {
            jedisCluster = getJedisCluster();
            return jedisCluster.get(key);
        }
        return "";
    }


}
