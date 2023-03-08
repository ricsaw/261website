package com.products.api.productsapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/*
 * Test class for Order
 * @author: Antar Narayan Chowdhury
 */
@Tag("Model-tier")
public class OrderTest {

    static final String STRING_FORMAT = "Order [id=%d, userId=%d, cardNumber=\"%s\", expDate=\"%s\", securityCode=%d, products=\"%s\", address= \"%s\"]";

    @Test
    public void testCtor() {
        // Setup
        int id = 20;
        int userId = 20;
        String cardNumber = "0000-0000-0000-0000";
        String expDate = "10/10/2022";
        int securityCode = 1;
        ArrayList<Product> product = new ArrayList<Product>();
        ArrayList<Address> address = new ArrayList<Address>();

        // Invoke
        Order order = new Order(id, userId, cardNumber, expDate, securityCode, product, address);

        // Analyze
        assertEquals(id, order.getId());
        assertEquals(userId, order.getUserId());
        assertEquals(cardNumber, order.getCardNumber());
        assertEquals(expDate, order.getExpDate());
        assertEquals(product, order.getProducts());
        assertEquals(address, order.getAddress());

    }

    @Test
    public void testId() {
        // Setup
        int id = 20;
        int userId = 20;
        String cardNumber = "0000-0000-0000-0000";
        String expDate = "10/10/2022";
        int securityCode = 1;
        ArrayList<Product> product = new ArrayList<Product>();
        ArrayList<Address> address = new ArrayList<Address>();
        Order order = new Order(id, userId, cardNumber, expDate, securityCode, product, address);

        int expectedId = 25;

        // Invoke
        order.setId(expectedId);

        // Analyze
        assertEquals(expectedId, order.getId());
    }

    @Test
    public void testUserId() {
        // Setup
        int id = 20;
        int userId = 20;
        String cardNumber = "0000-0000-0000-0000";
        String expDate = "10/10/2022";
        int securityCode = 1;
        ArrayList<Product> product = new ArrayList<Product>();
        ArrayList<Address> address = new ArrayList<Address>();
        Order order = new Order(id, userId, cardNumber, expDate, securityCode, product, address);

        int expectedId = 25;

        // Invoke
        order.setUserId(expectedId);

        // Analyze
        assertEquals(expectedId, order.getUserId());
    }

    @Test
    public void testCardNumber() {
        // Setup
        int id = 20;
        int userId = 20;
        String cardNumber = "0000-0000-0000-0000";
        String expDate = "10/10/2022";
        int securityCode = 1;
        ArrayList<Product> product = new ArrayList<Product>();
        ArrayList<Address> address = new ArrayList<Address>();
        Order order = new Order(id, userId, cardNumber, expDate, securityCode, product, address);

        String expectedCard = "0000-0000-0000-1111";

        // Invoke
        order.setCardNumber(expectedCard);

        // Analyze
        assertEquals(expectedCard, order.getCardNumber());
    }

    @Test
    public void testPrice() {
        // Setup
        int id = 20;
        int userId = 20;
        String cardNumber = "0000-0000-0000-0000";
        String expDate = "10/10/2022";
        int securityCode = 1;
        ArrayList<Product> product = new ArrayList<Product>();
        ArrayList<Address> address = new ArrayList<Address>();
        Order order = new Order(id, userId, cardNumber, expDate, securityCode, product, address);

        String expectedCard = "11/11/2022";

        // Invoke
        order.setExpDate(expectedCard);

        // Analyze
        assertEquals(expectedCard, order.getExpDate());
    }

    @Test
    public void testSecurityCode() {
        // Setup
        int id = 20;
        int userId = 20;
        String cardNumber = "0000-0000-0000-0000";
        String expDate = "10/10/2022";
        int securityCode = 1;
        ArrayList<Product> product = new ArrayList<Product>();
        ArrayList<Address> address = new ArrayList<Address>();
        Order order = new Order(id, userId, cardNumber, expDate, securityCode, product, address);

        int expectedSecuirtyCode = 99;

        // Invoke
        order.setSecurityCode(expectedSecuirtyCode);
        // Analyze
        assertEquals(expectedSecuirtyCode, order.getSecurityCode());
    }

    @Test
    public void testProduct() {
        // Setup
        int id = 20;
        int userId = 20;
        String cardNumber = "0000-0000-0000-0000";
        String expDate = "10/10/2022";
        int securityCode = 1;
        ArrayList<Product> product = new ArrayList<Product>();
        ArrayList<Address> address = new ArrayList<Address>();
        Order order = new Order(id, userId, cardNumber, expDate, securityCode, product, address);
        Product produt = new Product(0, "test_product", "description", 1, 1, "");
        product.add(produt);
        // Invoke
        order.setProducts(product);
        // Analyze
        assertEquals(product, order.getProducts());
    }

    @Test
    public void testAddress() {
        // Setup
        int id = 20;
        int userId = 20;
        String cardNumber = "0000-0000-0000-0000";
        String expDate = "10/10/2022";
        int securityCode = 1;
        ArrayList<Product> product = new ArrayList<Product>();
        ArrayList<Address> address = new ArrayList<Address>();
        Order order = new Order(id, userId, cardNumber, expDate, securityCode, product, address);
        Address produt = new Address(100, "Antares", "Cavduri", "123 eeMain St", "Anyteown", "NYC", 122245, "67899",
                false);
        address.add(produt);
        // Invoke
        order.setProducts(product);
        // Analyze
        assertEquals(product, order.getProducts());
    }

    @Test
    public void testToString() {
        // Setup
        int id = 20;
        int userId = 20;
        String cardNumber = "0000-0000-0000-0000";
        String expDate = "10/10/2022";
        int securityCode = 1;
        ArrayList<Product> product = new ArrayList<Product>();
        ArrayList<Address> address = new ArrayList<Address>();
        Order order = new Order(id, userId, cardNumber, expDate, securityCode, product, address);

        // Invoke
        String expected_string = String.format(STRING_FORMAT, id, userId, cardNumber, expDate, securityCode, product,
                address);

        // Analyze
        assertEquals(expected_string, order.toString());
    }

}