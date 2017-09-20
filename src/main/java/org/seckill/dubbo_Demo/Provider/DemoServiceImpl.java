package org.seckill.dubbo_Demo.Provider;

import org.seckill.dubbo_Demo.DemoService;

/**
 * Created by Ken Pan on 2017/9/19.
 */
public class DemoServiceImpl implements DemoService {
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
