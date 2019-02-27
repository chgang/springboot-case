package com.qskx.mockito;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.MessageFormat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootMockitoApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void testMessageFormat() {
        String a= "aaa";
        String b= "bb";
        String c= "c";
        StringBuilder sb = new StringBuilder();
        sb.append(a).append(b).append(c);
        System.out.println(MessageFormat.format(" {0} {1} {2} {3}", a, b,"",sb));
        System.out.println(MessageFormat.format(" ''{0}'' '{1}' {2} {3}", a, b,"",sb.toString()));
    }

}

