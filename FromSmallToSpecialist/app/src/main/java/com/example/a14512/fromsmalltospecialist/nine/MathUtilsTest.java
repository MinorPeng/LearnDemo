package com.example.a14512.fromsmalltospecialist.nine;

import junit.framework.TestCase;

/**
 * @author 14512 on 2018/4/9
 */

public class MathUtilsTest extends TestCase {

    public void testAdd() {
        assertEquals(0, MathUtils.add(0, 0));
        assertEquals(1, MathUtils.add(1, 0));
        assertEquals(2, MathUtils.add(1, 1));
        assertEquals(0, MathUtils.add(1, -1));
        assertEquals(Integer.MAX_VALUE - 1, MathUtils.add(-1, Integer.MAX_VALUE));
        //错误示例
//        assertEquals(2,5);
    }
}
