package com.products.api.productsapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;
import com.products.api.productsapi.persistence.DAO;
import com.products.api.productsapi.persistence.CouponFileDAO;
import com.products.api.productsapi.model.Coupon;


/**
 * Handles the REST API requests for orders
 * 
 * @author Aidan Collins
 */
@RestController
@RequestMapping("coupons")
public class CouponController implements Controller<Coupon> {
    private static final Logger LOG = Logger.getLogger(Coupon.class.getName());
    private CouponFileDAO cDAO;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param oDAO The {@link DAO Order Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public CouponController(CouponFileDAO cDAO) {
        this.cDAO = cDAO;
    }

    /**
     * Responds to the GET request for a {@linkplain Order order} for the given id
     * 
     * @param id The id used to locate the {@link Order order}
     * 
     * @return ResponseEntity with {@link Order order} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Coupon> get(@PathVariable int id) {
        LOG.info("GET /coupons/" + id);
        try {
            Coupon coupon = (Coupon) cDAO.get(id);
            if (coupon != null) {
                return new ResponseEntity<Coupon>(coupon, HttpStatus.OK);
            } else {
                return new ResponseEntity<Coupon>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "GET /coupons/" + id, e);
            return new ResponseEntity<Coupon>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Responds to the GET request for all {@linkplain Order orders}
     * 
     * @return ResponseEntity with array of {@link Order orders} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Coupon[]> getAll() {
        LOG.info("GET /coupons");
        try {
            Coupon[] coupons = cDAO.getAll();
            return new ResponseEntity<Coupon[]>(coupons, HttpStatus.OK);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "GET /coupons", e);
            return new ResponseEntity<Coupon[]>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
    

    /**
     * Creates a {@linkplain Order order} with the provided order object
     * 
     * @param order - The {@link Order order} to create
     * 
     * @return ResponseEntity with created {@link Order order} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @Override
    @PostMapping("/add")
    public ResponseEntity<Coupon> create(@RequestBody Coupon coupon) {
        LOG.info("POST /coupons/add");
        try {
            Coupon createdCoupon = (Coupon) cDAO.create(coupon);
            return new ResponseEntity<Coupon>(createdCoupon, HttpStatus.CREATED);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "POST /coupons/add", e);
            return new ResponseEntity<Coupon>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * Updates the {@linkplain Order order} with the provided {@linkplain Order order} object, if it exists
     *
     * @param order The {@link Order order} to update
     * 
     * @return ResponseEntity with updated {@link Order order} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @Override
    @PutMapping("/edit")
    public ResponseEntity<Coupon> update(@RequestBody Coupon coupon) {
        LOG.info("PUT /coupons/edit");
        try {
            Coupon updatedCoupon = (Coupon) cDAO.update(coupon);
            if (updatedCoupon != null) {
                return new ResponseEntity<Coupon>(updatedCoupon, HttpStatus.OK);
            } else {
                return new ResponseEntity<Coupon>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "PUT /coupons/edit", e);
            return new ResponseEntity<Coupon>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Order order} with the given id
     * 
     * @param id The id of the {@link Order order} to delete
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Coupon> delete(@PathVariable int id) {
        LOG.info("DELETE /coupons/delete/" + id);
        try {
            if (cDAO.delete(id)) {
                return new ResponseEntity<Coupon>(HttpStatus.OK);
            } else {
                return new ResponseEntity<Coupon>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "DELETE /coupons/delete/" + id, e);
            return new ResponseEntity<Coupon>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping ("/search/{code}")
    public ResponseEntity<Coupon[]> search(String text) {
        LOG.info("GET /coupons/search/" + text);
        try {
            Coupon[] coupons = cDAO.search(text);
            return new ResponseEntity<Coupon[]>(coupons, HttpStatus.OK);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "GET /coupons/search/" + text, e);
            return new ResponseEntity<Coupon[]>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}