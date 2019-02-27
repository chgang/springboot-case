package com.qskx.jsonrpc;

import com.qskx.jsonrpc.impl.ExecuteOrderImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootJsonrpcApplicationTests {

    @Autowired
    private ExecuteOrderImpl executeOrder;

    @Test
    public void contextLoads() {
        System.out.println(">>>>>>>>>> name = " + executeOrder.getName());
    }
}
