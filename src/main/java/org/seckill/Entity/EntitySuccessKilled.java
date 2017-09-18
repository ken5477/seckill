package org.seckill.Entity;

import java.util.Date;

/**
 * Created by Administrator on 2017/4/5.
 */
public class EntitySuccessKilled {
    private String seckillId;

    private long userPhone;

    private short state;

    private Date createTime;

    private EntitySeckill seckill;

    public String getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(String seckillId) {
        this.seckillId = seckillId;
    }

    public long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(long userPhone) {
        this.userPhone = userPhone;
    }

    public int getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public EntitySeckill getSeckill() {
        return seckill;
    }

    public void setSeckill(EntitySeckill seckill) {
        this.seckill = seckill;
    }

    @Override
    public String toString() {
        return "EntitySuccessKilled{" +
                "seckillId='" + seckillId + '\'' +
                ", userPhone=" + userPhone +
                ", state=" + state +
                ", createTime=" + createTime +
                '}';
    }
}
