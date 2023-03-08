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
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.products.api.productsapi.model.Address;
import com.products.api.productsapi.model.Coupon;
import com.products.api.productsapi.model.Model;
import com.products.api.productsapi.model.Product;
import com.products.api.productsapi.model.User;
import com.products.api.productsapi.model.Order;

/**
 * Test for the UserFileDAO class
 * 
 * @author James De Luca and Antar Narayan Chowdhury
 */
@Tag("Persistence-tier")
public class UserFileDAOTest {
    UserFileDAO testDAO;
    User[] testUsers;
    ObjectMapper testMap;

    /**
     * Before each test, create a new ObjectMapper.
     * 
     * @throws IOException
     */
    @BeforeEach
    public void createTestUFile() throws IOException {
        // Initialize testDAO
        testMap = mock(ObjectMapper.class);
        testUsers = new User[3];
        testUsers[0] = new User(100, "James", "james324", "test_admin", true, new ArrayList<Product>(),
                new ArrayList<Order>(), new ArrayList<Order>(), new ArrayList<Coupon>(), new ArrayList<Address>(),
                false);
        testUsers[1] = new User(101, "Aidan", "aidan551", "test_user", false, new ArrayList<Product>(),
                new ArrayList<Order>(), new ArrayList<Order>(), new ArrayList<Coupon>(), new ArrayList<Address>(),
                false);
        testUsers[2] = new User(102, "Robert", "robert286", "test_admin2", true, new ArrayList<Product>(),
                new ArrayList<Order>(), new ArrayList<Order>(), new ArrayList<Coupon>(), new ArrayList<Address>(),
                false);

        when(testMap.readValue(new File("test.txt"), User[].class))
                .thenReturn(testUsers);

        testDAO = new UserFileDAO("test.txt", testMap);

    }

    /**
     * Test for getAll().
     * 
     */
    @Test
    public void testGetAll() throws IOException {
        // Create user list
        User[] users = testDAO.getAll();

        // Assert
        assertEquals(users.length, testUsers.length);
        for (int i = 0; i < testUsers.length; ++i)
            assertEquals(users[i], testUsers[i]);
    }

    /**
     * Test for search().
     * 
     * @throws IOException
     */
    @Test
    public void testSearch() throws IOException {
        // Create model list
        Model[] models = testDAO.search("aidan");

        // Assert
        assertEquals(models.length, 1);
        assertEquals(models[0], testUsers[1]);
    }

    /**
     * Test for get().
     * 
     * @throws IOException
     */
    @Test
    public void testGet() throws IOException {
        // Get user
        User user = testDAO.get(100);

        // Assert
        assertEquals(user, testUsers[0]);
    }

    /**
     * Test for create().
     * 
     * @throws IOException
     */
    @Test
    public void testCreate() throws IOException {
        // Create new user
        User user = new User(103, "Antar", "antar608", "test_user2", false, new ArrayList<Product>(),
                new ArrayList<Order>(), new ArrayList<Order>(), new ArrayList<Coupon>(), new ArrayList<Address>(),
                false);

        // Test create()
        Model result = assertDoesNotThrow(() -> testDAO.create(user), "Exception thrown");

        // Assert
        assertNotNull(result);
        User actual = testDAO.get(user.getId());
        assertEquals(actual.getId(), user.getId());
        assertEquals(actual.getName(), user.getName());
    }

    /**
     * Test for update().
     * 
     * @throws IOException
     */
    @Test
    public void testUpdate() throws IOException {
        // Create new user
        User user = new User(100, "Richard", "richard394", "test_admin", true, new ArrayList<Product>(),
                new ArrayList<Order>(), new ArrayList<Order>(), new ArrayList<Coupon>(), new ArrayList<Address>(),
                false);

        // Test update()
        Model result = assertDoesNotThrow(() -> testDAO.update(user), "Exception thrown");

        // Assert
        assertNotNull(result);
        User actual = testDAO.get(user.getId());
        assertEquals(actual, user);
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
        assertEquals(testDAO.users.size(), testUsers.length - 1);
    }

