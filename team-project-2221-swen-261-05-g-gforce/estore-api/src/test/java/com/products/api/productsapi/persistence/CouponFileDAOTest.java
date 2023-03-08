package com.products.api.productsapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.products.api.productsapi.model.Coupon;
import com.products.api.productsapi.model.Model;

/**
 * Test for the AddressFileDAO class.
 * 
 * @author Antar Narayan Chowdhury
 */
@Tag("Persistence-tier")
public class CouponFileDAOTest {
    CouponFileDAO testDAO;
    Coupon[] testCoupons;
    ObjectMapper testMap;

    /**
     * Before each test, create a new ObjectMapper.
     * 
     * @throws IOException
     */
    @BeforeEach
    public void createTestPFile() throws IOException {
        // Initialize testDAO
        testMap = mock(ObjectMapper.class);
        testCoupons = new Coupon[3];
        testCoupons[0] = new Coupon(100, "Test Coupon", 100, "100");
        testCoupons[1] = new Coupon(101, "Test wwCoupon", 101, "101");
        testCoupons[2] = new Coupon(102, "Test wwCoupon", 102, "102");

        when(testMap.readValue(new File("test.txt"), Coupon[].class))
                .thenReturn(testCoupons);

        testDAO = new CouponFileDAO("test.txt", testMap);

    }

    /**
     * Test for getAll().
     * 
     * @throws IOException
     */
    @Test
    public void testGetAll() throws IOException {
        // Create coupon list
        Coupon[] coupons = testDAO.getAll();

        // Assert
        assertEquals(coupons.length, testCoupons.length);
        for (int i = 0; i < testCoupons.length; ++i)
            assertEquals(coupons[i], testCoupons[i]);
    }

    /**
     * Test for search().
     * 
     * @throws IOException
     */
    @Test
    public void testSearch() throws IOException {
        // Create model list
        Coupon[] models = (Coupon[]) testDAO.search("Test Coupon");

        // Assert
        assertEquals(models.length, 1);
        assertEquals(models[0], testCoupons[0]);
    }

    /**
     * Test for get().
     * 
     * @throws IOException
     */
    @Test
    public void testGet() throws IOException {
        // Get coupon
        Coupon coupon = testDAO.get(100);

        // Assert
        assertEquals(coupon, testCoupons[0]);
    }

    /**
     * Test for create().
     * 
     * @throws IOException
     */
    @Test
    public void testCreate() throws IOException {
        // Create new coupon
        Coupon coupon = new Coupon(104, "Test Coupon3", 103, "103");

        // Test create()
        Model result = assertDoesNotThrow(() -> testDAO.create(coupon), "Exception thrown");

        // Assert
        assertNotNull(result);
        Coupon actual = testDAO.get(coupon.getId());

        assertEquals(actual.getId(), coupon.getId());
        assertEquals(actual.getCode(), coupon.getCode());
        assertEquals(actual.getDiscount(), coupon.getDiscount());
        assertEquals(actual.getExpirationDate(), coupon.getExpirationDate());
    }

    /**
     * Test for update().
     * 
     * @throws IOException
     */
    @Test
    public void testUpdate() throws IOException {
        // Create new coupon
        Coupon coupon = new Coupon(100, "Test Coupon1", 101, "101");

        // Test update()
        Model result = assertDoesNotThrow(() -> testDAO.update(coupon), "Exception thrown");

        // Assert
        assertNotNull(result);
        Coupon actual = testDAO.get(coupon.getId());
        assertEquals(actual, coupon);
    }

    /**
     * Test for delete().
     */
    @Test
    public void testDelete() {
        // Test delete()
        boolean result = assertDoesNotThrow(() -> testDAO.delete(100), "Exception thrown");

        // Assert
        assertEquals(result, true);
        assertEquals(testDAO.coupons.size(), testCoupons.length - 1);
    }

    /**
     * Test for an exception thrown from save().
     * 
     * @throws IOException
     */
    @Test
    public void testSaveException() throws IOException {
        doThrow(new IOException()).when(testMap).writeValue(any(File.class), any(Coupon[].class));

        Coupon coupon = new Coupon(105, "Test Coupon3", 103, "103");

        assertThrows(IOException.class, () -> testDAO.create(coupon), "Exception not thrown");
    }

    /**
     * Test for getting a non-existent coupon.
     * 
     * @throws IOException
     */
    @Test
    public void testGetUnknownCoupon() throws IOException {
        // Try to get coupon
        Coupon coupon = testDAO.get(99);

        // Assert
        assertEquals(coupon, null);
    }

    /**
     * Test for deleting a non-existent coupon.
     */
    @Test
    public void testDeleteUnknownCoupon() {
        // Create test
        boolean result = assertDoesNotThrow(() -> testDAO.delete(99), "Unexpected exception thrown");

        // Assert
        assertEquals(result, false);
        assertEquals(testDAO.coupons.size(), testCoupons.length);
    }

    /**
     * Test for updating a non-existent coupon.
     */
    @Test
    public void testUpdateUnknownCoupon() {
        // Create coupon
        Coupon coupon = new Coupon(105, "Test Coupon1", 101, "101");

        // Test update() for unknown coupon
        Model result = assertDoesNotThrow(() -> testDAO.update(coupon), "Unexpected exception thrown");

        // Assert
        assertNull(result);
    }

    /**
     * Test for possible exception caused by coupon constructor.
     * 
     * @throws IOException
     */
    @Test
    public void testConstructorException() throws IOException {
        // Create ObjectMapper
        ObjectMapper testMap = mock(ObjectMapper.class);

        doThrow(new IOException()).when(testMap).readValue(new File("test.txt"), Coupon[].class);

        // Assert
        assertThrows(IOException.class, () -> new CouponFileDAO("test.txt", testMap), "Exception not thrown");
    }

}
