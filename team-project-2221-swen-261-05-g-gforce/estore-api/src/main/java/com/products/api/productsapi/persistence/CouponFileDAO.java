package com.products.api.productsapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.products.api.productsapi.model.Coupon;

/**
 * Implements the functionality for JSON file-based peristance for Coupons
 * 
 * @author RIchard
 */
@Component
public class CouponFileDAO implements DAO<Coupon> {
    Map<Integer,Coupon> coupons;   // Provides a local cache of the order objects
                                // so that we don't need to read from the file
                                // each time
    private ObjectMapper objectMapper;  // Provides conversion between Order
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;  // The next Id to assign to a new order
    private String filename;    // Filename to read from and write to

    /**
     * Creates a Order File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public CouponFileDAO(@Value("${coupons.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        load();  // load the orders from the file
    }

    /**
     * Generates the next id for a new {@linkplain Coupon coupon}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain Order orders} from the tree map
     * 
     * @return  The array of {@link Order orders}, may be empty
     */
    private Coupon[] getCouponsArray() {
        return coupons.values().toArray(new Coupon[0]);
    }

    /**
     * Generates an array of {@linkplain Order orders} from the tree map for any
     * {@linkplain Order orders} that contains the a product that contains the given text containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Order orders}
     * in the tree map
     * 
     * @return  The array of {@link Order orders}, may be empty
     */
    private Coupon[] getCouponsArray(String containsText) { // if containsText == null, no filter
        if (containsText == null) {
            return getCouponsArray();
        }
        Collection<Coupon> filtered = new ArrayList<>();
        for (Coupon coupon : coupons.values()) {
            if (coupon.toString().contains(containsText)) { //((Collection<Coupon>) coupon).contains(containsText)
                filtered.add(coupon);
            }
        }
        return filtered.toArray(new Coupon[filtered.size()]);
    }

    /**
     * Saves the {@linkplain Order orders} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Order orders} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Coupon[] couponArray = getCouponsArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),couponArray);
        return true;
    }

    /**
     * Loads {@linkplain Order orders} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        coupons = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of products
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Coupon[] couponArray = objectMapper.readValue(new File(filename),Coupon[].class);

        // Add each order to the tree map and keep track of the greatest id
        for (Coupon coupon : couponArray) {
            coupons.put(coupon.getId(),coupon);
            if (coupon.getId() >= nextId) {
                nextId = coupon.getId() + 1;
            }
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    
    /**
    * {@inheritDoc}
    * 
    * @return The list of the Orders
    */
    @Override
    public Coupon[] getAll() throws IOException {
        synchronized(coupons) {
            return getCouponsArray();
        }
    }

    /**
    ** {@inheritDoc}
    * @return Return the order list which are satisfies the condtion
     */  
    @Override
    public
    Coupon[] search(String containsText) throws IOException {
        synchronized(coupons){
            Coupon[] sModels = getCouponsArray(containsText); 
            return sModels;
        }
    }

    /**
    ** {@inheritDoc}
     * @return the order which satisifies the provided number
     */
    @Override
    public Coupon get(int id) throws IOException {
        synchronized(coupons) {
            if (coupons.containsKey(id))
                return coupons.get(id);
            else
                return null;
        }
    }

    /**
    ** {@inheritDoc}
    * @return created order 
     */   
    @Override
    public Coupon create(Coupon model) throws IOException {
        synchronized(coupons) {
            Coupon mCoupon = (Coupon) model; 
            // We create a new product object because the id field is immutable
            // and we need to assign the next unique id
            Coupon newCoupon = new Coupon(nextId(),mCoupon.getCode(), mCoupon.getDiscount(), mCoupon.getExpirationDate());
            coupons.put(newCoupon.getId(),newCoupon);
            save();
            return newCoupon;
        }
    }

    /**
    ** {@inheritDoc}
    * @return 
     */
    @Override
    public Coupon update(Coupon model) throws IOException {
        synchronized(coupons) {
            Coupon coupon = (Coupon) model; 
            if (coupons.containsKey(coupon.getId()) == false)
                return null;  // order does not exist   
            coupons.put(coupon.getId(),coupon);
            save(); // may throw an IOException
            return coupon;
        }
    }

    /**
    ** {@inheritDoc}
    * @return a boolean after deleting the order 
     */
    @Override
    public boolean delete(int id) throws IOException {
        synchronized(coupons) {
            if (coupons.containsKey(id)) {
                coupons.remove(id);
                return save();
            }
            else
                return false;
        }
    }
}