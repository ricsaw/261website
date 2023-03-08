package com.products.api.productsapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


import java.util.ArrayList;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test for the User class
 * 
 * @author Aidan Collins and Antar Narayan Chowdhury
 */
@Tag("Model-tier")
public class UserTest {

    // Package private for tests
    static final String STRING_FORMAT = "User [ id=%d, name=%s, username=%s, password=%s, isadmin=%s, cart=%s, currentOrders=%s, previousOrders=%s, coupons=%s, address=%s, darkMode=%s]";

    @Test
    public void testCtor() {
        // Setup
        int id = 20;
        String name = "name";
        String username = "username";
        String password = "password";
        boolean isAdmin = true;
        boolean darkMode = false;
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Order> currentOrders = new ArrayList<Order>();
        ArrayList<Order> previousOrders = new ArrayList<Order>();
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        ArrayList<Address> address = new ArrayList<Address>();

        // Invoke
        User user = new User(id, name, username, password, isAdmin, cart, currentOrders, previousOrders, coupons,
                address, darkMode);

        // Analyze
        assertEquals(id, user.getId());
        assertEquals(name, user.getName());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(isAdmin, user.getAdmin());
        assertEquals(cart, user.getCart());
        assertEquals(currentOrders, user.getCurrentOrders());
        assertEquals(previousOrders, user.getPreviousOrders());
        assertEquals(coupons, user.getCoupons());
        assertEquals(address, user.getAddresses());
    }

    @Test
    public void testName() {
        // Setup
        int id = 20;
        String name = "name";
        String username = "username";
        String password = "password";
        boolean isAdmin = true;
        boolean darkMode = false;
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Order> currentOrders = new ArrayList<Order>();
        ArrayList<Order> previousOrders = new ArrayList<Order>();
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        ArrayList<Address> address = new ArrayList<Address>();

        // Invoke
        User user = new User(id, name, username, password, isAdmin, cart, currentOrders, previousOrders, coupons,
                address, darkMode);

        String expected_name = "Bob";

        // Invoke
        user.setName(expected_name);

        // Analyze
        assertEquals(expected_name, user.getName());
    }

    @Test
    public void testId() {
        // Setup
        int id = 20;
        String name = "name";
        String username = "username";
        String password = "password";
        boolean isAdmin = true;
        boolean darkMode = false;
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Order> currentOrders = new ArrayList<Order>();
        ArrayList<Order> previousOrders = new ArrayList<Order>();
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        ArrayList<Address> address = new ArrayList<Address>();
        User user = new User(id, name, username, password, isAdmin, cart, currentOrders, previousOrders, coupons,
                address, darkMode);

        int expected_id = 99;

        // Invoke
        user.setId(expected_id);

        // Analyze
        assertEquals(expected_id, user.getId());
    }

    @Test
    public void testUsername() {
        // Setup
        int id = 20;
        String name = "name";
        String username = "username";
        String password = "password";
        boolean isAdmin = true;
        boolean darkMode = false;
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Order> currentOrders = new ArrayList<Order>();
        ArrayList<Order> previousOrders = new ArrayList<Order>();
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        ArrayList<Address> address = new ArrayList<Address>();

        // Invoke
        User user = new User(id, name, username, password, isAdmin, cart, currentOrders, previousOrders, coupons,
                address, darkMode);
        String expected_username = "Bob1234";

        // Invoke
        user.setUsername(expected_username);

        // Analyze
        assertEquals(expected_username, user.getUsername());
    }

    @Test
    public void testPrice() {
        // Setup
        int id = 20;
        String name = "name";
        String username = "username";
        String password = "password";
        boolean isAdmin = true;
        boolean darkMode = false;
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Order> currentOrders = new ArrayList<Order>();
        ArrayList<Order> previousOrders = new ArrayList<Order>();
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        ArrayList<Address> address = new ArrayList<Address>();

        // Invoke
        User user = new User(id, name, username, password, isAdmin, cart, currentOrders, previousOrders, coupons,
                address, darkMode);

        String expected_password = "Bobs_password";

        // Invoke
        user.setPassword(expected_password);

        // Analyze
        assertEquals(expected_password, user.getPassword());
    }

