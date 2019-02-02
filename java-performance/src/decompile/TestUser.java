package decompile;

import decompile.entity.User;

/**
 * @ProjectName: springboot-case
 * @Package: decompile
 * @ClassName: TestUser
 * @Description: java类作用域描述
 * @Author: 111111
 * @CreateDate: 2019/2/2 12:36
 * @Version: 1.0
 * Copyright: Copyright (c) 2019
 */
public class TestUser {
    private int count;

    public void test(int a){
        count = count + a;
    }

    public User initUser(int age,String name){
        User user = new User();
        user.setAge(age);
        user.setName(name);
        return user;
    }

    public void changeUser(User user,String newName){
        user.setName(newName);
    }
}