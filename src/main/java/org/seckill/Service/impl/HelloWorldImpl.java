package org.seckill.Service.impl;

import org.seckill.Service.HelloWorld;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * Created by Ken Pan on 2017/6/7.
 */
@WebService(endpointInterface = "org.seckill.Service.HelloWorld")
@SOAPBinding(style = SOAPBinding.Style.RPC)

public class HelloWorldImpl implements HelloWorld {
    @Override
    public String sayHi(@WebParam(name = "text")String text) {
        System.out.println("sayHi called!");
        return "Hello"+text;
    }
}