    @Test
    public void testAdmin() {
        // Setup
        int id = 20;
        String name = "name";
        String username = "username";
        String password = "password";
        boolean isAdmin = true;
        boolean darkMode = false;
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Order> currentOrders = new ArrayList<Order>();
        ArrayList<Order> previousOrders = new ArrayList<Order>();
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        ArrayList<Address> address = new ArrayList<Address>();

        // Invoke
        User user = new User(id, name, username, password, isAdmin, cart, currentOrders, previousOrders, coupons,
                address, darkMode);

        boolean expected_admin = false;

        // Invoke
        user.setAdmin(expected_admin);

        // Analyze
        assertEquals(expected_admin, user.getAdmin());
    }

    @Test
    public void testAddToCart() {
        // Setup
        int id = 20;
        String name = "name";
        String username = "username";
        String password = "password";
        boolean isAdmin = true;
        boolean darkMode = false;
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Order> currentOrders = new ArrayList<Order>();
        ArrayList<Order> previousOrders = new ArrayList<Order>();
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        ArrayList<Address> address = new ArrayList<Address>();

        // Invoke
        User user = new User(id, name, username, password, isAdmin, cart, currentOrders, previousOrders, coupons,
                address, darkMode);
        Product product = new Product(0, "test_product", "description",
                1, 1, "");

        ArrayList<Product> expected_add = new ArrayList<>();
        expected_add.add(product);

        // Invoke
        user.addToCart(product);

        // Analyze
        assertEquals(expected_add, user.getCart());
    }

    @Test
    public void testAddToCartFail() {
        // Setup
        int id = 20;
        String name = "name";
        String username = "username";
        String password = "password";
        boolean isAdmin = true;
        boolean darkMode = false;
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Order> currentOrders = new ArrayList<Order>();
        ArrayList<Order> previousOrders = new ArrayList<Order>();
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        ArrayList<Address> address = new ArrayList<Address>();

        // Invoke
        User user = new User(id, name, username, password, isAdmin, cart, currentOrders, previousOrders, coupons,
                address, darkMode);
        Product product = new Product(0, "test_product", "description",
                1, 1, "");

        ArrayList<Product> expected_add = new ArrayList<>();
        expected_add.add(product);
        // Analyze
        assertNotEquals(expected_add, user.getCart());
    }

    @Test
    public void testAddToCartMulti() {
        // Setup
        int id = 20;
        String name = "name";
        String username = "username";
        String password = "password";
        boolean isAdmin = true;
        boolean darkMode = false;
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Order> currentOrders = new ArrayList<Order>();
        ArrayList<Order> previousOrders = new ArrayList<Order>();
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        ArrayList<Address> address = new ArrayList<Address>();

        // Invoke
        User user = new User(id, name, username, password, isAdmin, cart, currentOrders, previousOrders, coupons,
                address, darkMode);
        Product product = new Product(0, "test_product", "description",
                1, 1, "");
        Product product2 = new Product(0, "test_product", "description",
                1, 1, "");

        Product expected_product = new Product(0, "test_product", "description",
                1, 2, "");

        ArrayList<Product> expected_add = new ArrayList<>();
        expected_add.add(expected_product);

        // Invoke
        user.addToCart(product);
        user.addToCart(product2);

        // Analyze
        assertNotEquals(expected_add, user.getCart());
    }

    @Test
    public void testRemoveToCart() {
        // Setup
        int id = 20;
        String name = "name";
        String username = "username";
        String password = "password";
        boolean isAdmin = true;
        boolean darkMode = false;
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Order> currentOrders = new ArrayList<Order>();
        ArrayList<Order> previousOrders = new ArrayList<Order>();
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        ArrayList<Address> address = new ArrayList<Address>();

        // Invoke
        User user = new User(id, name, username, password, isAdmin, cart, currentOrders, previousOrders, coupons,
                address, darkMode);
        Product product = new Product(0, "test_product", "description",
                1, 1, "");
        user.addToCart(product);
        boolean expected = true;
        // Invoke
        boolean result = user.removeFromCart(0);

        // Analyze
        assertEquals(expected, result);
    }

    @Test
    public void testRemoveToCartFail() {
        // Setup
        int id = 20;
        String name = "name";
        String username = "username";
        String password = "password";
        boolean isAdmin = true;
        boolean darkMode = false;
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Order> currentOrders = new ArrayList<Order>();
        ArrayList<Order> previousOrders = new ArrayList<Order>();
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        ArrayList<Address> address = new ArrayList<Address>();

        // Invoke
        User user = new User(id, name, username, password, isAdmin, cart, currentOrders, previousOrders, coupons,
                address, darkMode);
        // Invoke
        boolean result = user.removeFromCart(0);

        // Analyze
        assertEquals(result, false);
    }

