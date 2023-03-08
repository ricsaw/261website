package com.products.api.productsapi.model;

import java.util.ArrayList;
import java.util.Collections;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * A class representing a User
 * 
 * Notes:
 *      - I went ahead and made getters/setters for each of the variables because
 *        I figured they would be needed for deserialization. 
 *      
 *      - Also I used //region later in the code, that just allows part of the code to
 *        be collapsed to save space and organize things
 * 
 * @author Aidan Collins
 */
public class User implements Model {
    // Package private for tests
    static final String STRING_FORMAT = "User [ id=%d, name=%s, username=%s, password=%s, isadmin=%s, cart=%s, currentOrders=%s, previousOrders=%s, coupons=%s, address=%s, darkMode=%s]";

    //static final String STRING_FORMAT = "User: %s\n\t- id: %d\n\t- price: %.2f\n\t- quantity: %d\n\t- description: \"%s\" adress: \"%s\"";

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("username") private String username;
    @JsonProperty("password") private String password;
    @JsonProperty("isAdmin") private boolean isAdmin;
    @JsonProperty("cart") private ArrayList<Product> cart;
    @JsonProperty("currentOrders") private ArrayList<Order> currentOrders;
    @JsonProperty("previousOrders") private ArrayList<Order> previousOrders;
    @JsonProperty("coupons") private ArrayList<Coupon> coupons;
    @JsonProperty("address") private ArrayList<Address> address;
    @JsonProperty("darkMode") private boolean darkMode;
    
    /**
     * Create a user with the given traits:
     * @param id            The id of the user
     * @param name          The name of the user
     * @param username      The description of the user
     * @param password      The price of the user
     * @param isAdmin       Whether or not user is an admin
     * @param cart          The user's cart
     * @param currentOrders The user's current orders
     * @param previousOrders The user's previous orders
     * @param coupons       The user's coupons
     * @param address       The user's address
     * @param darkMode      Whether or not the user is in dark mode
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public User(@JsonProperty("id") int id,
                @JsonProperty("name") String name,
                @JsonProperty("username") String username,
                @JsonProperty("password") String password,
                @JsonProperty("isAdmin") boolean isAdmin,
                @JsonProperty("cart") ArrayList<Product> cart,
                @JsonProperty("currentOrders") ArrayList<Order> currentOrders,
                @JsonProperty("previousOrders") ArrayList<Order> previousOrders,
                @JsonProperty("coupons") ArrayList<Coupon> coupons,
                @JsonProperty("address") ArrayList<Address> address,
                @JsonProperty("darkMode") boolean darkMode)

    {                
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.darkMode = darkMode;
        if(cart == null)
        {
            this.cart = new ArrayList<>();
        }
        else
        {
            this.cart = cart;
        }
        if(currentOrders == null)
        {
            this.currentOrders = new ArrayList<>();
        }
        else
        {
            this.currentOrders = currentOrders;
        }
        if(previousOrders == null)
        {
            this.previousOrders = new ArrayList<>();
        }
        else
        {
            this.previousOrders = previousOrders;
        }
        if(coupons == null)
        {
            this.coupons = new ArrayList<>();
        }
        else
        {
            this.coupons = coupons;
        }
        if(address == null)
        {
            this.address = new ArrayList<>();
        }
        else
        {
            this.address = address;
        }
    }

    //region Getters and Setters

    /**
     * Sets the name of the user - necessary for JSON object to Java object deserialization
     * @param name The name of the user
     */
    public void setName(String name) {this.name = name;}

    /**
     * Retrieves the name of the user
     * @return The name of the user
     */
    public String getName() {return name;}

    /**
     * Sets the id of the user - necessary for JSON object to Java object deserialization
     * @param id The id of the user
     */
    public void setId(int id) {this.id = id;}

    /**
     * Retrieves the id of the user
     * @return The id of the user
     */
    public int getId() {return this.id;}
    
    /**
     * Sets the username of the user - necessary for JSON object to Java object deserialization
     * @param username The description of the user
     */
    public void setUsername(String username) {this.username = username;}

    /**
     * Retrieves the username of the user
     * @return The username of the user
     */
    public String getUsername() {return this.username;}

    /**
     * Sets the password of the user - necessary for JSON object to Java object deserialization
     * @param password The quantity of the user
     */
    public void setPassword(String password) {this.password = password;}

    /**
     * Retrieves the password of the user
     * @return The password of the user
     */
    public String getPassword() {return this.password;}

    /**
     * Sets the isAdmin boolean of the user - necessary for JSON object to Java object deserialization
     * @param isAdmin: Whether or not to set user as admin
     */
    public void setAdmin(boolean isAdmin) {this.isAdmin = isAdmin;}

    /**
     * Retrieves the isAdmin boolean of user
     * @return Whether or not user is an admin
     */
    public boolean getAdmin() {return this.isAdmin;}

    public boolean addToCart(Product product){
        for(Product i : this.cart){
            if(i.getId() == product.getId()){
                cart.remove(i);
                i.increaseQuantity();
                cart.add(i);
                return true;
            }
        }product.setQuantity(1);
        this.cart.add(product);
        return true;
        
    }

    public boolean removeFromCart(int id){
        for(Product i : this.cart){
            if(i.getId() == id){
                i.decreaseQuantity();
                if(i.getQuantity() == 0) this.cart.remove(i);
                return true;
            }
        }
        return false;
    }
    
    public Product searchCart(int id){
        for(Product i : this.cart){
            if(i.getId() == id){
                return i;
            }
        }
        return null;
    }
    /**
     * Sets the cart of the user - necessary for JSON object to Java object deserialization
     * @param cart: The cart to set
     */
    public void setCart(ArrayList<Product> cart) {this.cart = cart;}

