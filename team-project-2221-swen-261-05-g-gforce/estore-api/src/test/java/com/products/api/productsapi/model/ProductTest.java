package com.products.api.productsapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test for the Product Class
 * 
 * @author Aidan Collins
 */
@Tag("Model-Tier")
public class ProductTest {

    // Package private for tests
    static final String STRING_FORMAT = "Product [name=%s, id=%d, price=%.2f, quantity=%d, description=\"%s\", imgUrl=\"%s\"]";

    @Test
    public void testCtor() {
        // Setup
        int id = 20;
        String name = "Hamburger";
        String description = "Bun with burger and cheese";
        float price = 4.99f;
        int quantity = 30;
        String url = "";

        // Invoke
        Product product = new Product(id, name, description, price, quantity, url);

        // Analyze
        assertEquals(id, product.getId());
        assertEquals(name, product.getName());
        assertEquals(description, product.getDescription());
        assertEquals(price, product.getPrice());
        assertEquals(quantity, product.getQuantity());
    }

    @Test
    public void testName() {
        // Setup
        int id = 20;
        String name = "Hamburger";
        String description = "Bun with burger and cheese";
        float price = 4.99f;
        int quantity = 30;
        String url = "";
        Product product = new Product(id, name, description, price, quantity, url);

        String expected_name = "Cheeseburger";

        // Invoke
        product.setName(expected_name);

        // Analyze
        assertEquals(expected_name, product.getName());
    }

    @Test
    public void testId() {
        // Setup
        int id = 20;
        String name = "Hamburger";
        String description = "Bun with burger and cheese";
        float price = 4.99f;
        int quantity = 30;
        String url = "";
        Product product = new Product(id, name, description, price, quantity, url);

        int expected_id = 5;

        // Invoke
        product.setId(expected_id);

        // Analyze
        assertEquals(expected_id, product.getId());
    }

    @Test
    public void testDescription() {
        // Setup
        int id = 20;
        String name = "Hamburger";
        String description = "Bun with burger and cheese";
        float price = 4.99f;
        int quantity = 30;
        String url = "";
        Product product = new Product(id, name, description, price, quantity, url);

        String expected_description = "Bun with burger and no cheese";

        // Invoke
        product.setDescription(expected_description);

        // Analyze
        assertEquals(expected_description, product.getDescription());
    }

    @Test
    public void testPrice() {
        // Setup
        int id = 20;
        String name = "Hamburger";
        String description = "Bun with burger and cheese";
        float price = 4.99f;
        int quantity = 30;
        String url = "";
        Product product = new Product(id, name, description, price, quantity, url);

        float expected_price = 3.99f;

        // Invoke
        product.setPrice(expected_price);

        // Analyze
        assertEquals(expected_price, product.getPrice());
    }

    @Test
    public void testQuantity() {
        // Setup
        int id = 20;
        String name = "Hamburger";
        String description = "Bun with burger and cheese";
        float price = 4.99f;
        int quantity = 30;
        String url = "";
        Product product = new Product(id, name, description, price, quantity, url);

        int expected_quantity = 99;

        // Invoke
        product.setQuantity(expected_quantity);

        // Analyze
        assertEquals(expected_quantity, product.getQuantity());
    }

    @Test
    public void testToString() {
        // Setup
        int id = 20;
        String name = "Hamburger";
        String description = "Bun with burger and cheese";
        float price = 4.99f;
        int quantity = 30;
        String url = "";
        Product product = new Product(id, name, description, price, quantity, url);

        // Invoke
        String expected_string = String.format(STRING_FORMAT, name, id, price, quantity, description, url);

        // Analyze
        assertEquals(expected_string, product.toString());
    }

}