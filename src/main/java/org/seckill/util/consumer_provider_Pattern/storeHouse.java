package org.seckill.util.consumer_provider_Pattern;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ken Pan on 2017/5/19.
 */
class storeHouse {

    //仓库的容量
    private int capacity;
    //Object　是生產的產品
    private List<Object> list = new LinkedList<Object>();

    //构造方法
    public storeHouse(int capacity) {
        this.capacity = capacity;
        System.out.println("当前仓库产品数量：" + list.size());
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * 生产的方法
     *
     * @param num
     * @throws InterruptedException
     */
    public void producer(int num) throws InterruptedException {
        //同步
        synchronized (list) {
            //仓库已满
            while (list.size() + num > this.capacity) {
                System.out.println("【仓库已满，无法再生产" + num + "个产品】" + "当前仓库产品数量：" + list.size());
                list.wait();
            }
            //生产未满，再生产num个产品不会超过仓库容量
            System.out.println("【仓库未满，生产" + num + "个产品没问题】" + "当前仓库产品数量：" + list.size());
            for (int i = 0; i < num; i++) {
                list.add(new Object());
            }
            list.notifyAll();
        }
    }

    public void consumer(int num) throws InterruptedException {
        //同步方法
        synchronized (list) {
            //仓库没有num个产品供消费
            while (list.size() < num) {
                System.out.println("【仓库没有" + num + "个产品供消费】" + "当前仓库产品数量：" + list.size());
                list.wait();
            }
            //仓库有num个产品供消费
            System.out.println("【仓库有" + num + "个产品供消费】" + "当前仓库产品数量：" + list.size());
            for (int i = 0; i < num; i++) {
                list.remove(i);
            }
            list.notifyAll();
        }
    }


static class ProducerThread extends Thread {
    //每次生产的数量
    private int num;
    //仓库
    private storeHouse storehouse;

    //构造函数，设置仓库
    public ProducerThread(storeHouse storehouse, int num) {
        this.num = num;
        this.storehouse = storehouse;
    }

    public void run() {
        try {
            storehouse.producer(num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

static class ConsumerThread extends Thread {
    //每次消费的数量
    private int num;
    //仓库
    private storeHouse storehouse;

    //构造函数，设置仓库
    public ConsumerThread(storeHouse storehouse, int num) {
        this.num = num;
        this.storehouse = storehouse;
    }

    public void run() {
        try {
            storehouse.consumer(num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

    public static void main(String[] args) {
        //仓库对象
        storeHouse storage = new storeHouse(1000);

        // 生产者对象
        ProducerThread p1 = new ProducerThread(storage, 200);
        ProducerThread p2 = new ProducerThread(storage, 200);
        ProducerThread p3 = new ProducerThread(storage, 100);
        ProducerThread p4 = new ProducerThread(storage, 300);
        ProducerThread p5 = new ProducerThread(storage, 400);
        ProducerThread p6 = new ProducerThread(storage, 200);
        ProducerThread p7 = new ProducerThread(storage, 500);

        // 消费者对象
        ConsumerThread c1 = new ConsumerThread(storage, 500);
        ConsumerThread c2 = new ConsumerThread(storage, 200);
        ConsumerThread c3 = new ConsumerThread(storage, 800);

        // 线程开始执行
        c1.start();
        c2.start();
        c3.start();
        p1.start();
        p2.start();
        p3.start();
        p4.start();
        p5.start();
        p6.start();
        p7.start();

    }
}

