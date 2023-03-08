package com.products.api.productsapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A class representing an Order completed on the estore
 * @author Richard
 */
public class Coupon implements Model {
    static final String STRING_FORMAT = "Coupon [id=%d, code=%s, discount=%f, expirationDate=%s]";


    @JsonProperty("id") private int id;
    @JsonProperty("code") private String code;
    @JsonProperty("discount") private float discount;
    @JsonProperty("expirationDate") private String expirationDate;
    

    /**
     * Create an Order with the given traits:
     * @param id            The id of the order
        * @param code          The code of the coupon
        * @param discount      The discount of the coupon
     */
    public  Coupon(@JsonProperty("id") int id,
                    @JsonProperty("code") String code,
                    @JsonProperty("discount") float discount,
                    @JsonProperty("expirationDate") String expirationDate) 
    {
        this.id = id;
        this.code = code;
        this.discount = discount;
        this.expirationDate = expirationDate;
    }

    /**
     * Sets the id of the coupon
     * @param id The id of the coupon
     */
    public void setId(int id) {this.id = id;}

    /**
     * Retrieves the id of the coupon
     * @return The id of the coupon
     */
    public int getId() {return this.id;}

    /**
     * Sets the code of the coupon
     * @param code The code of the coupon
     */
    public void setCode(String code) {this.code = code;}

    /**
     * Retrieves the code of the coupon
     * @return The code of the coupon
     */
    public String getCode() {return this.code;}

    /**
     * Sets the discount of the coupon
     * @param discount The discount of the coupon
     */
    public void setDiscount(float discount) {this.discount = discount;}

    /**
     * Retrieves the discount of the coupon
     * @return The discount of the coupon
     */
    public float getDiscount() {return this.discount;}

    /**
     * Sets the expiration date of the coupon
     * @param expirationDate The expiration date of the coupon
     */
    public void setExpirationDate(String expirationDate) {this.expirationDate = expirationDate;}

    /**
     * Retrieves the expiration date of the coupon
     * @return The expiration date of the coupon
     */
    public String getExpirationDate() {return this.expirationDate;}

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, this.id, this.code, this.discount, this.expirationDate);
    }
}