    /**
     * Test for an exception thrown from save().
     * 
     * @throws IOException
     */
    @Test
    public void testSaveException() throws IOException {
        doThrow(new IOException()).when(testMap).writeValue(any(File.class), any(User[].class));

        User user = new User(104, "Tom", "tom322", "test_user3", false, new ArrayList<Product>(),
                new ArrayList<Order>(), new ArrayList<Order>(), new ArrayList<Coupon>(), new ArrayList<Address>(),
                false);

        assertThrows(IOException.class, () -> testDAO.create(user), "Exception not thrown");
    }

    /**
     * Test for getting a non-existent user.
     * 
     * @throws IOException
     */
    @Test
    public void testGetUnknownUser() throws IOException {
        // Try to get user
        User user = testDAO.get(99);

        // Assert
        assertEquals(user, null);
    }

    /**
     * Test for deleting a non-existent user.
     */
    @Test
    public void testDeleteUnknownUser() {
        // Create test
        boolean result = assertDoesNotThrow(() -> testDAO.delete(99), "Unexpected exception thrown");

        // Assert
        assertEquals(result, false);
        assertEquals(testDAO.users.size(), testUsers.length);
    }

    /**
     * Test for updating a non-existent user.
     */
    @Test
    public void testUpdateUnknownUser() {
        // Create user
        User user = new User(99, "Bob", "bob996", "test_admin3", true, new ArrayList<Product>(), new ArrayList<Order>(),
                new ArrayList<Order>(), new ArrayList<Coupon>(), new ArrayList<Address>(),
                false);

        // Test update() for unknown user
        Model result = assertDoesNotThrow(() -> testDAO.update(user), "Unexpected exception thrown");

        // Assert
        assertNull(result);
    }

    /**
     * Test for possible exception caused by user constructor.
     * 
     * @throws IOException
     */
    @Test
    public void testConstructorException() throws IOException {
        // Create ObjectMapper
        ObjectMapper testMap = mock(ObjectMapper.class);

        doThrow(new IOException()).when(testMap).readValue(new File("test.txt"), User[].class);

        // Assert
        assertThrows(IOException.class, () -> new UserFileDAO("test.txt", testMap), "Exception not thrown");
    }

    @Test
    public void testaddToCart() {
        // Create user
        User user = new User(100, "Richard", "richard394", "test_admin", true, new ArrayList<Product>(),
                new ArrayList<Order>(), new ArrayList<Order>(), new ArrayList<Coupon>(), new ArrayList<Address>(),
                false);

        // Create product
        Product product = new Product(1, "super food", "super cool food", 10.0f, 3, "");

        // Test addToCart()
        boolean result = assertDoesNotThrow(() -> testDAO.addToCart(user.getId(), product), "Exception thrown");

        // Assert
        assertEquals(result, true);
    }

    @Test
    public void testremoveFromCart() {
        // Create user
        User user = new User(100, "Richard", "richard394", "test_admin", true, new ArrayList<Product>(),
                new ArrayList<Order>(), new ArrayList<Order>(), new ArrayList<Coupon>(), new ArrayList<Address>(),
                false);

        // Create product
        Product product = new Product(1, "super food", "super cool food", 10.0f, 3, "");

        Boolean result = assertDoesNotThrow(() -> testDAO.removeFromCart(user.getId(), product.getId()),
                "Exception thrown");

        assertEquals(result, false);
    }

    @Test
    public void testAddCoupon() {
        // Create user
        User user = new User(100, "Richard", "richard394", "test_admin", true, new ArrayList<Product>(),
                new ArrayList<Order>(), new ArrayList<Order>(), new ArrayList<Coupon>(), new ArrayList<Address>(),
                false);

        // Create coupon
        Coupon coupon = new Coupon(99, "23456", 0.5f, "10/22");

        Boolean result = assertDoesNotThrow(() -> testDAO.addCoupon(user.getId(), coupon), "Exception thrown");

        assertEquals(result, true);
    }