    @Test
    public void testSetToCart() {
        // Setup
        int id = 20;
        String name = "name";
        String username = "username";
        String password = "password";
        boolean isAdmin = true;
        boolean darkMode = false;
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Order> currentOrders = new ArrayList<Order>();
        ArrayList<Order> previousOrders = new ArrayList<Order>();
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        ArrayList<Address> address = new ArrayList<Address>();

        // Invoke
        User user = new User(id, name, username, password, isAdmin, cart, currentOrders, previousOrders, coupons,
                address, darkMode);
        // Invoke
        user.setCart(cart);

        // Analyze
        assertEquals(cart, user.getCart());
    }

    @Test
    public void searchCart() {
        // Setup
        int id = 20;
        String name = "name";
        String username = "username";
        String password = "password";
        boolean isAdmin = true;
        boolean darkMode = false;
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Order> currentOrders = new ArrayList<Order>();
        ArrayList<Order> previousOrders = new ArrayList<Order>();
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        ArrayList<Address> address = new ArrayList<Address>();

        // Invoke
        User user = new User(id, name, username, password, isAdmin, cart, currentOrders, previousOrders, coupons,
                address, darkMode);
        Product product = new Product(0, "test_product", "description",
                1, 1, "");
        user.addToCart(product);
        // Invoke
        Product test = user.searchCart(0);

        // Analyze
        assertEquals(test, product);
    }

    @Test
    public void searchCartFail() {
        // Setup
        int id = 20;
        String name = "name";
        String username = "username";
        String password = "password";
        boolean isAdmin = true;
        boolean darkMode = false;
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Order> currentOrders = new ArrayList<Order>();
        ArrayList<Order> previousOrders = new ArrayList<Order>();
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        ArrayList<Address> address = new ArrayList<Address>();

        // Invoke
        User user = new User(id, name, username, password, isAdmin, cart, currentOrders, previousOrders, coupons,
                address, darkMode);
        Product product = new Product(0, "test_product", "description",
                1, 1, "");
        user.addToCart(product);
        // Invoke
        Product test = user.searchCart(1);

        // Analyze
        assertEquals(test, null);
    }

    @Test
    public void getTotalWithCoupon() {
        int id = 20;
        String name = "name";
        String username = "username";
        String password = "password";
        boolean isAdmin = true;
        boolean darkMode = false;
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Order> currentOrders = new ArrayList<Order>();
        ArrayList<Order> previousOrders = new ArrayList<Order>();
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        Coupon coupon = new Coupon(1, "test", 0.1f, "11/11/2020");
        coupons.add(coupon);
        ArrayList<Address> address = new ArrayList<Address>();
        User user = new User(id, name, username, password, isAdmin, cart, currentOrders, previousOrders, coupons,
                address, darkMode);
        Product product = new Product(0, "test_product", "description",
                1, 1, "");
        user.addToCart(product);
        // Invoke
        float test = user.getTotalWithCoupon(coupon);

        // Analyze
        assertEquals(test, 0.9f);
    }

    @Test
    public void applyCouponTest() {
        int id = 20;
        String name = "name";
        String username = "username";
        String password = "password";
        boolean isAdmin = true;
        boolean darkMode = false;
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Order> currentOrders = new ArrayList<Order>();
        ArrayList<Order> previousOrders = new ArrayList<Order>();
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        Coupon coupon = new Coupon(1, "test", 0.1f, "11/11/2020");
        coupons.add(coupon);
        ArrayList<Address> address = new ArrayList<Address>();
        User user = new User(id, name, username, password, isAdmin, cart, currentOrders, previousOrders, coupons,
                address, darkMode);
        Product product = new Product(0, "test_product", "description",
                1, 1, "");
        user.addToCart(product);
        // Invoke
        float test = user.applyCoupon(coupon);

        // Analyze
        assertEquals(test, 0.9f);
    }

    @Test
    public void getTotal() {
        int id = 20;
        String name = "name";
        String username = "username";
        String password = "password";
        boolean isAdmin = true;
        boolean darkMode = false;
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Order> currentOrders = new ArrayList<Order>();
        ArrayList<Order> previousOrders = new ArrayList<Order>();
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        Coupon coupon = new Coupon(1, "test", 0.1f, "11/11/2020");
        coupons.add(coupon);
        ArrayList<Address> address = new ArrayList<Address>();
        User user = new User(id, name, username, password, isAdmin, cart, currentOrders, previousOrders, coupons,
                address, darkMode);
        Product product = new Product(0, "test_product", "description",
                1, 1, "");
        user.addToCart(product);
        // Invoke
        float test = user.getTotal();

        // Analyze
        assertEquals(test, 1f);
    }

