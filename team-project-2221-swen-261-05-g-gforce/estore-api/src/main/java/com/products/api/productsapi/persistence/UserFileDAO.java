package com.products.api.productsapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


import com.products.api.productsapi.model.Product;
import com.products.api.productsapi.model.Address;
import com.products.api.productsapi.model.Coupon;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.products.api.productsapi.model.User;
import com.products.api.productsapi.model.Order;



/**
 * Handles the REST API requests for users
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author Antar Narayan Chowdhury
 */
@Component
public class UserFileDAO implements DAO<User>{
    Map<Integer,User> users;   // Provides a local cache of the user objects
                                // so that we don't need to read from the file
                                // each time
    private ObjectMapper objectMapper;  // Provides conversion between User
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;  // The next Id to assign to a new user
    private String filename;    // Filename to read from and write to
 
    /**
     * Creates a User File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public UserFileDAO(@Value("${users.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        load();  // load the users from the file
    }

    /**
     * Generates the next id for a new {@linkplain User user}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain User users} from the tree map
     * 
     * @return  The array of {@link User users}, may be empty
     */
    private User[] getUsersArray() {
        return getUsersArray(null);
    }

    /**
     * Generates an array of {@linkplain User users} from the tree map for any
     * {@linkplain User users} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain User users}
     * in the tree map
     * 
     * @return  The array of {@link User users}, may be empty
     */
    private User[] getUsersArray(String containsText) { // if containsText == null, no filter
        ArrayList<User> userArrayList = new ArrayList<>();

        for (User user : users.values()) {
            if (containsText == null || user.getUsername().contains(containsText)) {
                userArrayList.add(user);

            }
        }
        if (userArrayList.size() == 0) {
            return new User[0];
        }
        else {
            User[] userArray = new User[userArrayList.size()];
            userArrayList.toArray(userArray);
            System.out.println("userArray: " + userArray);
            return userArray;
        }
    }

    /**
     * Saves the {@linkplain User users} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link User users} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        User[] userArray = getUsersArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),userArray);
        return true;
    }

    /**
     * Loads {@linkplain User users} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        users = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of users
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        User[] userArray = objectMapper.readValue(new File(filename),User[].class);

        // Add each user to the tree map and keep track of the greatest id
        for (User user : userArray) {
            users.put(user.getId(),user);
            if (user.getId() > nextId)
                nextId = user.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }
    
    /**
    ** {@inheritDoc}
    *@return a list of users 
     */
    @Override
    public User[] getAll() throws IOException {
        synchronized(users) {
            return getUsersArray();
        }
    }
    
    /**
    ** {@inheritDoc}
    *@return return a list of user which satisfies the condition 
     */
    @Override
    public User[] search(String containsText) throws IOException {
        synchronized(users) {
            return getUsersArray(containsText);
        }
    }
    
    /**
    ** {@inheritDoc}
     * @return the user which has the provided id
     */
    @Override
    public User get(int id) throws IOException {
        synchronized(users) {
            if (users.containsKey(id))
                return users.get(id);
            else
                return null;
        }
    }
    
    /**
    ** {@inheritDoc}
    * @return return created user 
     */
    @Override
    public User create(User model) throws IOException {
        User user = model; 
        synchronized(users) {
            // Throws IO exception if user with the same name already exists
            for (User p : users.values()) {
                if (user.getName().contains(p.getName()) || user.getUsername().contains(p.getUsername())) {
                    throw new IOException();
                }
            }
            // We create a new user object because the id field is immutable
            // and we need to assign the next unique id
            ArrayList<Address> addresses = new ArrayList<Address>();

            User newUser = new User(nextId(), user.getName(), user.getUsername(), user.getPassword(),
                           user.getAdmin(), new ArrayList<Product>(), new ArrayList<Order>(), new ArrayList<Order>(), null, addresses, false);
            users.put(newUser.getId(),newUser);
            save(); // may throw an IOException
            return newUser;
        }
    }

