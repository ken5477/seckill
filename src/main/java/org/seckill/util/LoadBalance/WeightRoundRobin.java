package org.seckill.util.LoadBalance;

import java.util.*;

/**
 * @加权轮询法
 *
 * 根据权值，将server重复的加入服务器列表中
 * Created by Ken Pan on 2017/5/3.
 */
public class WeightRoundRobin {
    private static Integer pos;
    public static String getServer() {

        //重建一个Map,避免服务器的上下线导致并发问题
        Map<String, Integer> serverMap = new HashMap<String, Integer>();
        serverMap.putAll(IpMap.serverWeightMap);
        //获得ip地址
        Set<String> keySet = serverMap.keySet();
        Iterator<String> iterator = keySet.iterator();

        ArrayList<String> keyList = new ArrayList<String>();

        String server = null;

        while (iterator.hasNext()){
            server = iterator.next();
            int weight = serverMap.get(server);
            for (int i = 0; i<weight; i++){
                keyList.add(server);
            }
        }

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
