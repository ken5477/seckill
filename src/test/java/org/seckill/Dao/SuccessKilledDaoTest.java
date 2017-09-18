package org.seckill.Dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.Entity.EntitySuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2017/4/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {
    @Resource
    private SuccessKilledDao successKilledDao;
    @Test
    public void insertSuccessKilled() throws Exception {
        long id = 1000L;
        long phone = 13502181181L;
        int insertCount = successKilledDao.insertSuccessKilled(id,phone);
        System.out.println("insertCount = "+insertCount);
    }

    @Test
    public void queryByIdWithSeckill() throws Exception {
        EntitySuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(1000L,13502181181L);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
    }

}