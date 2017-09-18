package org.seckill.util.QuartzUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ken Pan on 2017/4/28.
 */
public class ExampleJob {
    /**
     * 执行定时统计任务
     * 自行指定方法
     */
    public void execute(){
        //业务代码
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "执行ExampleJob");
    }
}
