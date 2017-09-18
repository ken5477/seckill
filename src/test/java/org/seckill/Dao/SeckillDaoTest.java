package org.seckill.Dao;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.Entity.EntitySeckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.swing.*;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2017/4/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    //注入Dao依賴
    @Resource
    private SeckillDao seckillDao;
    @Test
    public void queryByID() throws Exception {
        long id = 1000;
        EntitySeckill seckill = seckillDao.queryByID(id);
        System.out.println(seckill);
    }

    @Test
    public void queryAll() throws Exception {
        int offset = 0;
        int limit = 100;
        List<EntitySeckill> seckills = seckillDao.queryAll(offset, limit);
        for (EntitySeckill seckill:seckills) {
            System.out.println(seckill);
        }
    }
    @Test
    public void reduceNumber() throws Exception {
        Date killTime = new Date();
        int updateCount = seckillDao.reduceNumber(1000L, killTime);
        System.out.println("updateCount="+updateCount);
    }


}