package com.qskx.servletdispatcher;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootServletdispatcherApplicationTests {

	@Test
	public void contextLoads() {
		Stream.of("a","b","c").forEach(item -> System.out.println(item));
	}

}
