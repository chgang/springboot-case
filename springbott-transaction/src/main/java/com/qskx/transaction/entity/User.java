package com.qskx.transaction.entity;

import java.io.Serializable;

/**
 * @author 111111
 * @date 2018-12-30 13:40
 */
public class User implements Serializable {

    private static final long serialVersionUID = -7031393417653458172L;

    private String userId;

    private String sex;

    private Integer age;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
