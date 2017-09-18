package org.seckill.Service.impl;

import org.seckill.Dao.cache.RedisDao;
import org.seckill.Dto.Exposer;
import org.seckill.Dto.SeckillExecution;
import org.seckill.Dao.SeckillDao;
import org.seckill.Dao.SuccessKilledDao;
import org.seckill.Entity.EntitySeckill;
import org.seckill.Entity.EntitySuccessKilled;
import org.seckill.Enum.SeckillStatEnum;
import org.seckill.Exception.RepeatKillException;
import org.seckill.Exception.SeckillCloseException;
import org.seckill.Exception.SeckillExcepetion;
import org.seckill.Service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by Ken Pan on 2017/4/7.
 */
@Service
public class SeckillServiceImpl implements SeckillService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SuccessKilledDao successKilledDao;
    @Autowired
    private RedisDao redisDao;

    private final String slat = "smca239jamcgq'jmnvgcyc/afevvf493#￥%……&fgf";

    public List<EntitySeckill> getSeckillList() {
        return seckillDao.queryAll(0, 4);
    }

    public EntitySeckill getById(long seckillId) {
        return seckillDao.queryByID(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        EntitySeckill seckill = redisDao.getSeckill(seckillId);

        if (seckill == null) {
            seckill = seckillDao.queryByID(seckillId);
            if (seckill == null) {
                return new Exposer(false, seckillId);
            } else {
                redisDao.putSeckill(seckill);
            }
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date now = new Date();
        if (now.getTime() <= startTime.getTime() || now.getTime() >= endTime.getTime()) {
            return new Exposer(false, now.getTime(), startTime.getTime(), endTime.getTime(), seckillId);
        }
        //转化特定字符串
        String md5 = getMD5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillExcepetion, RepeatKillException, SeckillCloseException {
        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            throw new SeckillExcepetion("seckill data rewrite");
        }
        //执行秒杀逻辑，件库存 + 记录购买行为
        Date nowTime = new Date();
        try {
            int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
            if (updateCount <= 0) {
                throw new SeckillCloseException("seckill is closed");
            } else {
                int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
                if (insertCount <= 0) {
                    throw new RepeatKillException("seckill repeat");
                } else {
                    EntitySuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
                }
            }
        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {
            throw new SeckillExcepetion("seckill inner error:" + e.getMessage());
        }
    }

    //获取seckillId md5加密字符串
    private String getMD5(long seckillId) {
        String base = seckillId + "/" + slat;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }
}
