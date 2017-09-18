package org.seckill.Dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.Entity.EntitySeckill;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/4/5.
 */
public interface SeckillDao {

    /**
     * j减库存
     * @param seckillId
     * @param killTime
     * @return
     */
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    /**
     * 查詢
     * @param seckillId
     * @return
     */
    EntitySeckill queryByID(@Param("seckillId") long seckillId);

    /**
     * 查詢列表
     * @param offset
     * @param limit
     * @return
     */
    List<EntitySeckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);

}
