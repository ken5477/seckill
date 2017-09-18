package org.seckill.Service;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * Created by Ken Pan on 2017/6/7.
 */
@WebService(targetNamespace = "seckill")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface HelloWorld {
    String sayHi(String text);
}
