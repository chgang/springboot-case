package com.qskx.springboot.webasynctask.service.impl;

import com.qskx.springboot.webasynctask.service.WebAsyncService;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author 111111
 * @date 2018-06-26 09:36
 */
@Service("webAsyncService")
public class WebAsyncServiceImpl implements WebAsyncService {

    @Override
    public String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
