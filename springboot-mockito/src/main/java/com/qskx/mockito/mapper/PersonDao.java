package com.qskx.mockito.mapper;

import com.qskx.mockito.entity.Person;

/**
 * @ProjectName: fengjr-p2passet-data
 * @Package: com.fengjr.p2passet.data.testcase
 * @ClassName: PersonDao
 * @Description: java类作用描述
 * @Author: 111111
 * @CreateDate: 2019/1/4 17:16
 * @Version: 1.0
 * Copyright: Copyright (c) 2019
 */
public interface PersonDao {

    Person getPerson(int id);

    boolean update(Person person);
}
