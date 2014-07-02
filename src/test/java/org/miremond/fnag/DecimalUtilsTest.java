package org.miremond.fnag;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DecimalUtilsTest {

    @Test
    public void test0Precision() {
        assertEquals(1200, DecimalUtils.toCents("12"));
        assertEquals("12.00", DecimalUtils.toString(1200));
    }

    @Test
    public void test1Precision() {
        assertEquals(1210, DecimalUtils.toCents("12.1"));
        assertEquals("12.10", DecimalUtils.toString(1210));
    }

    @Test
    public void test2Precision() {
        assertEquals(1213, DecimalUtils.toCents("12.13"));
        assertEquals("12.13", DecimalUtils.toString(1213));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test3Precision() {
        DecimalUtils.toCents("12.133");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMultiSeparator() {
        DecimalUtils.toCents("10.12.13");
    }

    @Test
    public void testToString0() {
        assertEquals("0.00", DecimalUtils.toString(0));
    }

    @Test
    public void testToString1() {
        assertEquals("0.01", DecimalUtils.toString(1));
    }

    @Test
    public void testToString2() {
        assertEquals("0.12", DecimalUtils.toString(12));
    }

    @Test
    public void testToString3() {
        assertEquals("1.23", DecimalUtils.toString(123));
    }

    @Test
    public void testToString4() {
        assertEquals("40.00", DecimalUtils.toString(4000));
    }

}