    @Test
    public void testRemoveCoupon() {
        int id = 20;
        String name = "name";
        String username = "username";
        String password = "password";
        boolean isAdmin = true;
        boolean darkMode = false;
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Order> currentOrders = new ArrayList<Order>();
        ArrayList<Order> previousOrders = new ArrayList<Order>();
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        Coupon coupon = new Coupon(1, "test", 0.1f, "11/11/2020");
        coupons.add(coupon);
        ArrayList<Address> address = new ArrayList<Address>();
        User user = new User(id, name, username, password, isAdmin, cart, currentOrders, previousOrders, coupons,
                address, darkMode);
        Product product = new Product(0, "test_product", "description",
                1, 1, "");
        user.addToCart(product);
        boolean expected = true;
        // Invoke
        boolean result = user.removeCoupon(coupon.getId());

        // Analyze
        assertEquals(expected, result);
    }

    @Test
    public void testRemoveCouponFail() {
        int id = 20;
        String name = "name";
        String username = "username";
        String password = "password";
        boolean isAdmin = true;
        boolean darkMode = false;
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Order> currentOrders = new ArrayList<Order>();
        ArrayList<Order> previousOrders = new ArrayList<Order>();
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        Coupon coupon = new Coupon(1, "test", 0.1f, "11/11/2020");
        coupons.add(coupon);
        ArrayList<Address> address = new ArrayList<Address>();
        User user = new User(id, name, username, password, isAdmin, cart, currentOrders, previousOrders, coupons,
                address, darkMode);
        Product product = new Product(0, "test_product", "description",
                1, 1, "");
        user.addToCart(product);
        boolean expected = false;
        // Invoke
        boolean result = user.removeCoupon(0);

        // Analyze
        assertEquals(expected, result);
    }

    @Test
    public void testAddressCoupon() {
        int id = 20;
        String name = "name";
        String username = "username";
        String password = "password";
        boolean isAdmin = true;
        boolean darkMode = false;
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Order> currentOrders = new ArrayList<Order>();
        ArrayList<Order> previousOrders = new ArrayList<Order>();
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        Coupon coupon = new Coupon(1, "test", 0.1f, "11/11/2020");
        coupons.add(coupon);
        ArrayList<Address> address = new ArrayList<Address>();
        Address address1 = new Address(99, "Antar", "Chowdhury", "123 Main St", "Anytown", "NY", 12345, "12345", false);
        address.add(address1);
        User user = new User(id, name, username, password, isAdmin, cart, currentOrders, previousOrders, coupons,
                address, darkMode);
        Product product = new Product(0, "test_product", "description",
                1, 1, "");
        user.addToCart(product);
        boolean expected = true;
        // Invoke
        boolean result = user.removeAdress(99);

        // Analyze
        assertEquals(expected, result);
    }

    @Test
    public void testRemoveAddressFail() {
        int id = 20;
        String name = "name";
        String username = "username";
        String password = "password";
        boolean isAdmin = true;
        boolean darkMode = false;
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Order> currentOrders = new ArrayList<Order>();
        ArrayList<Order> previousOrders = new ArrayList<Order>();
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        Coupon coupon = new Coupon(1, "test", 0.1f, "11/11/2020");
        coupons.add(coupon);
        ArrayList<Address> address = new ArrayList<Address>();
        User user = new User(id, name, username, password, isAdmin, cart, currentOrders, previousOrders, coupons,
                address, darkMode);
        Product product = new Product(0, "test_product", "description",
                1, 1, "");
        user.addToCart(product);
        boolean expected = false;
        // Invoke
        boolean result = user.removeAdress(0);

        // Analyze
        assertEquals(expected, result);
    }

    @Test
    public void testGetAddressCoupon() {
        int id = 20;
        String name = "name";
        String username = "username";
        String password = "password";
        boolean isAdmin = true;
        boolean darkMode = false;
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Order> currentOrders = new ArrayList<Order>();
        ArrayList<Order> previousOrders = new ArrayList<Order>();
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        Coupon coupon = new Coupon(1, "test", 0.1f, "11/11/2020");
        coupons.add(coupon);
        ArrayList<Address> address = new ArrayList<Address>();
        Address address1 = new Address(99, "Antar", "Chowdhury", "123 Main St", "Anytown", "NY", 12345, "12345", false);
        address.add(address1);
        User user = new User(id, name, username, password, isAdmin, cart, currentOrders, previousOrders, coupons,
                address, darkMode);
        Product product = new Product(0, "test_product", "description",
                1, 1, "");
        user.addToCart(product);
        // Invoke
        Address result = user.getAddress(address1.getId());

        // Analyze
        assertEquals(address1, result);
    }

