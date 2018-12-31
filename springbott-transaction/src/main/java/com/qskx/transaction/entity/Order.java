package com.qskx.transaction.entity;

import java.io.Serializable;

/**
 * @author 111111
 * @date 2018-12-30 13:40
 */
public class Order implements Serializable {

    private static final long serialVersionUID = 66759358982325549L;

    private String orderId;

    private String UserId;

    private Integer num;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