    @Test
    public void testRemoveCoupon() {
        // Create user
        User user = new User(100, "Richard", "richard394", "test_admin", true, new ArrayList<Product>(),
                new ArrayList<Order>(), new ArrayList<Order>(), new ArrayList<Coupon>(), new ArrayList<Address>(),
                false);

        // Create coupon
        Coupon coupon = new Coupon(99, "23456", 0.5f, "10/22");

        Boolean result = assertDoesNotThrow(() -> testDAO.removeCoupon(user.getId(), coupon.getId()),
                "Exception thrown");

        assertEquals(result, false);
    }

    @Test
    public void testApplyCoupon() {
        // Create user
        ArrayList<Product> products = new ArrayList<Product>();
        Product product = new Product(199, "Peppers", "Test peppers", 1.00f, 1, "");
        User user = new User(100, "Richard", "richard394", "test_admin", true, products, new ArrayList<Order>(),
                new ArrayList<Order>(), new ArrayList<Coupon>(), new ArrayList<Address>(),
                false);
        assertDoesNotThrow(() -> testDAO.create(user), "Exception thrown");
        assertDoesNotThrow(() -> testDAO.get(user.getId()), "Exception thrown");
        assertDoesNotThrow(() -> testDAO.addToCart(user.getId(), product), "Exception thrown");
        // Create coupon
        Coupon coupon = new Coupon(99, "23456", 0.1f, "10/22");

        // testDAO.addToCart(user.getId(), product);

        float result = assertDoesNotThrow(() -> testDAO.applyCoupon(user.getId(), coupon), "Exception thrown");
        float expected = 0.9f;

        assertEquals(expected, result);
    }

    @Test
    public void testGetCoupons() {

        ArrayList<Coupon> coupons = new ArrayList<Coupon>();

        // Create coupon
        Coupon coupon = new Coupon(99, "23456", 0.5f, "10/22");

        // Create user
        User user = new User(100, "Richard", "richard394", "test_admin", true, new ArrayList<Product>(),
                new ArrayList<Order>(), new ArrayList<Order>(), coupons, new ArrayList<Address>(),
                false);

        assertDoesNotThrow(() -> testDAO.addCoupon(user.getId(), coupon), "Exception thrown");

        ArrayList<Coupon> result = assertDoesNotThrow(() -> testDAO.getCoupons(user.getId(), 99), "Exception thrown");

        assertEquals(result.size(), 1);
    }

    @Test
    public void testGetCoupon() {

        ArrayList<Coupon> coupons = new ArrayList<Coupon>();

        // Create coupon
        Coupon coupon = new Coupon(99, "23456", 0.5f, "10/22");
        Coupon coupon2 = new Coupon(100, "23456", 0.5f, "10/22");
        // Create user
        User user = new User(100, "Richard", "richard394", "test_admin", true, new ArrayList<Product>(),
                new ArrayList<Order>(), new ArrayList<Order>(), coupons, new ArrayList<Address>(),
                false);

        assertDoesNotThrow(() -> testDAO.addCoupon(user.getId(), coupon), "Exception thrown");
        assertDoesNotThrow(() -> testDAO.addCoupon(user.getId(), coupon2), "Exception thrown");

        Coupon result = assertDoesNotThrow(() -> testDAO.getCoupon(user.getId(), 100), "Exception thrown");

        assertEquals(result.getId(), 100);
    }

    @Test
    public void testAddAddress() {
        User user = new User(100, "Richard", "richard394", "test_admin", true, new ArrayList<Product>(),
                new ArrayList<Order>(), new ArrayList<Order>(), new ArrayList<Coupon>(), new ArrayList<Address>(),
                false);

        Address address = new Address(99, "Robert", "Turtle", "1234", "1234", "1234", 1234, "1234", true);

        Boolean result = assertDoesNotThrow(() -> testDAO.addAddress(user.getId(), address), "Exception thrown");

        assertEquals(result, true);
    }

