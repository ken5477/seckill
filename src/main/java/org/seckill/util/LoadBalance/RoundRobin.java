package org.seckill.util.LoadBalance;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @轮询法
 *
 * 优点：试图做到请求转移的绝对均衡
 *
 * 缺点：在服务器上线、下线的时候 无法获取到实时信息。
 * 比如在serverMap获取到IPMap后有server下线，轮询算法并不知道，可能导致服务无法用
 * Created by Ken Pan on 2017/5/3.
 */
public class RoundRobin {
    private static Integer pos = 0;

    public static String getServer(){

        //重建一个Map,避免服务器的上下线导致并发问题
        Map<String, Integer> serverMap = new HashMap<String, Integer>();
        serverMap.putAll(IpMap.serverWeightMap);
        //获得ip地址
        Set<String> keySet = serverMap.keySet();
        ArrayList<String> keyList = new ArrayList<String>();
        keyList.addAll(keySet);

        String server = null;
        synchronized (pos){
            if(pos>keyList.size()){
                pos = 0;
            }
            server = keyList.get(pos);
            pos++;
        }


        return server;
    }
}