    @Test
    public void testGetAddressFail() {
        int id = 20;
        String name = "name";
        String username = "username";
        String password = "password";
        boolean isAdmin = true;
        boolean darkMode = false;
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Order> currentOrders = new ArrayList<Order>();
        ArrayList<Order> previousOrders = new ArrayList<Order>();
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        Coupon coupon = new Coupon(1, "test", 0.1f, "11/11/2020");
        coupons.add(coupon);
        ArrayList<Address> address = new ArrayList<Address>();
        Address address1 = new Address(99, "Antar", "Chowdhury", "123 Main St", "Anytown", "NY", 12345, "12345", false);
        address.add(address1);
        User user = new User(id, name, username, password, isAdmin, cart, currentOrders, previousOrders, coupons,
                address, darkMode);
        Product product = new Product(0, "test_product", "description",
                1, 1, "");
        user.addToCart(product);
        // Invoke
        Address result = user.getAddress(0);

        // Analyze
        assertEquals(null, result);
    }

    @Test
    public void testGetCoupon() {
        int id = 20;
        String name = "name";
        String username = "username";
        String password = "password";
        boolean isAdmin = true;
        boolean darkMode = false;
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Order> currentOrders = new ArrayList<Order>();
        ArrayList<Order> previousOrders = new ArrayList<Order>();
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        Coupon coupon = new Coupon(1, "test", 0.1f, "11/11/2020");
        coupons.add(coupon);
        ArrayList<Address> address = new ArrayList<Address>();
        Address address1 = new Address(99, "Antar", "Chowdhury", "123 Main St", "Anytown", "NY", 12345, "12345", false);
        address.add(address1);
        User user = new User(id, name, username, password, isAdmin, cart, currentOrders, previousOrders, coupons,
                address, darkMode);
        Product product = new Product(0, "test_product", "description",
                1, 1, "");
        user.addToCart(product);
        // Invoke
        Coupon result = user.getCoupon(coupon.getId());

        // Analyze
        assertEquals(coupon, result);
    }

    @Test
    public void testGetCouponFail() {
        int id = 20;
        String name = "name";
        String username = "username";
        String password = "password";
        boolean isAdmin = true;
        boolean darkMode = false;
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Order> currentOrders = new ArrayList<Order>();
        ArrayList<Order> previousOrders = new ArrayList<Order>();
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        Coupon coupon = new Coupon(1, "test", 0.1f, "11/11/2020");
        coupons.add(coupon);
        ArrayList<Address> address = new ArrayList<Address>();
        Address address1 = new Address(99, "Antar", "Chowdhury", "123 Main St", "Anytown", "NY", 12345, "12345", false);
        address.add(address1);
        User user = new User(id, name, username, password, isAdmin, cart, currentOrders, previousOrders, coupons,
                address, darkMode);
        Product product = new Product(0, "test_product", "description",
                1, 1, "");
        user.addToCart(product);
        // Invoke
        Coupon result = user.getCoupon(0);

        // Analyze
        assertEquals(null, result);
    }

    @Test
    public void testGetDefaultAddress() {
        int id = 20;
        String name = "name";
        String username = "username";
        String password = "password";
        boolean isAdmin = true;
        boolean darkMode = false;
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Order> currentOrders = new ArrayList<Order>();
        ArrayList<Order> previousOrders = new ArrayList<Order>();
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        Coupon coupon = new Coupon(1, "test", 0.1f, "11/11/2020");
        coupons.add(coupon);
        ArrayList<Address> address = new ArrayList<Address>();
        Address address1 = new Address(99, "Antar", "Chowdhury", "123 Main St", "Anytown", "NY", 12345, "12345", true);
        address.add(address1);
        User user = new User(id, name, username, password, isAdmin, cart, currentOrders, previousOrders, coupons,
                address, darkMode);
        Product product = new Product(0, "test_product", "description",
                1, 1, "");
        user.addToCart(product);
        // Invoke
        Address result = user.getDefaultAddress();

        // Analyze
        assertEquals(address1, result);
    }

