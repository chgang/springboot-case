package com.qskx.mockito.service;

import com.qskx.mockito.entity.Person;
import com.qskx.mockito.mapper.PersonDao;

/**
 * @ProjectName: fengjr-p2passet-data
 * @Package: com.fengjr.p2passet.data.testcase
 * @ClassName: PersonService
 * @Description: java类作用描述
 * @Author: 111111
 * @CreateDate: 2019/1/4 17:16
 * @Version: 1.0
 * Copyright: Copyright (c) 2019
 */
public class PersonService {

    private final PersonDao personDao;

    public PersonService(PersonDao personDao) {
        this.personDao = personDao;
    }

    public boolean update(int id, String name) {
        Person person = personDao.getPerson(id);
        if (person == null) {
            return false;
        }

        Person personUpdate = new Person(person.getId(), name);
        return personDao.update(personUpdate);
    }

}
