package org.seckill.Dao.cache;

import org.seckill.Entity.EntitySeckill;
import org.seckill.util.serializeUtil.SerializeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by Ken Pan on 2017/4/18.
 */
public class RedisDao {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JedisPool jedisPool;

    public RedisDao(String ip, int port) {
        jedisPool = new JedisPool(ip, port);
    }

    public EntitySeckill getSeckill(long seckillId) {

        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:" + seckillId;
                //采用自定义序列化
                byte[] bytes = jedis.get(key.getBytes());
                if (bytes != null) {
                    try {
                        EntitySeckill seckill = (EntitySeckill) SerializeUtils.deSerialize(bytes);
                        return seckill;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public String putSeckill(EntitySeckill seckill) {

        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:" + seckill.getSeckillId();
                //采用自定义序列化
                byte[] bytes = SerializeUtils.serialize(seckill);
                if (bytes != null) {
                    int timeOut = 60*60;
                    String result = jedis.setex(key.getBytes(),timeOut,bytes);
                    return result;
                }
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
