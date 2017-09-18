package org.seckill.Dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.Entity.EntitySeckill;
import org.seckill.Entity.EntitySuccessKilled;

/**
 * Created by Administrator on 2017/4/5.
 */
public interface SuccessKilledDao {

    /**
     * 插入秒殺結果
     * @param seckillId
     * @param userPhone
     * @return
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") Long userPhone);

    /**
     *根據id查詢秒殺結果並携帶秒殺實體
     * @param seckillId
     * @return
     */
    EntitySuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);
}