    @Test
    public void testGetDefaultAddressFail() {
        int id = 20;
        String name = "name";
        String username = "username";
        String password = "password";
        boolean isAdmin = true;
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Order> currentOrders = new ArrayList<Order>();
        ArrayList<Order> previousOrders = new ArrayList<Order>();
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        Coupon coupon = new Coupon(1, "test", 0.1f, "11/11/2020");
        coupons.add(coupon);
        ArrayList<Address> address = new ArrayList<Address>();
        Address address1 = new Address(99, "Antar", "Chowdhury", "123 Main St", "Anytown", "NY", 12345, "12345", false);
        address.add(address1);
        User user = new User(id, name, username, password, isAdmin, cart, currentOrders, previousOrders, coupons,
                address, false);
        Product product = new Product(0, "test_product", "description",
                1, 1, "");
        user.addToCart(product);
        // Invoke
        Address result = user.getDefaultAddress();

        // Analyze
        assertEquals(null, result);
    }

    @Test
    public void testCoupon() {
        int id = 20;
        String name = "name";
        String username = "username";
        String password = "password";
        boolean isAdmin = true;
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Order> currentOrders = new ArrayList<Order>();
        ArrayList<Order> previousOrders = new ArrayList<Order>();
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        Coupon coupon = new Coupon(1, "test", 0.1f, "11/11/2020");
        coupons.add(coupon);
        ArrayList<Address> address = new ArrayList<Address>();
        Address address1 = new Address(99, "Antar", "Chowdhury", "123 Main St", "Anytown", "NY", 12345, "12345", false);
        address.add(address1);
        User user = new User(id, name, username, password, isAdmin, cart, currentOrders, previousOrders, coupons,
                address, false);
        Product product = new Product(0, "test_product", "description",
                1, 1, "");
        user.addToCart(product);

        Coupon coupon1 = new Coupon(2, "te2st", 0.14f, "11/12/2020");
        boolean expected = user.addCoupon(coupon1);

        // Analyze
        assertEquals(expected, true);
    }

    @Test
    public void testAddAdress() {
        int id = 20;
        String name = "name";
        String username = "username";
        String password = "password";
        boolean isAdmin = true;
        boolean darkMode = false;
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Order> currentOrders = new ArrayList<Order>();
        ArrayList<Order> previousOrders = new ArrayList<Order>();
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        Coupon coupon = new Coupon(1, "test", 0.1f, "11/11/2020");
        coupons.add(coupon);
        ArrayList<Address> address = new ArrayList<Address>();
        Address address1 = new Address(99, "Antar", "Chowdhury", "123 Main St", "Anytown", "NY", 12345, "12345", false);
        address.add(address1);
        User user = new User(id, name, username, password, isAdmin, cart, currentOrders, previousOrders, coupons,
                address, darkMode);
        Product product = new Product(0, "test_product", "description",
                1, 1, "");
        user.addToCart(product);

        Address address2 = new Address(939, "Ant44ar", "C33howdhury", "123 M33ain St", "An33ytown", "N434Y", 1244345,
                "123444445", false);
        boolean expected = user.addAdress(address2);

        // Analyze
        assertEquals(true, expected);
    }

    @Test
    public void testaddAddress() {
        int id = 20;
        String name = "name";
        String username = "username";
        String password = "password";
        boolean isAdmin = true;
        boolean darkMode = false;
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Order> currentOrders = new ArrayList<Order>();
        ArrayList<Order> previousOrders = new ArrayList<Order>();
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        Coupon coupon = new Coupon(1, "test", 0.1f, "11/11/2020");
        coupons.add(coupon);
        ArrayList<Address> address = new ArrayList<Address>();
        Address address1 = new Address(99, "Antar", "Chowdhury", "123 Main St", "Anytown", "NY", 12345, "12345", false);
        address.add(address1);
        User user = new User(id, name, username, password, isAdmin, cart, currentOrders, previousOrders, coupons,
                address, darkMode);
        Product product = new Product(0, "test_product", "description",
                1, 1, "");
        user.addToCart(product);

        Address address2 = new Address(939, "Ant44ar", "C33howdhury", "123 M33ain St", "An33ytown", "N434Y", 1244345,
                "123444445", false);
        boolean expected = user.addAddress(address2);

        // Analyze
        assertEquals(expected, true);
    }

