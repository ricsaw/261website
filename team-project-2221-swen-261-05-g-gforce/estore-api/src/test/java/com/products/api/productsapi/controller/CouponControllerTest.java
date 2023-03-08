package com.products.api.productsapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.products.api.productsapi.persistence.CouponFileDAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.products.api.productsapi.model.Coupon;

/* Tests the {@link CouponController} class
 * 
 * @author Antar Narayan Chowdhuty
 */
@Tag("controller-tier")
public class CouponControllerTest {
    private CouponController couponController;
    private CouponFileDAO mockDAO;

    /**
     * Before each test, create a new CouponController object and inject
     * a mock Coupon DAO
     */
    @BeforeEach
    public void setupCouponController() {
        mockDAO = mock(CouponFileDAO.class);
        couponController = new CouponController(mockDAO);
    }

    @Test
    public void testGetCoupon() throws IOException { // get may throw IOException
        Coupon coupon = new Coupon(8, "987654", 0.50f, "2020-12-31");
        when(mockDAO.get(8)).thenReturn(coupon);

        // Invoke
        ResponseEntity<Coupon> response = couponController.get(coupon.getId());

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(coupon, response.getBody());

    }

    @Test
    public void testGetCouponNotFound() throws Exception { // create may throw IOException
        // Setup
        int couponId = 99;
        // When the same id is passed in, our mock Coupon DAO will return null,
        // simulating
        // no Coupon found
        when(mockDAO.get(couponId)).thenReturn(null);

        // Invoke
        ResponseEntity<Coupon> response = couponController.get(couponId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetCouponHandleException() throws Exception { // create may throw IOException
        // Setup
        int CouponId = 99;
        // When get is called on the Mock Coupon DAO, throw an IOException
        doThrow(new IOException()).when(mockDAO).get(CouponId);

        // Invoke
        ResponseEntity<Coupon> response = couponController.get(CouponId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /*****************************************************************
     * The following tests will fail until all CouponController methods
     * are implemented.
     ****************************************************************/

    @Test
    public void testCreateCoupon() throws IOException { // create may throw IOException
        // Setup
        Coupon coupon = new Coupon(99, "012345", 0.50f, "2020-12-31");
        when(mockDAO.create(coupon)).thenReturn(coupon);

        // Invoke
        ResponseEntity<Coupon> response = couponController.create(coupon);

        // Analyze
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(coupon, response.getBody());
    }

    @Test
    public void testCreateDuplicate() throws IOException { // create may throw IOException
        // Setup
        Coupon coupon = new Coupon(99, "012345", 0.50f, "2020-12-31");
        Coupon coupon1 = new Coupon(99, "012345", 0.50f, "2020-12-31");

        Coupon[] listWithDuplicates = new Coupon[] { coupon, coupon1 };

        // when create is called, return true simulating successful
        // creation and save
        when(mockDAO.search(coupon.getCode())).thenReturn(listWithDuplicates);

        // Invoke
        ResponseEntity<Coupon> response = couponController.create(coupon);
        response = couponController.create(coupon);

        // Analyze
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testCreateCouponHandleException() throws IOException { // create may throw IOException
        // Setup
        Coupon coupon = new Coupon(99, "012345", 0.50f, "2020-12-31");

        // When create is called on the Mock Coupon DAO, throw an IOException
        doThrow(new IOException()).when(mockDAO).create(coupon);

        // Invoke
        ResponseEntity<Coupon> response = couponController.create(coupon);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testUpdateCoupon() throws IOException { // create may throw IOException
        // Setup
        Coupon coupon = new Coupon(100, "Raddest Food", 0.10f, "2020-12-31");

        // when create is called, return true simulating successful
        // creation and save
        when(mockDAO.update(coupon)).thenReturn(coupon);
        when(mockDAO.get(coupon.getId())).thenReturn(coupon);
        // Invoke
        ResponseEntity<Coupon> response = couponController.update(coupon);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(coupon, response.getBody());
    }

    @Test
    public void testUpdateCouponFailed() throws IOException { // update may throw IOException
        // Setup
        Coupon coupon = new Coupon(100, "Raddest Food", 0.10f, "2020-12-31");
        // when update is called, return true simulating successful
        // update and save
        when(mockDAO.update(coupon)).thenReturn(null);

        // Invoke
        ResponseEntity<Coupon> response = couponController.update(coupon);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateCouponHandleException() throws IOException { // update may throw IOException
        // Setup
        Coupon coupon = new Coupon(100, "Raddest Food", 0.10f, "2020-12-31");

        // When update is called on the Mock Coupon DAO, throw an IOException
        doThrow(new IOException()).when(mockDAO).update(coupon);

        // Invoke
        ResponseEntity<Coupon> response = couponController.update(coupon);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetcoupons() throws IOException { // getAll may throw IOException
        // Setup
        Coupon[] coupons = new Coupon[2];
        coupons[0] = new Coupon(99, "Raddest Food", 0.10f, "2020-12-31");
        coupons[1] = new Coupon(100, "Food", 0.20f, "2020-12-31");

        // When getAll is called return the coupons created above
        when(mockDAO.getAll()).thenReturn(coupons);

        // Invoke
        ResponseEntity<Coupon[]> response = couponController.getAll();

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(coupons, response.getBody());
    }

    @Test
    public void testGetcouponsHandleException() throws IOException { // getAll may throw IOException
        // Setup
        // When getAll is called on the Mock Coupon DAO, throw an IOException
        doThrow(new IOException()).when(mockDAO).getAll();

        // Invoke
        ResponseEntity<Coupon[]> response = couponController.getAll();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testSearchcoupons() throws IOException { // search may throw IOException
        // Setup
        String searchString = "la";
        Coupon[] coupons = new Coupon[2];
        coupons[0] = new Coupon(99, "Raddest Food", 0.10f, "2020-12-31");
        coupons[1] = new Coupon(100, "Food", 0.20f, "2020-12-31");

        // When search is called with the search string, return the two
        /// coupons above
        when(mockDAO.search(searchString)).thenReturn(coupons);

        // Invoke
        ResponseEntity<Coupon[]> response = couponController.search(searchString);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(coupons, response.getBody());
    }

    @Test
    public void testSearchcouponsHandleException() throws IOException { // search may throw IOException
        // Setup
        String searchString = "an";
        // When create is called on the Mock Coupon DAO, throw an IOException
        doThrow(new IOException()).when(mockDAO).search(searchString);

        // Invoke
        ResponseEntity<Coupon[]> response = couponController.search(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteCoupon() throws IOException { // delete may throw IOException
        // Setup
        int CouponId = 99;
        // when delete is called return true, simulating successful deletion
        when(mockDAO.delete(CouponId)).thenReturn(true);

        // Invoke
        ResponseEntity<Coupon> response = couponController.delete(CouponId);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteCouponNotFound() throws IOException { // delete may throw IOException
        // Setup
        int CouponId = 99;
        // when delete is called return false, simulating failed deletion
        when(mockDAO.delete(CouponId)).thenReturn(false);

        // Invoke
        ResponseEntity<Coupon> response = couponController.delete(CouponId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteCouponHandleException() throws IOException { // delete may throw IOException
        // Setup
        int CouponId = 99;
        // When delete is called on the Mock Coupon DAO, throw an IOException
        doThrow(new IOException()).when(mockDAO).delete(CouponId);

        // Invoke
        ResponseEntity<Coupon> response = couponController.delete(CouponId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
