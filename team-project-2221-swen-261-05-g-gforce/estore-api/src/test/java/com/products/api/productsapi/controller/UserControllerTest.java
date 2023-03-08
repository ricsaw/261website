package com.products.api.productsapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;

import com.products.api.productsapi.persistence.UserFileDAO;

import com.products.api.productsapi.model.Address;
import com.products.api.productsapi.model.Coupon;

import com.products.api.productsapi.model.Order;
import com.products.api.productsapi.model.Product;
import com.products.api.productsapi.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the User Controller class
 * 
 * @author Antar Narayan Chowdhury
 * @author Aidan
 * @author Robert Tetreault
 */
@Tag("Controller-tier")
public class UserControllerTest {
    private UserController userController;
    private UserFileDAO mockDAO;

    /**
     * Before each test, create a new UserController object and inject
     * a mock User DAO
     */
    @BeforeEach
    public void setupUserController() {
        mockDAO = mock(UserFileDAO.class);
        userController = new UserController(mockDAO);
    }

    @Test
    public void testGetUser() throws IOException { // get may throw IOException
        // Setup
        User user = new User(99, "super user", "superUser", "password", true, new ArrayList<Product>(),
                new ArrayList<Order>(), new ArrayList<Order>(), new ArrayList<Coupon>(), new ArrayList<Address>(),
                false);
        when(mockDAO.get(99)).thenReturn(user);
        when(mockDAO.get(99)).thenReturn(user);

        // When the same id is passed in, our mock User DAO will return the User object
        when(mockDAO.get(user.getId())).thenReturn(user);

        // Invoke
        ResponseEntity<User> response = userController.get(user.getId());

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testGetUserNotFound() throws Exception { // create may throw IOException
        // Setup
        int userId = 99;
        // When the same id is passed in, our mock User DAO will return null, simulating
        // no User found
        when(mockDAO.get(userId)).thenReturn(null);

        // Invoke
        ResponseEntity<User> response = userController.get(userId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetUserHandleException() throws Exception { // create may throw IOException
        // Setup
        int userId = 99;
        // When get is called on the Mock User DAO, throw an IOException
        doThrow(new IOException()).when(mockDAO).get(userId);

        // Invoke
        ResponseEntity<User> response = userController.get(userId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /*****************************************************************
     * The following tests will fail until all UserController methods
     * are implemented.
     ****************************************************************/

    @Test
    public void testCreateUser() throws IOException { // create may throw IOException
        // Setup
        User user = new User(99, "Coolest user", "coolDude", "password", true, new ArrayList<Product>(),
                new ArrayList<Order>(), new ArrayList<Order>(), new ArrayList<Coupon>(), new ArrayList<Address>(),
                false);

        // when create is called, return true simulating successful
        // creation and save
        when(mockDAO.create(user)).thenReturn(user);

        // Invoke
        ResponseEntity<User> response = userController.create(user);

        // Analyze
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testCreateUserFailed() throws IOException { // create may throw IOException
        // Setup
        User user = new User(99, "bad user", "badUser", "password", true, new ArrayList<Product>(),
                new ArrayList<Order>(), new ArrayList<Order>(), new ArrayList<Coupon>(), new ArrayList<Address>(),
                false);
        // when create is called, return false simulating failed
        // creation and save
        when(mockDAO.create(user)).thenReturn(null);

        // Invoke
        ResponseEntity<User> response = userController.create(user);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /*
     * @Test
     * public void testCreateInvalidUser() throws IOException { // create may throw
     * IOException
     * // Setup
     * Product product = new Product(99,"expired food", "a week old", 10.0f, 3, "");
     * // Invoke
     * ResponseEntity<Model> response = userController.create(product);
     * // Analyze
     * assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
     * }
     */

    @Test
    public void testCreateUserHandleException() throws IOException { // create may throw IOException
        // Setup
        User user = new User(99, "old user", "oldUser", "password", true, new ArrayList<Product>(),
                new ArrayList<Order>(), new ArrayList<Order>(), new ArrayList<Coupon>(), new ArrayList<Address>(),
                false);

        // When create is called on the Mock User DAO, throw an IOException
        doThrow(new IOException()).when(mockDAO).create(user);

        // Invoke
        ResponseEntity<User> response = userController.create(user);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testUpdateUser() throws IOException { // create may throw IOException
        // Setup
        User user = new User(99, "super user", "superUser", "password", true, new ArrayList<Product>(),
                new ArrayList<Order>(), new ArrayList<Order>(), new ArrayList<Coupon>(), new ArrayList<Address>(),
                false);
        // when create is called, return true simulating successful
        // creation and save
        when(mockDAO.update(user)).thenReturn(user);
        when(mockDAO.get(user.getId())).thenReturn(user);
        // Invoke
        ResponseEntity<User> response = userController.update(user);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    /*
     * @Test
     * public void testUpdateInvalidUser() throws IOException { // create may throw
     * IOException
     * // Setup
     * Product product = new Product(99,"expired food", "a week old", 10.0f, 3, "");
     * // Invoke
     * ResponseEntity<User> response = userController.update(user);
     * // Analyze
     * assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
     * }
     */

    @Test
    public void testUpdateUserFailed() throws IOException { // update may throw IOException
        // Setup
        User user = new User(99, "super user", "superUser", "password", true, new ArrayList<Product>(),
                new ArrayList<Order>(), new ArrayList<Order>(), new ArrayList<Coupon>(), new ArrayList<Address>(),
                false);
        // when update is called, return true simulating successful
        // update and save
        when(mockDAO.update(user)).thenReturn(null);

        // Invoke
        ResponseEntity<User> response = userController.update(user);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateUserHandleException() throws IOException { // update may throw IOException
        // Setup
        User user = new User(99, "Health Guru", "eggplantMan", "password", true, new ArrayList<Product>(),
                new ArrayList<Order>(), new ArrayList<Order>(), new ArrayList<Coupon>(), new ArrayList<Address>(),
                false);

        // When update is called on the Mock Product DAO, throw an IOException
        doThrow(new IOException()).when(mockDAO).update(user);

        // Invoke
        ResponseEntity<User> response = userController.update(user);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetUsers() throws IOException { // getAll may throw IOException
        // Setup
        User[] users = new User[2];
        users[0] = new User(99, "super user", "superUser", "password", true, new ArrayList<Product>(),
                new ArrayList<Order>(), new ArrayList<Order>(), new ArrayList<Coupon>(), new ArrayList<Address>(),
                false);
        users[1] = new User(100, "lame user", "Luser", "password", true, new ArrayList<Product>(),
                new ArrayList<Order>(), new ArrayList<Order>(), new ArrayList<Coupon>(), new ArrayList<Address>(),
                false);

        // When getAll is called return the users created above
        when(mockDAO.getAll()).thenReturn(users);

        // Invoke
        ResponseEntity<User[]> response = userController.getAll();

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    public void testGetusersHandleException() throws IOException { // getAll may throw IOException
        // Setup
        // When getAll is called on the Mock User DAO, throw an IOException
        doThrow(new IOException()).when(mockDAO).getAll();

        // Invoke
        ResponseEntity<User[]> response = userController.getAll();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testSearchUsers() throws IOException { // search may throw IOException
        // Setup
        String searchString = "la";
        User[] users = new User[2];
        users[0] = new User(99, "super user", "superUser", "password", true, new ArrayList<Product>(),
                new ArrayList<Order>(), new ArrayList<Order>(), new ArrayList<Coupon>(), new ArrayList<Address>(),
                false);
        users[1] = new User(100, "lame user", "Luser", "password", true, new ArrayList<Product>(),
                new ArrayList<Order>(), new ArrayList<Order>(), new ArrayList<Coupon>(), new ArrayList<Address>(),
                false);

        // When search is called with the search string, return the two
        /// users above
        when(mockDAO.search(searchString)).thenReturn(users);

        // Invoke
        ResponseEntity<User[]> response = userController.search(searchString);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    public void testSearchUsersHandleException() throws IOException { // search may throw IOException
        // Setup
        String searchString = "an";
        // When create is called on the Mock User DAO, throw an IOException
        doThrow(new IOException()).when(mockDAO).search(searchString);

        // Invoke
        ResponseEntity<User[]> response = userController.search(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteUser() throws IOException { // delete may throw IOException
        // Setup
        int userId = 99;
        // when delete is called return true, simulating successful deletion
        when(mockDAO.delete(userId)).thenReturn(true);

        // Invoke
        ResponseEntity<User> response = userController.delete(userId);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteUserNotFound() throws IOException { // delete may throw IOException
        // Setup
        int userId = 99;
        // when delete is called return false, simulating failed deletion
        when(mockDAO.delete(userId)).thenReturn(false);

        // Invoke
        ResponseEntity<User> response = userController.delete(userId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteUserHandleException() throws IOException { // delete may throw IOException
        // Setup
        int userId = 99;
        // When delete is called on the Mock User DAO, throw an IOException
        doThrow(new IOException()).when(mockDAO).delete(userId);

        // Invoke
        ResponseEntity<User> response = userController.delete(userId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testAddToCart() throws IOException { // delete may throw IOException
        // Setup
        int userId = 99;
        Product product = new Product(99, "super food", "super cool food", 10.0f, 3, "");
        // When delete is called on the Mock User DAO, throw an IOException
        when(mockDAO.addToCart(userId, product)).thenReturn(true);

        // Invoke
        ResponseEntity<User> response = userController.addToCart(userId, product);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testAddToCartFail() throws IOException { // delete may throw IOException
        // Setup
        int userId = 99;
        Product product = new Product(99, "super food", "super cool food", 10.0f, 3, "");
        // When delete is called on the Mock User DAO, throw an IOException
        doThrow(new IOException()).when(mockDAO).addToCart(userId, product);

        // Invoke
        ResponseEntity<User> response = userController.addToCart(userId, product);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testchangeAdminStatus() throws IOException { // delete may throw IOException
        // Setup
        User user = new User(99, "super user", "superUser", "password", false, new ArrayList<Product>(),
                new ArrayList<Order>(), new ArrayList<Order>(), new ArrayList<Coupon>(), new ArrayList<Address>(),
                false);
        boolean adminStatus = true;
        when(mockDAO.get(user.getId())).thenReturn(user);
        user.setAdmin(adminStatus);
        when(mockDAO.update(user)).thenReturn(user);
        // Invoke
        ResponseEntity<Boolean> response = userController.changeAdminStatus(user.getId(), null);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testchangeAdminStatusFail() throws IOException { // delete may throw IOException
        // Setup
        User user = new User(99, "super user", "superUser", "password", false, new ArrayList<Product>(),
                new ArrayList<Order>(), new ArrayList<Order>(), new ArrayList<Coupon>(), new ArrayList<Address>(),
                false);
        boolean adminStatus = true;
        when(mockDAO.get(user.getId())).thenReturn(user);
        user.setAdmin(adminStatus);
        // When delete is called on the Mock User DAO, throw an IOException
        doThrow(new IOException()).when(mockDAO).update(user);

        // Invoke
        ResponseEntity<Boolean> response = userController.changeAdminStatus(user.getId(), null);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testremoveFromCart() throws IOException { // delete may throw IOException
        // Setup
        int userId = 99;
        int productId = 99;
        // When delete is called on the Mock User DAO, throw an IOException
        when(mockDAO.removeFromCart(userId, productId)).thenReturn(true);

        // Invoke
        ResponseEntity<User> response = userController.removeFromCart(userId, productId);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testremoveFromCartFail() throws IOException { // delete may throw IOException
        // Setup
        int userId = 99;
        int productId = 99;
        // When delete is called on the Mock User DAO, throw an IOException
        when(mockDAO.removeFromCart(userId, productId)).thenReturn(false);

        // Invoke
        ResponseEntity<User> response = userController.removeFromCart(userId, productId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testremoveFromCartHandle() throws IOException { // delete may throw IOException
        // Setup
        int userId = 99;
        int productId = 99;
        // When delete is called on the Mock User DAO, throw an IOException
        doThrow(new IOException()).when(mockDAO).removeFromCart(userId, productId);

        // Invoke
        ResponseEntity<User> response = userController.removeFromCart(userId, productId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testUpdateMultiple() throws IOException { // create may throw IOException
        // Setup
        ArrayList<User> users = new ArrayList<User>();

        users.add(new User(99, "super user", "superUser", "password", true, new ArrayList<Product>(),
                new ArrayList<Order>(), new ArrayList<Order>(), new ArrayList<Coupon>(), new ArrayList<Address>(),
                false));
        users.add(new User(100, "lame user", "Luser", "password", true, new ArrayList<Product>(),
                new ArrayList<Order>(), new ArrayList<Order>(), new ArrayList<Coupon>(), new ArrayList<Address>(),
                false));

        // When create is called on the Mock User DAO, return the first user
        for (User user : users) {
            when(mockDAO.update(user)).thenReturn(user);
        }

        // Invoke
        ResponseEntity<Boolean> response = userController.updateMultiple(users);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetAddress() throws IOException { // delete may throw IOException
        // Setup
        ArrayList<Address> addressList = new ArrayList<Address>();
        Address address = new Address(99, "Antar", "Chowdhury", "123 Main St", "Anytown", "NY", 12345, "12345", false);
        addressList.add(address);
        User user = new User(99, "super user", "superUser", "password", false, new ArrayList<Product>(),
                new ArrayList<Order>(), new ArrayList<Order>(), new ArrayList<Coupon>(), addressList, false);
        when(mockDAO.create(user)).thenReturn(user);
        when(mockDAO.get(user.getId())).thenReturn(user);
        when(mockDAO.getAddress(user.getId(), address.getId())).thenReturn(address);
        // Invoke
        ResponseEntity<Address> response = userController.getAddress(user.getId());

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetAddressFail() throws IOException { // delete may throw IOException
        // Setup
        ArrayList<Address> addressList = new ArrayList<Address>();
        Address address = new Address(99, "Antar", "Chowdhury", "123 Main St", "Anytown", "NY", 12345, "12345", false);
        addressList.add(address);
        User user = new User(99, "super user", "superUser", "password", false, new ArrayList<Product>(),
                new ArrayList<Order>(), new ArrayList<Order>(), new ArrayList<Coupon>(), addressList, false);
        when(mockDAO.create(user)).thenReturn(user);
        when(mockDAO.get(user.getId())).thenReturn(user);
        when(mockDAO.getAddress(user.getId(), address.getId())).thenReturn(null);
        when(mockDAO.create(user)).thenReturn(user);
        doThrow(new IOException()).when(mockDAO).get(user.getId());
        // Invoke
        ResponseEntity<Address> response = userController.getAddress(user.getId());

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetCoupon() throws IOException { // delete may throw IOException
        // Setup
        ArrayList<Coupon> couponList = new ArrayList<Coupon>();
        Coupon coupon = new Coupon(8, "987654", 0.50f, "2020-12-31");

        couponList.add(coupon);
        User user = new User(99, "super user", "superUser", "password", false, new ArrayList<Product>(),
                new ArrayList<Order>(), new ArrayList<Order>(), couponList, new ArrayList<Address>(), false);
        when(mockDAO.create(user)).thenReturn(user);
        when(mockDAO.get(user.getId())).thenReturn(user);
        when(mockDAO.getCoupon(user.getId(), coupon.getId())).thenReturn(coupon);
        // Invoke
        ResponseEntity<Coupon> response = userController.getCoupon(user.getId());

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetCouponFail() throws IOException { // delete may throw IOException
        // Setup
        ArrayList<Coupon> couponList = new ArrayList<Coupon>();
        Coupon coupon = new Coupon(8, "987654", 0.50f, "2020-12-31");
        couponList.add(coupon);
        User user = new User(99, "super user", "superUser", "password",
                false, new ArrayList<Product>(), new ArrayList<Order>(), new ArrayList<Order>(), couponList,
                new ArrayList<Address>(),
                false);
        when(mockDAO.create(user)).thenReturn(user);
        doThrow(new IOException()).when(mockDAO).get(user.getId());
        // Invoke
        ResponseEntity<Coupon> response = userController.getCoupon(user.getId());

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void emptyCartTest() throws IOException { // delete may throw IOException
        // Setup
        ArrayList<Product> productList = new ArrayList<Product>();
        Product product = new Product(99, "Raddest Food", "super cool-est food", 10.0f, 3, "");

        productList.add(product);
        User user = new User(99, "super user", "superUser", "password",
                false, productList, new ArrayList<Order>(), new ArrayList<Order>(), new ArrayList<Coupon>(),
                new ArrayList<Address>(),
                false);
        when(mockDAO.create(user)).thenReturn(user);
        when(mockDAO.get(user.getId())).thenReturn(user);
        when(mockDAO.emptyCart(user.getId())).thenReturn(true);
        // Invoke
        ResponseEntity<User> response = userController.emptyCart(user.getId());

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void emptyCartFail() throws IOException { // delete may throw IOException
        // Setup
        User user = new User(99, "super user", "superUser", "password",
                false, new ArrayList<Product>(), new ArrayList<Order>(), new ArrayList<Order>(),
                new ArrayList<Coupon>(), new ArrayList<Address>(),
                false);
        when(mockDAO.create(user)).thenReturn(user);
        doThrow(new IOException()).when(mockDAO).emptyCart(user.getId());
        // Invoke
        ResponseEntity<User> response = userController.emptyCart(user.getId());

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void addAddressTest() throws IOException { // delete may throw IOException
        // Setup
        ArrayList<Address> addressList = new ArrayList<Address>();
        Address address = new Address(99, "Antar", "Chowdhury", "123 Main St", "Anytown", "NY", 12345, "12345", false);
        addressList.add(address);
        Address address_new = new Address(100, "Ante2ar", "Chowd23hury", "123232 Main St", "Anytw2own", "N23Y", 12445,
                "13345", false);
        User user = new User(99, "super user", "superUser", "password", false, new ArrayList<Product>(),
                new ArrayList<Order>(), new ArrayList<Order>(), new ArrayList<Coupon>(), addressList,
                false);
        when(mockDAO.create(user)).thenReturn(user);
        user.addAddress(address_new);
        when(mockDAO.update(user)).thenReturn(user);
        when(mockDAO.get(user.getId())).thenReturn(user);
        when(mockDAO.addAddress(user.getId(), address_new)).thenReturn(true);
        // Invoke
        ResponseEntity<Address> response = userController.addAddress(user.getId(), address_new);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void addAddressFail() throws IOException { // delete may throw IOException
        // Setup
        // Setup
        ArrayList<Address> addressList = new ArrayList<Address>();
        Address address = new Address(99, "Antar", "Chowdhury", "123 Main St", "Anytown", "NY", 12345, "12345", false);
        addressList.add(address);
        Address address_new = new Address(100, "Ante2ar", "Chowd23hury", "123232 Main St", "Anytw2own", "N23Y", 12445,
                "13345", false);
        User user = new User(99, "super user", "superUser", "password", false, new ArrayList<Product>(),
                new ArrayList<Order>(), new ArrayList<Order>(), new ArrayList<Coupon>(), addressList,
                false);
        when(mockDAO.create(user)).thenReturn(user);
        when(mockDAO.update(user)).thenReturn(user);
        when(mockDAO.get(user.getId())).thenReturn(user);
        doThrow(new IOException()).when(mockDAO).get(user.getId());
        // doThrow(new IOException()).when(mockDAO).addAddress(user.getId(),
        // address_new);
        // Invoke
        ResponseEntity<Address> response = userController.addAddress(user.getId(), address_new);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void addCouponTest() throws IOException { // delete may throw IOException
        // Setup
        ArrayList<Coupon> couponList = new ArrayList<Coupon>();
        Coupon coupon = new Coupon(8, "987654", 0.50f, "2020-12-31");
        couponList.add(coupon);
        Coupon coupon_new = new Coupon(8, "987654", 0.50f, "2020-12-31");
        User user = new User(99, "super user", "superUser", "password", false, new ArrayList<Product>(),
                new ArrayList<Order>(), new ArrayList<Order>(), couponList, new ArrayList<Address>(),
                false);
        when(mockDAO.create(user)).thenReturn(user);
        user.addCoupon(coupon_new);
        when(mockDAO.update(user)).thenReturn(user);
        when(mockDAO.get(user.getId())).thenReturn(user);
        when(mockDAO.addCoupon(user.getId(), coupon_new)).thenReturn(true);
        // Invoke
        ResponseEntity<User> response = userController.addCoupon(user.getId(), coupon_new);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void addCouponFail() throws IOException { // delete may throw IOException
        // Setup
        User user = new User(99, "super user", "superUser", "password", false, new ArrayList<Product>(),
                new ArrayList<Order>(), new ArrayList<Order>(), new ArrayList<Coupon>(), new ArrayList<Address>(),
                false);
        when(mockDAO.create(user)).thenReturn(user);
        when(mockDAO.update(user)).thenReturn(user);
        when(mockDAO.get(user.getId())).thenReturn(user);
        doThrow(new IOException()).when(mockDAO).get(user.getId());
        // doThrow(new IOException()).when(mockDAO).get(user.getId());
        // doThrow(new IOException()).when(mockDAO).addCoupon(0, null);
        // Invoke
        ResponseEntity<User> response = userController.addCoupon(user.getId(), null);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

}