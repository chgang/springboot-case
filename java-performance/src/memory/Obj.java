package memory;

/**
 * @ProjectName: springboot-case
 * @Package: memory
 * @ClassName: Obj
 * @Description:
 * @Author: 111111
 * @CreateDate: 2019/1/28 17:03
 * @Version: 1.0
 * Copyright: Copyright (c) 2019
 */
public class Obj {
    public static void main(String[] args) {
        Obj obj = new Obj();
        StrObj1 s1 = new StrObj1();
        StrObj2 s2 = new StrObj2();
        StrObj3 s3 = new StrObj3();
        StrObj4 s4 = new StrObj4();
        Obj[] arrObj = new Obj[10];
        NumObj num = new NumObj();
        System.out.printf(">>>>>>>>>>>>>>> 执行完毕!!");
        //System.gc()
        System.gc();
    }
}