package org.seckill.util.zkUtil;

import org.I0Itec.zkclient.*;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * *ZkClient的使用测试
 *
 * Created by Ken Pan on 2017/5/24.
 */
public class zkClientTest {

    public static void main(String[] args) {

        HashMap map = new HashMap();
        /*

        读写锁
        ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
        rw.readLock().lock();*/

        ZkClient zkClient = new ZkClient("192.168.0.251:2181");
        String node = "/myapp";

        // 订阅监听事件
        childChangesListener(zkClient, node);
        dataChangesListener(zkClient, node);
        stateChangesListener(zkClient);

        if (!zkClient.exists(node)) {
            zkClient.createPersistent(node, "hello zookeeper");
        }
        System.out.println(zkClient.readData(node));

        zkClient.updateDataSerialized(node, new DataUpdater<String>() {

            public String update(String currentData) {
                return currentData + "-123";
            }
        });
        System.out.println(zkClient.readData(node));

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class SessionWatcher implements Watcher {

        public void process(WatchedEvent event) {
            // 如果是“数据变更”事件
            if (event.getType() != Event.EventType.None) {
                return;
            }
            synchronized (this){
                switch(event.getState()) {
                    case SyncConnected:
                        //zk连接建立成功,或者重连成功
                        System.out.println("Connected...");
                        break;
                    case Expired:
                        // session过期,这是个非常严重的问题,有可能client端出现了问题,也有可能zk环境故障
                        // 此处仅仅是重新实例化zk client
                        System.out.println("Expired(重连)...");
                        //connect();
                        break;
                    case Disconnected:
                        System.out.println("链接断开，或session迁移....");
                        break;
                    case AuthFailed:
                        //close();
                        throw new RuntimeException("ZK Connection auth failed...");
                    default:
                        break;
                }
            }
        }
    }

    /**
     * 订阅children变化
     *
     * @param zkClient
     * @param path
     * Created by Ken Pan on 2017/5/24.
     */
    public static void childChangesListener(ZkClient zkClient, final String path) {
        zkClient.subscribeChildChanges(path, new IZkChildListener() {

            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                System.out.println("clildren of path " + parentPath + ":" + currentChilds);
            }

        });
    }

    /**
     * 订阅节点数据变化
     *
     * @param zkClient
     * @param path
     * Created by Ken Pan on 2017/5/24.
     */
    public static void dataChangesListener(ZkClient zkClient, final String path){
        zkClient.subscribeDataChanges(path, new IZkDataListener(){

            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println("Data of " + dataPath + " has changed.");
            }

            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println("Data of " + dataPath + " has changed.");
            }

        });
    }

    /**
     * 订阅状态变化
     *
     * @param zkClient
     * Created by Ken Pan on 2017/5/24.
     */
    public static void stateChangesListener(ZkClient zkClient){
        zkClient.subscribeStateChanges(new IZkStateListener() {

            public void handleStateChanged(Watcher.Event.KeeperState state) throws Exception {
                System.out.println("handleStateChanged");
            }

            public void handleSessionEstablishmentError(Throwable error) throws Exception {
                System.out.println("handleSessionEstablishmentError");
            }

            public void handleNewSession() throws Exception {
                System.out.println("handleNewSession");
            }
        });
    }
}