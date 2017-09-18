package org.seckill.util.SpringSchedulerJob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by Ken Pan on 2017/5/25.
 */
@Component
public class FirstSchedulerJob {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //@Scheduled(cron = "0 * * * * ?")
    public void run() {
        logger.info("MyFirstSpringJob trigger...");

        /* 模拟此Job需耗时5秒 */
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
