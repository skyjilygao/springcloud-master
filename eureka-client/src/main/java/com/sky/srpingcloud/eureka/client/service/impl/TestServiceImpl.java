package com.sky.srpingcloud.eureka.client.service.impl;

import com.sky.srpingcloud.eureka.client.service.TestService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class TestServiceImpl implements TestService {
    private Integer aa = 0;
    @Override
    public String tt() {
        return ""+(++aa);
    }
}
