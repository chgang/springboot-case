package com.qskx.mockito.entity;

/**
 * @ProjectName: fengjr-p2passet-data
 * @Package: com.fengjr.p2passet.data.testcase
 * @ClassName: Person
 * @Description: java类作用描述
 * @Author: 111111
 * @CreateDate: 2019/1/4 17:15
 * @Version: 1.0
 * Copyright: Copyright (c) 2019
 */
public class Person {

    private final int    id;
    private final String name;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
