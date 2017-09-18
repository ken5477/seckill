package org.seckill.Exception;

/**
 * Created by Ken Pan on 2017/4/7.
 */
public class SeckillExcepetion extends RuntimeException {
    public SeckillExcepetion(String message) {
        super(message);
    }

    public SeckillExcepetion(String message, Throwable cause) {
        super(message, cause);
    }
}
