package com.products.api.productsapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.products.api.productsapi.model.Address;
import com.products.api.productsapi.model.Order;
import com.products.api.productsapi.model.Product;
import com.products.api.productsapi.persistence.OrderFileDAO;

/**
 * This is the test class for OrderController
 * 
 * @author Antar Narayan Chowdhury
 */

@Tag("Controller-tier")
public class OrderControllerTest {
    private OrderController orderController;
    private OrderFileDAO mockDAO;

    /**
     * Before each test, create a new OrderController object and inject
     * a mock Order DAO
     */
    @BeforeEach
    public void setupOrderController() {
        mockDAO = mock(OrderFileDAO.class);
        orderController = new OrderController(mockDAO);
    }

    @Test
    public void testGetOrder() throws IOException { // get may throw IOException
        // Setup
        Order order = new Order(99, 10031, "001030", "exp3Dat2e", 1343, new ArrayList<Product>(),
                new ArrayList<Address>());

        // When the same id is passed in, our mock Order DAO will return the Order
        // object
        when(mockDAO.get(order.getId())).thenReturn(order);

        // Invoke
        ResponseEntity<Order> response = orderController.get(order.getId());

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order, response.getBody());
    }

    @Test
    public void testGetOrderNotFound() throws Exception { // create may throw IOException
        // Setup
        int OrderId = 99;
        // When the same id is passed in, our mock Order DAO will return null,
        // simulating
        // no Order found
        when(mockDAO.get(OrderId)).thenReturn(null);

        // Invoke
        ResponseEntity<Order> response = orderController.get(OrderId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetOrderHandleException() throws Exception { // create may throw IOException
        // Setup
        int OrderId = 99;
        // When get is called on the Mock Order DAO, throw an IOException
        doThrow(new IOException()).when(mockDAO).get(OrderId);

        // Invoke
        ResponseEntity<Order> response = orderController.get(OrderId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /*****************************************************************
     * The following tests will fail until all OrderController methods
     * are implemented.
     ****************************************************************/

    @Test
    public void testCreateOrder() throws IOException { // create may throw IOException
        // Setup
        Order order = new Order(99, 10031, "001030", "exp3Dat2e", 1343, new ArrayList<Product>(),
                new ArrayList<Address>());

        // when create is called, return true simulating successful
        // creation and save
        when(mockDAO.create(order)).thenReturn(order);

        // Invoke
        ResponseEntity<Order> response = orderController.create(order);

        // Analyze
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(order, response.getBody());
    }

    @Test
    public void testCreateDuplicate() throws IOException { // create may throw IOException
        //
        ArrayList<Product> products = new ArrayList<Product>();
        products.add(new Product(100, "Peppers", "Test peppers", (float) 12.99, 2, ""));
        products.add(new Product(101, "Bananas", "Test bananas", (float) 1.99, 1, ""));
        products.add(new Product(102, "Bagels", "Test bagels", (float) 2.99, 3, ""));

        Order order = new Order(99, 10031, "001030", "exp3Dat2e", 1343, products, new ArrayList<Address>());
        Order order2 = new Order(99, 10031, "001030", "exp3Dat2e", 1343, products, new ArrayList<Address>());
        Order order3 = new Order(98, 10031, "001032", "exp334Dat2e", 1343234, new ArrayList<Product>(),
                new ArrayList<Address>());
        Order order4 = new Order(97, 10031, "001033", "exp33Dat2e", 134332, new ArrayList<Product>(),
                new ArrayList<Address>());

        Order[] listWithDuplicates = new Order[] { order, order2, order3, order4 };

        // when create is called, return true simulating successful
        // creation and save
        when(mockDAO.search(order.getProducts().toString())).thenReturn(listWithDuplicates);

        // Invoke
        ResponseEntity<Order> response = orderController.create(order);
        response = orderController.create(order);

        // Analyze
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testCreateOrderFailed() throws IOException { // create may throw IOException
        // Setup
        Order order = new Order(99, 10031, "001030", "exp3Dat2e", 1343, new ArrayList<Product>(),
                new ArrayList<Address>());

        // when create is called, return false simulating failed
        // creation and save
        when(mockDAO.create(order)).thenReturn(null);

        // Invoke
        ResponseEntity<Order> response = orderController.create(order);

        // Analyze
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    // @Test
    // public void testCreateInvalidOrder() throws IOException { // create may throw
    // IOException
    // // Setup
    // User user = new User(99,"Coolest user", "coolDude", "password", true);

    // // Invoke
    // ResponseEntity<Order> response = orderController.create(user);

    // // Analyze
    // assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    // }

    @Test
    public void testCreateOrderHandleException() throws IOException { // create may throw IOException
        // Setup
        Order order = new Order(99, 10031, "001030", "exp3Dat2e", 1343, new ArrayList<Product>(),
                new ArrayList<Address>());

        // When create is called on the Mock Order DAO, throw an IOException
        doThrow(new IOException()).when(mockDAO).create(order);

        // Invoke
        ResponseEntity<Order> response = orderController.create(order);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    // @Test
    // public void testUpdateOrder() throws IOException { // update may throw
    // IOException
    // // Setup
    // Order order = new Order(99,"super food", "super cool food", 10.0f, 3);

    // // when update is called, return true simulating successful
    // // update and save
    // when(mockDAO.update(order)).thenReturn(order);
    // ResponseEntity<Model> response = OrderController.update(order);
    // order.setName("Super Nasty Food");

    // // Invoke
    // response = OrderController.update(order);

    // // Analyze
    // assertEquals(HttpStatus.OK,response.getStatusCode());
    // assertEquals(order,response.getBody());
    // }

    @Test
    public void testUpdateOrder() throws IOException { // create may throw IOException
        // Setup
        Order order = new Order(99, 10031, "00102330", "exp3Dat2e", 1343, new ArrayList<Product>(),
                new ArrayList<Address>());

        // when create is called, return true simulating successful
        // creation and save
        when(mockDAO.update(order)).thenReturn(order);
        when(mockDAO.get(order.getId())).thenReturn(order);
        // Invoke
        ResponseEntity<Order> response = orderController.update(order);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order, response.getBody());
    }

    @Test
    public void testUpdateOrderFailed() throws IOException { // update may throw IOException
        // Setup
        Order order = new Order(99, 10031, "00102330", "exp3Dat2e", 1343, new ArrayList<Product>(),
                new ArrayList<Address>());

        // when update is called, return true simulating successful
        // update and save
        when(mockDAO.update(order)).thenReturn(null);

        // Invoke
        ResponseEntity<Order> response = orderController.update(order);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateOrderHandleException() throws IOException { // update may throw IOException
        // Setup
        Order order = new Order(99, 10031, "00102330", "exp3Dat2e", 1343, new ArrayList<Product>(),
                new ArrayList<Address>());
        // When update is called on the Mock Order DAO, throw an IOException
        doThrow(new IOException()).when(mockDAO).update(order);

        // Invoke
        ResponseEntity<Order> response = orderController.update(order);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    // @Test
    // public void testUpdateInvalidOrder() throws IOException { // create may throw
    // IOException
    // // Setup
    // User user = new Order(99,"Coolest user", "coolDude", "password", true);

    // // Invoke
    // ResponseEntity<Order> response = orderController.update(user);

    // // Analyze
    // assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    // }

    @Test
    public void testGetorders() throws IOException { // getAll may throw IOException
        // Setup
        Order[] orders = new Order[2];
        orders[0] = new Order(99, 10031, "00102330", "exp3Dat2e", 1343, new ArrayList<Product>(),
                new ArrayList<Address>());
        orders[1] = new Order(100, 10032, "00102330", "expDate", 1343, new ArrayList<Product>(),
                new ArrayList<Address>());

        // When getAll is called return the orders created above
        when(mockDAO.getAll()).thenReturn(orders);

        // Invoke
        ResponseEntity<Order[]> response = orderController.getAll();

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orders, response.getBody());
    }

    @Test
    public void testGetordersHandleException() throws IOException { // getAll may throw IOException
        // Setup
        // When getAll is called on the Mock Order DAO, throw an IOException
        doThrow(new IOException()).when(mockDAO).getAll();

        // Invoke
        ResponseEntity<Order[]> response = orderController.getAll();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testSearchorders() throws IOException { // search may throw IOException
        // Setup
        String searchString = "la";
        Order[] orders = new Order[2];
        orders[0] = new Order(99, 10031, "00102330", "exp3Dat2e", 1343, new ArrayList<Product>(),
                new ArrayList<Address>());
        orders[1] = new Order(100, 10032, "00102330", "expDate", 1343, new ArrayList<Product>(),
                new ArrayList<Address>());

        // When search is called with the search string, return the two
        /// orders above
        when(mockDAO.search(searchString)).thenReturn(orders);

        // Invoke
        ResponseEntity<Order[]> response = orderController.search(searchString);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orders, response.getBody());
    }

    @Test
    public void testSearchordersHandleException() throws IOException { // search may throw IOException
        // Setup
        String searchString = "an";
        // When create is called on the Mock Order DAO, throw an IOException
        doThrow(new IOException()).when(mockDAO).search(searchString);

        // Invoke
        ResponseEntity<Order[]> response = orderController.search(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteOrder() throws IOException { // delete may throw IOException
        // Setup
        int OrderId = 99;
        // when delete is called return true, simulating successful deletion
        when(mockDAO.delete(OrderId)).thenReturn(true);

        // Invoke
        ResponseEntity<Order> response = orderController.delete(OrderId);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteOrderNotFound() throws IOException { // delete may throw IOException
        // Setup
        int OrderId = 99;
        // when delete is called return false, simulating failed deletion
        when(mockDAO.delete(OrderId)).thenReturn(false);

        // Invoke
        ResponseEntity<Order> response = orderController.delete(OrderId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteOrderHandleException() throws IOException { // delete may throw IOException
        // Setup
        int OrderId = 99;
        // When delete is called on the Mock Order DAO, throw an IOException
        doThrow(new IOException()).when(mockDAO).delete(OrderId);

        // Invoke
        ResponseEntity<Order> response = orderController.delete(OrderId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

}
