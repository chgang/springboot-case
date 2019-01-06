package com.qskx.mockito;

import com.qskx.mockito.entity.Person;
import com.qskx.mockito.mapper.PersonDao;
import com.qskx.mockito.mapper.TimeMockTest;
import com.qskx.mockito.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.internal.verification.api.VerificationData;
import org.mockito.verification.Timeout;
import org.mockito.verification.VerificationMode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @ProjectName: fengjr-p2passet-data
 * @Package: com.fengjr.p2passet.data.testcase
 * @ClassName: PersonServiceTest
 * @Description: java类作用描述
 * @Author: 111111
 * @CreateDate: 2019/1/4 17:17
 * @Version: 1.0
 * Copyright: Copyright (c) 2019
 */
public class PersonServiceTest {

    private PersonDao mockDao;
    private PersonService personService;

    @Before
    public void setUp() throws Exception {
        //模拟PersonDao对象
        mockDao = mock(PersonDao.class);
        when(mockDao.getPerson(1)).thenReturn(new Person(1, "Person1"));
        when(mockDao.update(isA(Person.class))).thenReturn(true);

        personService = new PersonService(mockDao);
    }

    @Test
    public void testUpdate() throws Exception {
        boolean result = personService.update(1, "new name");
        assertTrue("must true", result);
        //验证是否执行过一次getPerson(1)
        verify(mockDao, times(1)).getPerson(eq(1));
        //验证是否执行过一次update
        verify(mockDao, times(1)).update(isA(Person.class));
    }

    @Test
    public void testUpdateNotFind() throws Exception {
        boolean result = personService.update(2, "new name");
        assertFalse("must true", result);
        //验证是否执行过一次getPerson(1)
        verify(mockDao, times(1)).getPerson(eq(1));
        //验证是否执行过一次update
        verify(mockDao, never()).update(isA(Person.class));
    }

    @Test
    public void testVerify() throws Exception {
        //mock creation
        List mockedList = mock(List.class);

        //using mock object
        mockedList.add("one");
        mockedList.add("two");
        mockedList.add("two");
        mockedList.clear();

        //verification
        verify(mockedList).add("one");//验证是否调用过一次 mockedList.add("one")方法，若不是（0次或者大于一次），测试将不通过
        verify(mockedList, times(2)).add("two");
        //验证调用过2次 mockedList.add("two")方法，若不是，测试将不通过
        verify(mockedList).clear();//验证是否调用过一次 mockedList.clear()方法，若没有（0次或者大于一次），测试将不通过
    }

    @Test
    public void testStubbing() throws Exception {
        //你可以mock具体的类，而不仅仅是接口
        LinkedList mockedList = mock(LinkedList.class);

        //设置桩
        when(mockedList.get(0)).thenReturn("first");
        when(mockedList.get(1)).thenThrow(new RuntimeException());

        //打印 "first"
        System.out.println(mockedList.get(0));

        //这里会抛runtime exception
//        System.out.println(mockedList.get(1));

        //这里会打印 "null" 因为 get(999) 没有设置
        System.out.println(mockedList.get(999));

        //Although it is possible to verify a stubbed invocation, usually it's just redundant
        //If your code cares what get(0) returns, then something else breaks (often even before verify() gets executed).
        //If your code doesn't care what get(0) returns, then it should not be stubbed. Not convinced? See here.
        verify(mockedList).get(0);
    }

    @Test
    public void testVoidMethodsWithExceptions() throws Exception {

        LinkedList mockedList = mock(LinkedList.class);
        doThrow(new RuntimeException()).when(mockedList).clear();
        //下面会抛RuntimeException
        mockedList.clear();
    }

    @Test
    public void testVerificationInOrder() throws Exception {
        // A. Single mock whose methods must be invoked in a particular order
        List singleMock = mock(List.class);

        //使用单个mock对象
        singleMock.add("was added first");
        singleMock.add("was added second");

        //创建inOrder
        InOrder inOrder = inOrder(singleMock);

        //验证调用次数，若是调换两句，将会出错，因为singleMock.add("was added first")是先调用的
        inOrder.verify(singleMock).add("was added first");
        inOrder.verify(singleMock).add("was added second");

        // 多个mock对象
        List firstMock = mock(List.class);
        List secondMock = mock(List.class);

        //using mocks
        firstMock.add("was called first");
        secondMock.add("was called second");

        //创建多个mock对象的inOrder
        inOrder = inOrder(firstMock, secondMock);

        //验证firstMock先于secondMock调用
        inOrder.verify(firstMock).add("was called first");
        inOrder.verify(secondMock).add("was called second");
    }

    @Test
    public void testCapturingArguments() throws Exception {
        List mockedList = mock(List.class);
        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        mockedList.add("John");
        mockedList.add("tom");
        //验证后再捕捉参数
        verify(mockedList, times(2)).add(argument.capture());
        //验证参数
        assertEquals(Arrays.asList("John", "tom"), argument.getAllValues());
    }

    @Test
    public void testTimeout() throws Exception {

        TimeMockTest mock = mock(TimeMockTest.class);

        mock.someMethod();
        //测试程序将会在下面这句阻塞100毫秒，timeout的时候再进行验证是否执行过someMethod()
        verify(mock, timeout(5000)).someMethod();
        //和上面代码等价
//        verify(mock, timeout(100).times(1)).someMethod();

        //阻塞100ms，timeout的时候再验证是否刚好执行了2次
//        verify(mock, timeout(100).times(2)).someMethod();

        //timeout的时候，验证至少执行了2次
//        verify(mock, timeout(100).atLeast(2)).someMethod();

        //timeout时间后，用自定义的检验模式验证someMethod()
        VerificationMode yourOwnVerificationMode = new VerificationMode() {
            @Override
            public void verify(VerificationData data) {
                // TODO: 2016/12/4 implement me
            }
        };
        verify(mock, new Timeout(100, yourOwnVerificationMode)).someMethod();
    }

}
