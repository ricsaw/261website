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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Optional;

import com.products.api.productsapi.persistence.UserFileDAO;
import com.products.api.productsapi.model.User;
import com.products.api.productsapi.model.Address;
import com.products.api.productsapi.model.Coupon;
import com.products.api.productsapi.model.Product;


/**
 * Handles the REST API requests for users
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author Aidan and Antar 
 */
@RestController
@RequestMapping("users")
public class UserController implements Controller<User> {
    private static final Logger LOG = Logger.getLogger(User.class.getName());
    private UserFileDAO userFileDAO;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param userFileDAO The {@link UserFileDAO User Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public UserController(UserFileDAO userFileDAO) {
        this.userFileDAO = userFileDAO; 
    }

    /**
     * Responds to the GET request for a {@linkplain User user} for the given id
     * 
     * @param id The id used to locate the {@link User user}
     * 
     * @return ResponseEntity with {@link User user} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable int id) {
        LOG.info("GET /user/" + id);
        try {
            User user = userFileDAO.get(id);
            if (user != null)
                return new ResponseEntity<User>(user,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain User users}
     * 
     * @return ResponseEntity with array of {@link User user} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<User[]> getAll() {
        LOG.info("GET /users");

        // Replace below with your implementation
        try
        {
            User[] users = (User[]) userFileDAO.getAll();
            return new ResponseEntity<User[]>(users, HttpStatus.OK);
        }
        catch(IOException e)
        {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain User users} whose name contains
     * the text in username
     * 
     * @param text The parameter which contains the text used to find the {@link User user}
     * 
     * @return ResponseEntity with array of {@link User user} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * <p>
     * Example: Find all users that contain the text "ma"
     * GET http://localhost:8080/users/?username=ma
     */
    @GetMapping("/search")
    public ResponseEntity<User[]> search(@RequestParam String text) {
        LOG.info("GET /users/search/?username="+text);

        // Replace below with your implementation
        try
        {
            User[] users = (User[]) userFileDAO.search(text);
            return new ResponseEntity<User[]>(users, HttpStatus.OK);
        }
        catch(IOException e)
        {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain User user} with the provided user object
     * 
     * @param model - The {@link User user} to create
     * 
     * @return ResponseEntity with created {@link User user} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link User user} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("/add")
    public ResponseEntity<User> create(@RequestBody User user) {
        LOG.info("POST /users/add " + user);
        
        try
        {
            if (!(user instanceof User)) {       // Ensure model is an instance of Product
                throw new IOException("Not instance of User");
            }
            //User ModelUser = (User) user; 
            //String userName = ModelUser.getUsername();

            // User[] temp = userFileDAO.search(userName);          
            // System.out.println(userFileDAO.search(userName));
            // if (temp.length != 0) {    // Throws IOException if user with the same username already exists
            //     throw new IOException("Username taken");
            // }else{
                
            User new_user = (User) userFileDAO.create(user);
            if(new_user != null){
                return new ResponseEntity<User>(new_user, HttpStatus.CREATED);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } 
        }
        catch(IOException e)
        {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a product from the user's cart with the given id.
     * 
     * @param id The id of the {@link User user} to be deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{user_id}/cart/delete/{id}")
    public ResponseEntity<User> removeFromCart(@PathVariable int user_id, @PathVariable int id) {
        LOG.info("DELETE /" + user_id + "/cart/delete/" + id);

        try
        {
            if(userFileDAO.removeFromCart(user_id, id))
            {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e)
        {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain User user} with the provided {@linkplain User user} object, if it exists
     * 
     * @param model The {@link User user} to update
     * 
     * @return ResponseEntity with updated {@link User user} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */

    @PutMapping("/edit")
    public ResponseEntity<User> update(@RequestBody User user) {
        LOG.info("PUT /users/edit " + user);

        
        try
        {
            // if (!(user instanceof User)) {       // Ensure model is an instance of Product
            //     throw new IOException("Not instance of Product");
            // }

            //int id = user.getId();
            //User new_user = userFileDAO.get(id);
            User new_user = (User)userFileDAO.update(user);

            if(new_user != null)
            {
                return new ResponseEntity<User>(new_user, HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e)
        {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain ArrayList<User> users} with the provided 
     * {@linkplain ArrayList<User> users} objects, if they exist
     * 
     * @param users The {@link ArrayList<User> users} to update
     * 
     * @return ResponseEntity with updated {@link ArrayList<ResponseEntity<User>> responses} objects
     *         and HTTP status of OK if updated<br>\
     */
    @PutMapping("/editMultiple")
    public ResponseEntity<Boolean> updateMultiple(@RequestBody ArrayList<User> users) {
        for (User user : users) {
            this.update(user);
        }
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @PutMapping("/{id}/changeAdminStatus")
    public ResponseEntity<Boolean> changeAdminStatus(@PathVariable int id, @RequestBody Optional<User> userSpecified) {
        LOG.info("PUT /users/" + id + "/changeAdminStatus");
        try
        {
            User user = userFileDAO.get(id);
            user.setAdmin(!user.getAdmin());
            userFileDAO.update(user);
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        catch(IOException e)
        {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain User user} with the given id
     * 
     * @param id The id of the {@link User user} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<User> delete(@PathVariable int id) {
        LOG.info("DELETE /users/delete/" + id);

        // Replace below with your implementation
        try
        {
            if(userFileDAO.delete(id))
            {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e)
        {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain User user} with the provided {@linkplain User user} object, if it exists
     * 
     * @param model The {@link User user} to update
     * 
     * @return ResponseEntity with updated {@link User user} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */

    @PutMapping("{userId}/cart/add")
    public ResponseEntity<User> addToCart(@PathVariable int userId, @RequestBody Product product) {
        LOG.info("PUT /users/" + userId + "/cart/add " + product);
        try
        {
            /*
            if (!(user instanceof User)) {       // Ensure model is an instance of Product
                throw new IOException("Not instance of Product");
            }*/
            userFileDAO.addToCart(userId, product);

            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(IOException e)
        {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{userId}/cart/empty")
    public ResponseEntity<User> emptyCart(@PathVariable int userId) {
        LOG.info("DELETE /users/" + userId + "/cart/empty");
        try
        {
            userFileDAO.emptyCart(userId);
            User temp = userFileDAO.get(userId);
            LOG.info("User: " + temp);
            return new ResponseEntity<User>(temp, HttpStatus.OK);
        }
        catch(IOException e)
        {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //     /**
    //  * Updates the {@linkplain User user} with the provided {@linkplain User user} object, if it exists
    //  * 
    //  * @param model The {@link User user} to update
    //  * 
    //  * @return ResponseEntity with updated {@link User user} object and HTTP status of OK if updated<br>
    //  * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
    //  * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
    //  */

    // @PutMapping("{userId}/cart/total")
    // public ResponseEntity<Float> getTotolOfCart(@PathVariable int userId, @RequestBody Product product) {
    //     LOG.info("GET /users/" + userId + "/cart/total");
    //     try
    //     {
    //         User currentUser = userFileDAO.get(userId);
    //         float TotalOfCart = (float) currentUser.getTotal();
    //         return new ResponseEntity<Float>( TotalOfCart, HttpStatus.OK);
    //     }
    //     catch(IOException e)
    //     {
    //         LOG.log(Level.SEVERE, e.getLocalizedMessage());
    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }


    /**
     * get coupon from the user
     */
    @GetMapping("{userId}/getCoupon")
    public ResponseEntity<Coupon> getCoupon(@PathVariable int userId) {
        LOG.info("GET /users/" + userId + "/getCoupon");
        try
        {
            User currentUser = userFileDAO.get(userId);
            ArrayList<Coupon> coupon = currentUser.getCoupons();
            return new ResponseEntity<Coupon>( coupon.get(0), HttpStatus.OK);
        }
        catch(IOException e)
        {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * add coupon to the user
     */
    @PutMapping("{userId}/addCoupon")
    public ResponseEntity<User> addCoupon(@PathVariable int userId, @RequestBody Coupon coupon) {
        LOG.info("PUT /users/" + userId + "/addCoupon");
        try
        {
            User currentUser = userFileDAO.get(userId);
            currentUser.addCoupon(coupon);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(IOException e)
        {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    /**
     * get address from the user ---------------------------------------------------
     */
    @GetMapping("{userId}/getAddress")
    public ResponseEntity<Address> getAddress(@PathVariable int userId) {
        LOG.info("GET /users/" + userId + "/getAddress");
        try
        {
            User currentUser = userFileDAO.get(userId);
            ArrayList<Address> address = currentUser.getAddresses();
            if(address.size() == 0){
           address.add( new Address(0, "name", "lastname", "address", "city", "state", 00000, "country", true));
            }
            return new ResponseEntity<Address>( address.get(address.size()-1), HttpStatus.OK);
        
        }
        catch(IOException e)
        {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * add address from the user ---------------------------------------------------
     */
    @PostMapping("{userId}/addAddress")
    public ResponseEntity<Address> addAddress(@PathVariable int userId, @RequestBody Address address) {
        LOG.info("POST /users/" + userId + "/addAddress" + address);
        try
        {
            User currentUser = userFileDAO.get(userId);
            currentUser.addAddress(address);
            userFileDAO.update(currentUser);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(IOException e)
        {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    





}
