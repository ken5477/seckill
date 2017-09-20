package org.seckill.dubbo_Demo.Provider;

/**
 * Created by Ken Pan on 2017/9/19.
 */
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Provider {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] {"spring/dubbo-demo-provider.xml"});
        context.start();
        System.in.read(); // press any key to exit
    }
}

