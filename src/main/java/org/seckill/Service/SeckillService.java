package org.seckill.Service;

import org.seckill.Dto.Exposer;
import org.seckill.Dto.SeckillExecution;
import org.seckill.Entity.EntitySeckill;
import org.seckill.Exception.RepeatKillException;
import org.seckill.Exception.SeckillCloseException;
import org.seckill.Exception.SeckillExcepetion;

import java.util.List;

/**
 * Created by Ken Pan on 2017/4/7.
 */
public interface SeckillService {
    /**
     * 查詢所有秒殺記錄
     * @return
     */
    List<EntitySeckill> getSeckillList();

    /**
     * 查詢單個秒殺記錄
     * @return
     */
    EntitySeckill getById(long seckillId);

    /**
     * 秒殺開啓時輸出秒殺地址
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 執行秒殺操作
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
        throws SeckillExcepetion,RepeatKillException,SeckillCloseException;
}
