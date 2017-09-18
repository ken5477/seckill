package org.seckill.util.LoadBalance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @随机算法
 *
 *
 * 缺点：同RoundRobin
 * 优点: 吞吐量足够大的时候  可接近轮询算法
 * Created by Ken Pan on 2017/5/3.
 */
public class Random {

    public static String getServer(){

        //重建一个Map,避免服务器的上下线导致并发问题
        Map<String, Integer> serverMap = new HashMap<String, Integer>();
        serverMap.putAll(IpMap.serverWeightMap);
        //获得ip地址
        Set<String> keySet = serverMap.keySet();
        ArrayList<String> keyList = new ArrayList<String>();
        keyList.addAll(keySet);

        String server = null;
        java.util.Random random = new java.util.Random();
        int randomPos = random.nextInt(keyList.size());
        server = keyList.get(randomPos);

        return server;
    }
}
