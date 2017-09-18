package org.seckill.Exception;

/**
 * 重複秒殺異常
 * Created by Ken Pan on 2017/4/7.
 */
public class RepeatKillException extends RuntimeException {
    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
