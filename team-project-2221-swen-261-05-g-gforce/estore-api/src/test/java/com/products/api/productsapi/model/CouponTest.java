package com.products.api.productsapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/*
 * Test class for Coupon
 * @author: Antar Narayan Chowdhury
 */
@Tag("Model-tier")
public class CouponTest {
    // Pakage private for test
    static final String STRING_FORMAT = "Coupon [id=%d, code=%s, discount=%f, expirationDate=%s]";

    @Test
    public void TestConstructor() {
        // Setup
        int id = 4;
        String code = "code";
        float discount = 0.5f;
        String expirationDate = "2020-12-31";

        // Invoke
        Coupon couponObject = new Coupon(id, code, discount, expirationDate);

        // Analyze
        assertEquals(id, couponObject.getId());
        assertEquals(code, couponObject.getCode());
        assertEquals(discount, couponObject.getDiscount());

    }

    @Test
    public void TestId() {
        // Setup
        int id = 4;
        String code = "code";
        float discount = 0.5f;
        String expirationDate = "2020-12-31";
        Coupon couponObject = new Coupon(id, code, discount, expirationDate);

        int expectedId = 99;

        // Invoke
        couponObject.setId(expectedId);

        // Analyze
        assertEquals(expectedId, couponObject.getId());

    }

    @Test
    public void TestCode() {
        // Setup
        int id = 4;
        String code = "code";
        float discount = 0.5f;
        String expirationDate = "2020-12-31";
        Coupon couponObject = new Coupon(id, code, discount, expirationDate);

        String expectedName = "Antar";

        // Invoke
        couponObject.setCode(expectedName);

        // Analyze
        assertEquals(expectedName, couponObject.getCode());

    }

    @Test
    public void TestDiscount() {
        // Setup
        int id = 4;
        String code = "code";
        float discount = 0.5f;
        String expirationDate = "2020-12-31";
        Coupon couponObject = new Coupon(id, code, discount, expirationDate);

        Float expectedNumber = 0.1f;

        // Invoke
        couponObject.setDiscount(expectedNumber);

        // Analyze
        assertEquals(expectedNumber, couponObject.getDiscount());

    }

    @Test
    public void TestExpiration() {
        // Setup
        int id = 4;
        String code = "code";
        float discount = 0.5f;
        String expirationDate = "2020-12-31";
        Coupon couponObject = new Coupon(id, code, discount, expirationDate);

        String expectedAddress = "2020-12-32";

        // Invoke
        couponObject.setExpirationDate(expectedAddress);

        // Analyze
        assertEquals(expectedAddress, couponObject.getExpirationDate());

    }

    @Test
    public void testToString() {
        // Setup
        int id = 4;
        String code = "code";
        float discount = 0.5f;
        String expirationDate = "2020-12-31";
        Coupon couponObject = new Coupon(id, code, discount, expirationDate);

        // Invoke
        String expected = String.format(STRING_FORMAT, id, code, discount, expirationDate);

        // Analyze
        assertEquals(expected, couponObject.toString());

    }

}
