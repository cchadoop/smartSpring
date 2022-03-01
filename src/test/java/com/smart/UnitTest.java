package com.smart;

import cn.hutool.core.lang.Pair;
import org.junit.jupiter.api.Test;

public class UnitTest {

    @Test
    public void testPair() {
        Pair<Integer, String> pair = new Pair<>(1, "One");
        Integer key = pair.getKey();
        String value = pair.getValue();
        System.out.println(key + "------" + value);

        ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
        threadLocal.set(1);

        System.out.println(threadLocal.get());

    }


    @Test
    public void test() {
        jj:
        for (int j = 0; j < 2; j++) {
            ii:
            for (int i =0 ; i < 6; i++) {
                if (i > 3) {
                    break jj;
                }
                System.out.println("----III的值：" + i);  // 0 , 1 ,2 ,3
            }
            System.out.println("----JJJ的值：" + j); // 1 2 1 2
        }
    }

    @Test
    public void test2() {
        for (int j = 0; j < 2; j++) {
            for (int i =0 ; i < 6; i++) {
                if (i > 3) {
                    break;
                }
                System.out.println("----III的值：" + i);  // 0 , 1 ,2 ,3
            }
            System.out.println("----JJJ的值：" + j); // 1 2 1 2
        }
    }
}