    @Test
    public void testRemoveAddress() {
        User user = new User(100, "Richard", "richard394", "test_admin", true, new ArrayList<Product>(),
                new ArrayList<Order>(), new ArrayList<Order>(), new ArrayList<Coupon>(), new ArrayList<Address>(),
                false);

        Address address = new Address(99, "Robert", "Turtle", "1234", "1234", "1234", 1234, "1234", true);

        assertDoesNotThrow(() -> testDAO.addAddress(user.getId(), address), "Exception thrown");

        Boolean result = assertDoesNotThrow(() -> testDAO.removeAddress(user.getId(), address.getId()),
                "Exception thrown");

        assertEquals(result, true);
    }

    @Test
    public void testEmptyCart() {
        Product product = new Product(1, "super food", "super cool food", 10.0f, 3, "");
        Product product2 = new Product(2, "super food", "super cool food", 10.0f, 3, "");

        ArrayList<Product> products = new ArrayList<Product>();
        products.add(product);
        products.add(product2);

        User user = new User(100, "Richard", "richard394", "test_admin", true, products, new ArrayList<Order>(),
                new ArrayList<Order>(), new ArrayList<Coupon>(), new ArrayList<Address>(),
                false);

        Boolean result = assertDoesNotThrow(() -> testDAO.emptyCart(user.getId()), "Exception thrown");

        assertEquals(true, result);
    }

    @Test
    public void testGetAddresses() {
        Address address = new Address(99242, "Robert", "Turtle", "1234", "1234", "1234", 1234, "1234", true);
        Address address2 = new Address(100, "Robert1", "Turtle1", "12314", "12134", "12134", 12314, "12314", true);

        ArrayList<Address> addresses = new ArrayList<Address>();
        User user = new User(100, "Richard", "richard394", "test_admin", true, new ArrayList<Product>(),
                new ArrayList<Order>(), new ArrayList<Order>(), new ArrayList<Coupon>(), addresses,
                false);

        assertDoesNotThrow(() -> testDAO.addAddress(user.getId(), address), "Exception thrown");
        assertDoesNotThrow(() -> testDAO.addAddress(user.getId(), address2), "Exception thrown");

        Address result = assertDoesNotThrow(() -> testDAO.getAddress(user.getId(), 100), "Exception thrown");

        assertEquals(result, address2);
    }

    @Test
    public void testGetAddressess() {
        Address address = new Address(99242, "Robert", "Turtle", "1234", "1234", "1234", 1234, "1234", true);
        Address address2 = new Address(100, "Robert1", "Turtle1", "12314", "12134", "12134", 12314, "12314", true);

        ArrayList<Address> addresses = new ArrayList<Address>();
        User user = new User(100, "Richard", "richard394", "test_admin", true, new ArrayList<Product>(),
                new ArrayList<Order>(), new ArrayList<Order>(), new ArrayList<Coupon>(), addresses,
                false);

        assertDoesNotThrow(() -> testDAO.addAddress(user.getId(), address), "Exception thrown");
        assertDoesNotThrow(() -> testDAO.addAddress(user.getId(), address2), "Exception thrown");
        addresses.add(address);
        addresses.add(address2);
        ArrayList<Address> result = assertDoesNotThrow(() -> testDAO.getAddresses(user.getId(), address.getId()),
                "Exception thrown");

        assertEquals(result, addresses);
    }

    @Test
    public void testGetDefaultAddress() {
        ArrayList<Address> addresses = new ArrayList<Address>();
        User user = new User(100, "Richard", "richard394", "test_admin", true, new ArrayList<Product>(),
                new ArrayList<Order>(), new ArrayList<Order>(), new ArrayList<Coupon>(), addresses,
                false);

        Address address = new Address(99, "Robert", "Turtle", "1234", "1234", "1234", 1234, "1234", true);
        Address address2 = new Address(100, "Robert", "Turtle", "1234", "1234", "1234", 1234, "1234", false);

        assertDoesNotThrow(() -> testDAO.addAddress(user.getId(), address2), "Exception thrown");
        assertDoesNotThrow(() -> testDAO.addAddress(user.getId(), address), "Exception thrown");

        Address result = assertDoesNotThrow(() -> testDAO.getDefaultAddress(user.getId()), "Exception thrown");

        assertEquals(result.getId(), 99);
    }

}