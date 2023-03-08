package com.products.api.productsapi.model;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A class representing an Order completed on the estore
 * @author Aidan Collins
 */
public class Order implements Model {
    static final String STRING_FORMAT = "Order [id=%d, userId=%d, cardNumber=\"%s\", expDate=\"%s\", securityCode=%d, products=\"%s\", address= \"%s\"]";


    @JsonProperty("id") private int id;
    @JsonProperty("userId") private int userId;
    @JsonProperty("cardNumber") private String cardNumber;
    @JsonProperty("expDate") private String expDate;
    @JsonProperty("securityCode") private int securityCode;
    @JsonProperty("products") private ArrayList<Product> products;
    @JsonProperty("address") private ArrayList<Address> address;
   
    

    /**
     * Create an Order with the given traits:
     * @param id            The id of the order
     * @param userId        The id of the user who placed the order
     * @param address       The address of the user who placed the order
     * @param cardNumber    The card number of the user who placed the order
     * @param expDate       The expiration date associated with the card
     * @param securityCode  The security code associated with the card
     * @param products      The list of products in order
     */
    public  Order(@JsonProperty("id") int id,
                  @JsonProperty("userId") int userId,
                  @JsonProperty("cardNumber") String cardNumber,
                  @JsonProperty("expDate") String expDate,
                  @JsonProperty("securityCode") int securityCode,
                  @JsonProperty("products") ArrayList<Product> products,
                  @JsonProperty("address") ArrayList<Address> address)
    {                
        this.id = id;
        this.userId = userId;
        this.address = address;
        this.cardNumber = cardNumber;
        this.expDate = expDate;
        this.securityCode = securityCode;
        this.products = products;  
    }


    /**
     * Sets the id of the Order
     * @param id The id of the Order
     */
    public void setId(int id) {this.id = id;}

    /**
     * Retrieves the id of the Order
     * @return The id of the Order
     */
    public int getId() {return this.id;}

    /**
     * Sets the userId of the Order
     * @param userId The userId of the Order
     */
    public void setUserId(int userId) {this.userId = userId;}

    /**
     * Retrieves the userId of the Order
     * @return The userId of the Order
     */
    public int getUserId() {return this.userId;}


    /* 
     * Sets the address of the Order
     * @param address The address of the Order
     */
    public void setAddress(ArrayList<Address> address) {this.address = address;}

    /**
     * Retrieves the address of the Order
     * @return The address of the Order
     */
    public ArrayList<Address> getAddress() {return this.address;}


    /**
     * Sets the cardNumber of the Order
     * @param cardNumber The cardNumber of the Order
     */
    public void setCardNumber(String cardNumber) {this.cardNumber = cardNumber;}

    /**
     * Retrieves the cardNumber of the Order
     * @return The cardNumber of the Order
     */
    public String getCardNumber() {return this.cardNumber;}

    /**
     * Sets the expDate of the Order
     * @param expDate The expDate of the Order
     */
    public void setExpDate(String expDate) {this.expDate = expDate;}

    /**
     * Retrieves the expDate of the Order
     * @return The expDate of the Order
     */
    public String getExpDate() {return this.expDate;}

    /**
     * Sets the securityCode of the Order
     * @param securityCode The securityCode of the Order
     */
    public void setSecurityCode(int securityCode) {this.securityCode = securityCode;}

    /**
     * Retrieves the securityCode of the Order
     * @return The securityCode of the Order
     */
    public int getSecurityCode() {return this.securityCode;}

    /**
     * Sets the products of the Order
     * @param products The products of the Order
     */
    public void setProducts(ArrayList<Product> products) {this.products = products;}

    /**
     * Retrieves the products of the Order
     * @return The products of the Order
     */
    public ArrayList<Product> getProducts() {return this.products;}


    @Override
    public String toString() {              
        return String.format(STRING_FORMAT, this.id, this.userId, this.cardNumber, this.expDate, 
                             this.securityCode, this.products.toString(), this.address.toString());
    }
}