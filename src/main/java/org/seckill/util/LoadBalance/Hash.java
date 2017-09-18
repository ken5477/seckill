package org.seckill.util.LoadBalance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @源地址hash法
 *
 * 优点：保证相同客户端的请求到同一台服务器，对session，cache等有益处
 * 缺点：当集群中的server上、下线，会有问题，尤其是cache会血崩
 *
 * Created by Ken Pan on 2017/5/3.
 */
public class Hash {
    public static String getServer(){

        //重建一个Map,避免服务器的上下线导致并发问题
        Map<String, Integer> serverMap = new HashMap<String, Integer>();
        serverMap.putAll(IpMap.serverWeightMap);
        //获得ip地址
        Set<String> keySet = serverMap.keySet();
        ArrayList<String> keyList = new ArrayList<String>();
        keyList.addAll(keySet);

        String server = null;

        //获取请求的ip
        //在web应用中可通过HttpServlet的getRemoteIp方法获取
        String remoteIp = "127.0.0.1";
        int hashCode = remoteIp.hashCode();
        int serverListSize = keyList.size();
        int serverPos = hashCode % serverListSize;

        server = keyList.get(serverPos);

        return server;
    }
}
