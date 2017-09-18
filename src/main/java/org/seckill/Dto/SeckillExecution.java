package org.seckill.Dto;

import org.seckill.Entity.EntitySuccessKilled;
import org.seckill.Enum.SeckillStatEnum;

/**
 * 封裝秒殺執行結果
 * Created by Ken Pan on 2017/4/7.
 */
public class SeckillExecution {

    private long seckillId;
    //秒殺結果狀態
    private int state;
    //結果表示
    private String stateInfo;

    private EntitySuccessKilled successKilled;

    public SeckillExecution(long seckillId, SeckillStatEnum statEnum, EntitySuccessKilled successKilled) {
        this.seckillId = seckillId;
        this.state = statEnum.getState();
        this.stateInfo = statEnum.getStateInfo();
        this.successKilled = successKilled;
    }

    public SeckillExecution(long seckillId, SeckillStatEnum statEnum) {
        this.seckillId = seckillId;
        this.state = statEnum.getState();
        this.stateInfo = statEnum.getStateInfo();
    }

    @Override
    public String toString() {
        return "SeckillExecution{" +
                "seckillId=" + seckillId +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", successKilled=" + successKilled +
                '}';
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public EntitySuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(EntitySuccessKilled successKilled) {
        this.successKilled = successKilled;
    }
}
