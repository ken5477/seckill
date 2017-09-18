package org.seckill.util.Singleton;

/**
 * 双检测的懒汉单例模式
 * Created by Ken Pan on 2017/6/15.
 */
public class Singleton_Better {
    private static Singleton_Better instance=null;

    private Singleton_Better(){

    }

    public static Singleton_Better getInstance(){
        if (instance==null){
            synchronized (Singleton_Better.class){
                if (instance==null){
                    instance = new Singleton_Better();
                }
            }
        }
        return instance;
    }
}
