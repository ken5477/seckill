package org.seckill.Service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.Dto.Exposer;
import org.seckill.Dto.SeckillExecution;
import org.seckill.Entity.EntitySeckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by Ken Pan on 2017/4/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillService seckillService;
    @Test
    public void getSeckillList() throws Exception {
        List<EntitySeckill> seckills = seckillService.getSeckillList();
        for(EntitySeckill seckill : seckills){
            logger.info("seckill={}",seckill);
        }
    }

    @Test
    public void getById() throws Exception {
        long seckillId = 1000L;
        EntitySeckill seckill = seckillService.getById(seckillId);
        logger.info(" seckill = {}", seckill);
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        long seckillId = 1000L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        logger.info("exposer={}",exposer);
    }

    @Test
    public void executeSeckill() throws Exception {
        long seckillId = 1000L;
        long userPhone = 15295557277L;
        String Md5 = "03853c1017d585292cb8d3c35c4ba254";
        SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, userPhone, Md5);
        logger.info("seckillExecution = {}",seckillExecution);
    }

}