    /**
    ** {@inheritDoc}
    * @return the updated user
     */
    @Override
    public User update(User model) throws IOException {
        synchronized(users) {
            User user = model; 
            if (users.containsKey(user.getId()) == false)
                return null;  // user does not exist   
            users.put(user.getId(),user);
            save(); // may throw an IOException
            return user;
        }
    }   
    /**
    ** {@inheritDoc}
    * @return a boolean after the user deleted
     */
    @Override
    public boolean delete(int id) throws IOException {
        synchronized(users) {
            if (users.containsKey(id)) {
                users.remove(id);
                return save();
            }
            else
                return false;
        }
    }
    /**
    ** Add a new item in a Cart
    *
     */
    public boolean addToCart(int userId, Product product) throws IOException {
        synchronized(users) {}
                User user = users.get(userId); 
                boolean result = user.addToCart(product);
                user = update(user); 
                //sort the cart
                user.sortCartByName();
                return result;    
    }
        /**
    ** Add a new item in a Cart
    *
     */
    public boolean removeFromCart(int userId, int productId ) throws IOException {
        synchronized(users) {}
                User user = users.get(userId); 
                boolean result = user.removeFromCart(productId);
                user = update(user); 
                return result;    
    }

    /**
     * addCoupon method adds a coupon to the user's coupon list
     */
    public boolean addCoupon(int userId, Coupon coupon) throws IOException {
        synchronized(users) {}
                User user = users.get(userId); 
                boolean result = user.addCoupon(coupon);
                user = update(user); 
                return result;    
    }
    

    /**
     * removeCoupon method removes a coupon from the user's coupon list
    */

    public boolean removeCoupon(int userId, int couponId) throws IOException {
        synchronized(users) {}
                User user = users.get(userId); 
                boolean result = user.removeCoupon(couponId);
                user = update(user); 
                return result;    
    }

    /**
     * applyCoupon method applies a coupon to the user's cart
     */
    public float applyCoupon(int userId, Coupon coupon) throws IOException {
        synchronized(users) {}
                User user = users.get(userId); 
                float result = user.applyCoupon(coupon);
                user = update(user); 
                return result;    
    }
    
    //get Coupon
    public ArrayList<Coupon> getCoupons(int userId, int couponId) throws IOException {
        synchronized(users) {}
                User user = users.get(userId); 
                ArrayList<Coupon> result = user.getCoupons();
                return result;    
    }

    /**
     * get coupon by id
     */
    public Coupon getCoupon(int userId, int couponId) throws IOException {
        synchronized(users) {}
                User user = users.get(userId); 
                Coupon result = user.getCoupon(couponId);
                return result;
    }

    
    /**
     * addAddress method adds a address to the user's address list
     */
    public boolean addAddress(int userId, Address address) throws IOException {
        synchronized(users) {}
                User user = users.get(userId); 
                boolean result = user.addAdress(address);
                user = update(user); 
                return result;    
    }
            

    /**
     * removeAddress method removes a address from the user's address list
    */

    public boolean removeAddress(int userId, int addressId) throws IOException {
        synchronized(users) {}
                User user = users.get(userId); 
                boolean result = user.removeAdress(addressId);
                user = update(user); 
                return result;    
    }

        /**
     * Empty the cart of a user
     * @param userId the id of the user
     * @return true if the cart was emptied
     * @throws IOException
     */
    public boolean emptyCart(int userId) throws IOException {
        synchronized(users) {}
                User user = users.get(userId); 
                boolean result = user.emptyCart();
                user = update(user); 
                return result;    
        }


    /**
     * getAddress method gets a address from the user's address list
    */
    public ArrayList<Address> getAddresses(int userId, int addressId) throws IOException {
        synchronized(users) {}
                User user = users.get(userId); 
                ArrayList<Address> result = user.getAddresses();
                return result;    
    }

    /**
     * get address by id
     */
    public Address getAddress(int userId, int addressId) throws IOException {
        synchronized(users) {}
                User user = users.get(userId); 
                Address result = user.getAddress(addressId);
                return result;
    }


    /*Get the default address of the user*/
    public Address getDefaultAddress(int userId) throws IOException {
        synchronized(users) {}
                User user = users.get(userId); 
                Address result = user.getDefaultAddress();
                return result;
    }


}
    






