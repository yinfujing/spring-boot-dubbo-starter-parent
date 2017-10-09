package com.alibaba.dubbo.spring.boot.autoconfigure.register;

import com.alibaba.dubbo.config.ProtocolConfig;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProtocolConfigRegisterTest {

    @Test
    public void getDefault() throws Exception {
        System.out.println(new ProtocolConfig());
    }
}