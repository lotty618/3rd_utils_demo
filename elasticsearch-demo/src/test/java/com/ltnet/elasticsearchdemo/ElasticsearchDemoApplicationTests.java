package com.ltnet.elasticsearchdemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ElasticsearchDemoApplicationTests {

    @Test
    void contextLoads() {
        ClsSubA a = new ClsSubA();
        a.check(0);
    }

    class ClsSubA extends ClsTest {
        @Override
        protected boolean check(int x) {
            x = a;
            if (super.check(x)) {
                if (x == 2)
                    System.out.println("x is 2");
                else
                    System.out.println("x is another number");
            } else {
                System.out.println("x < 1");
            }

            return false;
        }
    }

    abstract class ClsTest {
        protected int a;

        protected boolean check(int x) {
            x = a;
            if (x >= 1)
                return true;
            else
                return false;
        }
    }
}
