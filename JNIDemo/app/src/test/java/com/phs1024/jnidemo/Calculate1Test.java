package com.phs1024.jnidemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * @author PHS1024
 * @date 2019/8/6 20:04
 */
public class Calculate1Test {
    private Calculate mCalculate;

    @BeforeEach
    public void setUp() throws Exception {
        mCalculate = new Calculate();
    }

    @Test
    public void add() {
        assertNotEquals( 4.0, mCalculate.add(2, 2));
    }
}