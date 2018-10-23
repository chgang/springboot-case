package com.qskx.quartz.entity;

import java.io.Serializable;

/**
 * @author 111111
 * @date 2018-10-20 22:47
 */
public class SheduleUser implements Serializable {

    private static final long serialVersionUID = 4205744476944452077L;

    private String username;
    private String password;
    private int permission;             // 权限：0-普通用户、1-管理员

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

}
