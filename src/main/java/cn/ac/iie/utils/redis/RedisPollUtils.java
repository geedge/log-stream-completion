package cn.ac.iie.utils.redis;

import cn.ac.iie.common.FlowWriteConfig;
import com.zdjizhi.utils.StringUtil;
import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Properties;

/**
 * @author qidaijie
 */
public class RedisPollUtils {
    private static final Logger logger = Logger.getLogger(RedisPollUtils.class);
    private static JedisPool jedisPool = null;
    private static Properties props = new Properties();


    private RedisPollUtils() {
    }

    static {
        initialPool();

    }

    /**
     * 初始化Redis连接池
     */
    private static void initialPool() {
        try {
            //加载连接池配置文件
            props.load(RedisPollUtils.class.getClassLoader().getResourceAsStream("redis_config.properties"));
            // 创建jedis池配置实例
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxTotal(Integer.valueOf(props.getProperty(FlowWriteConfig.REDIS_POOL_MAXACTIVE)));
            poolConfig.setMaxIdle(Integer.valueOf(props.getProperty(FlowWriteConfig.REDIS_POOL_MAXIDLE)));
            poolConfig.setMaxWaitMillis(Long.valueOf(props.getProperty(FlowWriteConfig.REDIS_POOL_MAXWAIT)));
            poolConfig.setTestOnReturn(Boolean.valueOf(props.getProperty(FlowWriteConfig.REDIS_POOL_TESTONRETURN)));
            poolConfig.setTestOnBorrow(Boolean.valueOf(props.getProperty(FlowWriteConfig.REDIS_POOL_TESTONBORROW)));
            // 根据配置实例化jedis池
            jedisPool = new JedisPool(poolConfig, props.getProperty(FlowWriteConfig.REDIS_IP),
                    Integer.valueOf(props.getProperty(FlowWriteConfig.REDIS_PORT)));
        } catch (Exception e) {
            logger.error("Redis连接池初始化错误", e);
        }
    }

    /**
     * 获取Jedis实例
     *
     * @return Jedis实例
     */
    public static Jedis getJedis() {
        Jedis jedis = null;
        try {
            if (jedisPool == null) {
                initialPool();
            }
            jedis = jedisPool.getResource();
        } catch (Exception e) {
            logger.error("Redis连接池错误,无法获取连接", e);
        }
        return jedis;
    }

//    /**
//     * @param key redis key
//     * @return value
//     */
//    public static Integer getWorkerId(String key) {
//        int workId = 0;
//        int maxId = 32;
//        try (Jedis jedis = RedisPollUtils.getJedis()) {
//            if (jedis != null) {
//                String work = jedis.get(key);
//                if (StringUtil.isBlank(work)) {
//                    jedis.set(key, "0");
//                } else {
//                    workId = Integer.parseInt(work);
//                }
//                if (workId < maxId) {
//                    jedis.set(key, String.valueOf(workId + 1));
//                } else {
//                    workId = 0;
//                    jedis.set(key, "1");
//                }
//            }
//        } catch (Exception e) {
//            logger.error("通过Redis获取用户名出现异常", e);
//            workId = RandomUtils.nextInt(0, 31);
//        }
//        return workId;
//    }

    public static Integer getWorkerId(String key) {
        int workId = 0;
        try (Jedis jedis = RedisPollUtils.getJedis()) {
            if (jedis != null) {
                workId = Integer.parseInt(jedis.get(key));
                jedis.set(key, String.valueOf(workId + 2));
                logger.error("\n工作id是：" + workId + "\n");
            }
        } catch (Exception e) {
            logger.error("通过Redis获取用户名出现异常", e);
            workId = RandomUtils.nextInt(0, 31);
        }
        return workId;
    }


}
