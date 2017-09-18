package org.seckill.Exception;

/**
 * 秒殺關閉異常
 * Created by Ken Pan on 2017/4/7.
 */
public class SeckillCloseException extends RuntimeException{
    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