    @Test
    public void testaddCurrentOrder() {
        int id = 20;
        String name = "name";
        String username = "username";
        String password = "password";
        boolean isAdmin = true;
        boolean darkMode = false;
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Order> currentOrders = new ArrayList<Order>();
        ArrayList<Order> previousOrders = new ArrayList<Order>();
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        Coupon coupon = new Coupon(1, "test", 0.1f, "11/11/2020");
        coupons.add(coupon);
        ArrayList<Address> address = new ArrayList<Address>();
        Address address1 = new Address(99, "Antar", "Chowdhury", "123 Main St", "Anytown", "NY", 12345, "12345", false);
        address.add(address1);
        User user = new User(id, name, username, password, isAdmin, cart, currentOrders, previousOrders, coupons,
                address, darkMode);
        Product product = new Product(0, "test_product", "description",
                1, 1, "");
        user.addToCart(product);
        Order currenOrder = new Order(100, 1000, "00000", "expDate", 1, new ArrayList<Product>(),
                new ArrayList<Address>());

        boolean expected = user.addCurrentOrder(currenOrder);

        // Analyze
        assertEquals(expected, true);
    }

    @Test
    public void testaddPreviousOrder() {
        int id = 20;
        String name = "name";
        String username = "username";
        String password = "password";
        boolean isAdmin = true;
        boolean darkMode = false;
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Order> currentOrders = new ArrayList<Order>();
        ArrayList<Order> previousOrders = new ArrayList<Order>();
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        Coupon coupon = new Coupon(1, "test", 0.1f, "11/11/2020");
        coupons.add(coupon);
        ArrayList<Address> address = new ArrayList<Address>();
        Address address1 = new Address(99, "Antar", "Chowdhury", "123 Main St", "Anytown", "NY", 12345, "12345", false);
        address.add(address1);
        User user = new User(id, name, username, password, isAdmin, cart, currentOrders, previousOrders, coupons,
                address, darkMode);
        Product product = new Product(0, "test_product", "description",
                1, 1, "");
        user.addToCart(product);
        Order currenOrder = new Order(100, 1000, "00000", "expDate", 1, new ArrayList<Product>(),
                new ArrayList<Address>());

        boolean expected = user.addPreviousOrder(currenOrder);

        // Analyze
        assertEquals(expected, true);
    }

    @Test
    public void testsetCurrentOrder() {
        int id = 20;
        String name = "name";
        String username = "username";
        String password = "password";
        boolean isAdmin = true;
        boolean darkMode = false;
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Order> currentOrders = new ArrayList<Order>();
        ArrayList<Order> previousOrders = new ArrayList<Order>();
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        Coupon coupon = new Coupon(1, "test", 0.1f, "11/11/2020");
        coupons.add(coupon);
        ArrayList<Address> address = new ArrayList<Address>();
        Address address1 = new Address(99, "Antar", "Chowdhury", "123 Main St", "Anytown", "NY", 12345, "12345", false);
        address.add(address1);
        User user = new User(id, name, username, password, isAdmin, cart, currentOrders, previousOrders, coupons,
                address, darkMode);
        Product product = new Product(0, "test_product", "description",
                1, 1, "");
        user.addToCart(product);
        Order currenOrder = new Order(100, 1000, "00000", "expDate", 1, new ArrayList<Product>(),
                new ArrayList<Address>());
        currentOrders.add(currenOrder);
        user.setCurrentOrders(currentOrders);

        // Analyze
        assertEquals(currentOrders, user.getCurrentOrders());
    }

    @Test
    public void testsetPreviousOrder() {
        int id = 20;
        String name = "name";
        String username = "username";
        String password = "password";
        boolean isAdmin = true;
        boolean darkMode = false;
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Order> currentOrders = new ArrayList<Order>();
        ArrayList<Order> previousOrders = new ArrayList<Order>();
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        Coupon coupon = new Coupon(1, "test", 0.1f, "11/11/2020");
        coupons.add(coupon);
        ArrayList<Address> address = new ArrayList<Address>();
        Address address1 = new Address(99, "Antar", "Chowdhury", "123 Main St", "Anytown", "NY", 12345, "12345", false);
        address.add(address1);
        User user = new User(id, name, username, password, isAdmin, cart, currentOrders, previousOrders, coupons,
                address, darkMode);
        Product product = new Product(0, "test_product", "description",
                1, 1, "");
        user.addToCart(product);
        Order currenOrder = new Order(100, 1000, "00000", "expDate", 1, new ArrayList<Product>(),
                new ArrayList<Address>());

        previousOrders.add(currenOrder);
        user.setPreviousOrders(previousOrders);

        // Analyze
        assertEquals(previousOrders, user.getPreviousOrders());
    }

