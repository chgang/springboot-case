package com.qskx.springbootsync.lock.distribute;

import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;

import java.io.UnsupportedEncodingException;

/**
 * @ProjectName: springboot-case
 * @Package: com.qskx.springbootsync.lock.distribute
 * @ClassName: MyZkSerializer
 * @Description: java类作用域描述
 * @Author: 111111
 * @CreateDate: 2019/2/27 18:49
 * @Version: 1.0
 * Copyright: Copyright (c) 2019
 */
public class MyZkSerializer implements ZkSerializer {

    @Override
    public Object deserialize(byte[] bytes) throws ZkMarshallingError {
        try {
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new ZkMarshallingError(e);
        }
    }

    @Override
    public byte[] serialize(Object obj) throws ZkMarshallingError {
        try {
            return String.valueOf(obj).getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new ZkMarshallingError(e);
        }
    }
}