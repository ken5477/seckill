package org.seckill.util.Singleton;

/**
 * 效率最高的单例模式
 * 通过jvm内部类实现
 * 静态内部类
 * 达到lazy loading效果
 * Created by Ken Pan on 2017/6/15.
 */
public class Singleton_Best {
    private Singleton_Best(){

    }

    private static class SingletonHolder{
        private final static Singleton_Best instance = new Singleton_Best();
    }

    public static Singleton_Best getInstance(){
        return SingletonHolder.instance;
    }
}