    /**
     * Retrieves the cart of user
     * @return the user cart
     */
    public ArrayList<Product> getCart() {return this.cart;}

    /**
     * Changes the admin status of the user
     */
    public void changeAdminStatus() {
        this.isAdmin = !this.isAdmin;
    }

    /**
     * Get the total price of the user's cart
     */
    public float getTotal(){
        float Total = 0;
        ArrayList<Product> currentCart = this.getCart();
        for(Product i: currentCart){
            Total += (i.getPrice() * i.getQuantity()); 
        }
        return Total;   
    }

    /**
     * Empties the user's cart
     */
    public boolean emptyCart() {
        this.cart.clear();
        return true;
    }


    /**
     * Get the total price of the user's cart with a coupon
     */
    public float getTotalWithCoupon(Coupon coupon){
        float Total = 0;
        ArrayList<Product> currentCart = this.getCart();
        
           for(Product i: currentCart){
            Total += (i.getPrice() * i.getQuantity()); 
        }
        return Total - coupon.getDiscount();   
    }

    /**
     * Sets the coupons of the user - necessary for JSON object to Java object deserialization
     * @param coupons: The coupons to set
     */
    public void setCoupons(ArrayList<Coupon> coupons) {this.coupons = coupons;}

    /**
     * Retrieves the coupons of user
     * @return the user coupons
     */
    public ArrayList<Coupon> getCoupons() {return this.coupons;}

    /**
     * Adds a coupon to the user's coupons
     * @param coupon: The coupon to add
     */
    public boolean addCoupon(Coupon coupon) {return this.coupons.add(coupon);}

    /**
     * Removes a coupon from the user's coupons
     * @param coupon: The coupon to remove
     * @return 
     */
    public boolean removeCoupon(int couponId) {
        for(Coupon i : this.coupons){
            if(i.getId() == couponId){
                this.coupons.remove(i);
                return true;
            }
        }
        return false;
    }
    
    
    /**
     * Adds an adress to the user's coupons
     * @param address: The coupon to add
     */
    public boolean addAddress(Address address) {
        if(this.address.size() == 0){
            address.setDefaultOrNot(true);
            this.address.add(address);
            return true;
        }
        this.address.set(0, address);
        return true;
    }


    //get single address and return it
    public Address getAddress(int addressId){
        for(Address i : this.address){
            if(i.getId() == addressId){
                return i;
            }
        }
        return null;
    }

    /**
     * Get the default user address 
     * @return default address
     */
    public Address getDefaultAddress(){
        for(Address i : this.address){
            if(i.getDefaultOrNot()){
                return i;
            }
        }
        return null;
    }

    /**
     * Sort the cart by name
     */
    public void sortCartByName(){
        Collections.sort(this.cart, (o1, o2) -> o1.getName().compareTo(o2.getName()));
    }

       /**
     * Sets the addresses of the user - necessary for JSON object to Java object deserialization
     * @param addresses: The addresses to set
     */
    public void setAddresses(ArrayList<Address> addresses) {this.address = addresses;}

    /**
     * Retrieves the addresses of user
     * @return the user addresses
     */
    public ArrayList<Address> getAddresses() {return this.address;}

    /**
     * Adds a address to the user's addresses
     * @param address: The address to add
     */
    public boolean addAdress(Address address) {return this.address.add(address);}

    /**
     * Removes a address from the user's addresses
     * @param address: The address to remove
     * @return 
     */
    public boolean removeAdress(int addressId) {
        for(Address i : this.address){
            if(i.getId() == addressId){
                this.address.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Sets the orders of the user - necessary for JSON object to Java object deserialization
     * @param orders: The orders to set
     */
    public void setCurrentOrders(ArrayList<Order> orders) {this.currentOrders = orders;}

    /**
     * Retrieves the orders of user
     * @return the user orders
     */
    public ArrayList<Order> getCurrentOrders() {return this.currentOrders;}


     /**
     * Sets the orders of the user - necessary for JSON object to Java object deserialization
     * @param orders: The orders to set
     */
    public void setPreviousOrders(ArrayList<Order> orders) {this.previousOrders = orders;}

    /** Get the previous orders
     * @return the previous orders
     */
    public ArrayList<Order> getPreviousOrders() {return this.previousOrders;}

    /**
     * Adds a order to the user's orders
     * @param order: The order to add
     */
    public boolean addCurrentOrder(Order order) {return this.currentOrders.add(order);}


        /**
     * Adds a order to the user's orders
     * @param order: The order to add
     */
    public boolean addPreviousOrder(Order order) {return this.previousOrders.add(order);}

    /**
     * applyCoupon should apply a coupon to the user's cart and return the total
     * price of the cart
     */
    public float applyCoupon(Coupon coupon) {
        float total = 0;
        for (Product i : this.cart) {
            // get quantity of product and price
            int quantity = i.getQuantity();
            float price = i.getPrice();
            // multiply quantity and price
            float subtotal = quantity * price;
            // add subtotal to total
            total += subtotal;
        }
        total = total - (total * coupon.getDiscount());
        return total;
    }

    // get single coupon and return it
    public Coupon getCoupon(int couponId) {
        for (Coupon i : this.coupons) {
            if (i.getId() == couponId) {
                return i;
            }
        }
        return null;
    }


    //endregion

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() { 
        return String.format(STRING_FORMAT, this.id,  this.name, this.username, this.password, this.isAdmin, this.cart, this.currentOrders, this.previousOrders, this.coupons, this.address, this.darkMode);
    }

    
}
