package org.seckill.util.LoadBalance;

import java.util.HashMap;

/**
 * Created by Ken Pan on 2017/5/3.
 */
public class IpMap {
    //待路由的IP列表，key代表ip，value代表权重
    public static HashMap<String, Integer> serverWeightMap = new HashMap<String, Integer>();

    static {
        serverWeightMap.put("192.168.2.100",1);
        serverWeightMap.put("192.168.2.101",1);
        serverWeightMap.put("192.168.2.102",4);
        serverWeightMap.put("192.168.2.103",1);
        serverWeightMap.put("192.168.2.104",1);
        serverWeightMap.put("192.168.2.105",3);
        serverWeightMap.put("192.168.2.106",1);
        serverWeightMap.put("192.168.2.107",2);
        serverWeightMap.put("192.168.2.108",1);
        serverWeightMap.put("192.168.2.109",1);
        serverWeightMap.put("192.168.2.110",1);
    }
}