    @Test
    public void testSetCoupon() {
        int id = 20;
        String name = "name";
        String username = "username";
        String password = "password";
        boolean isAdmin = true;
        boolean darkMode = false;
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Order> currentOrders = new ArrayList<Order>();
        ArrayList<Order> previousOrders = new ArrayList<Order>();
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        Coupon coupon = new Coupon(1, "test", 0.1f, "11/11/2020");
        coupons.add(coupon);
        ArrayList<Address> address = new ArrayList<Address>();
        Address address1 = new Address(99, "Antar", "Chowdhury", "123 Main St", "Anytown", "NY", 12345, "12345", false);
        address.add(address1);
        User user = new User(id, name, username, password, isAdmin, cart, currentOrders, previousOrders, coupons,
                address, darkMode);
        Product product = new Product(0, "test_product", "description",
                1, 1, "");
        user.addToCart(product);

        Coupon coupon1 = new Coupon(2, "te2st", 0.14f, "11/12/2020");
        coupons.add(coupon1);
        user.setCoupons(coupons);
        // Analyze
        assertEquals(coupons, user.getCoupons());
    }

    @Test
    public void testAdminsChanger() {
        int id = 20;
        String name = "name";
        String username = "username";
        String password = "password";
        boolean isAdmin = true;
        boolean darkMode = false;
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Order> currentOrders = new ArrayList<Order>();
        ArrayList<Order> previousOrders = new ArrayList<Order>();
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        Coupon coupon = new Coupon(1, "test", 0.1f, "11/11/2020");
        coupons.add(coupon);
        ArrayList<Address> address = new ArrayList<Address>();
        Address address1 = new Address(99, "Antar", "Chowdhury", "123 Main St", "Anytown", "NY", 12345, "12345", false);
        address.add(address1);
        User user = new User(id, name, username, password, isAdmin, cart, currentOrders, previousOrders, coupons,
                address, darkMode);
        Product product = new Product(0, "test_product", "description",
                1, 1, "");
        user.addToCart(product);
        user.changeAdminStatus();
        assertEquals(false, user.getAdmin());
    }

    @Test
    public void testAdminsChangerBack() {
        int id = 20;
        String name = "name";
        String username = "username";
        String password = "password";
        boolean isAdmin = false;
        boolean darkMode = false;
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Order> currentOrders = new ArrayList<Order>();
        ArrayList<Order> previousOrders = new ArrayList<Order>();
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        Coupon coupon = new Coupon(1, "test", 0.1f, "11/11/2020");
        coupons.add(coupon);
        ArrayList<Address> address = new ArrayList<Address>();
        Address address1 = new Address(99, "Antar", "Chowdhury", "123 Main St", "Anytown", "NY", 12345, "12345", false);
        address.add(address1);
        User user = new User(id, name, username, password, isAdmin, cart, currentOrders, previousOrders, coupons,
                address, darkMode);
        Product product = new Product(0, "test_product", "description",
                1, 1, "");
        user.addToCart(product);
        user.changeAdminStatus();
        assertEquals(true, user.getAdmin());
    }

    @Test
    public void testEmptyCart() {
        int id = 20;
        String name = "name";
        String username = "username";
        String password = "password";
        boolean isAdmin = true;
        boolean darkMode = false;
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Order> currentOrders = new ArrayList<Order>();
        ArrayList<Order> previousOrders = new ArrayList<Order>();
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        Coupon coupon = new Coupon(1, "test", 0.1f, "11/11/2020");
        coupons.add(coupon);
        ArrayList<Address> address = new ArrayList<Address>();
        Address address1 = new Address(99, "Antar", "Chowdhury", "123 Main St", "Anytown", "NY", 12345, "12345", false);
        address.add(address1);
        User user = new User(id, name, username, password, isAdmin, cart, currentOrders, previousOrders, coupons,
                address, darkMode);
        boolean expected = user.emptyCart();
        assertEquals(true, expected);
    }

    @Test
    public void testToString() {
        // Setup
        int id = 20;
        String name = "name";
        String username = "username";
        String password = "password";
        boolean isAdmin = true;
        boolean darkMode = false;
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Order> currentOrders = new ArrayList<Order>();
        ArrayList<Order> previousOrders = new ArrayList<Order>();
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        ArrayList<Address> address = new ArrayList<Address>();
        User user = new User(id, name, username, password, isAdmin, cart, currentOrders, previousOrders, coupons,
                address, darkMode);

        // Invoke
        String expected_string = String.format(STRING_FORMAT, id, name, username, password, isAdmin, cart,
                currentOrders, previousOrders, coupons, address, darkMode);

        // Analyze
        assertEquals(expected_string, user.toString());
    }

}
