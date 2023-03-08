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
import com.products.api.productsapi.model.Model;
import com.products.api.productsapi.model.Order;
import com.products.api.productsapi.model.Product;


/**
 * Test for the AddressFileDAO class.
 * @author Antar Narayan Chowdhury
 */
@Tag("Persistence-tier")
public class OrderFileDAOTest {
    OrderFileDAO testDAO;
        Order[] testOrders;
        ObjectMapper testMap;
    
        /**
         * Before each test, create a new ObjectMapper.
         * @throws IOException
         */
        @BeforeEach
        public void createTestPFile() throws IOException
        {
            // Initialize testDAO
            testMap = mock(ObjectMapper.class);
        
            ArrayList<Product> products = new ArrayList<Product>();
            products.add(new Product(100, "Peppers", "Test peppers", (float) 12.99, 2, ""));
            products.add(new Product(101, "Bananas", "Test bananas", (float) 1.99, 1, ""));
            products.add(new Product(102, "Bagels", "Test bagels", (float) 2.99, 3, ""));

            testOrders = new Order[3];
            testOrders[0] = new Order(100, 1000, "00000", "expDate", 1, products, new ArrayList<Address>());
            testOrders[1] = new Order(101, 10001, "00100", "expDat2e", 134,  new ArrayList<Product>(), new ArrayList<Address>());
            testOrders[2] = new Order(1320, 1003430, "0000430", "exp343Date", 1343, new ArrayList<Product>(), new ArrayList<Address>());
    

            
            when(testMap.readValue(new File("test.txt"),Order[].class))
                    .thenReturn(testOrders);
                
            testDAO = new OrderFileDAO("test.txt", testMap);
            
        }
    
        /**
         * Test for getAll().
         * @throws IOException
         */
        @Test
        public void testGetAll() throws IOException
        {
            // Create order list
            Order[] orders = testDAO.getAll();
    
            // Assert
            assertEquals(orders.length, testOrders.length);
            for (int i = 0; i < testOrders.length;++i)
                assertEquals(orders[i],testOrders[i]);
        }
    
        /**
         * Test for search().
         * @throws IOException
         */
        @Test
        public void testSearch() throws IOException {
            // Create model list
            Model[] models = testDAO.search("Bananas");
    
            // Assert
            assertEquals(models.length, 1);
            assertEquals(models[0], testOrders[0]);
        }
    
        /**
         * Test for get().
         * @throws IOException
         */
        @Test
        public void testGet() throws IOException
        {
            // Get order
            Order order = testDAO.get(100);
    
            // Assert
            assertEquals(order, testOrders[0]);
        }
    
        /**
         * Test for create().
         * @throws IOException
         */
        @Test
        public void testCreate() throws IOException
        {
            // Create new order
            Order order = new Order(1321, 1300, "30000", "3expDate", 31, new ArrayList<Product>(), new ArrayList<Address>());
    
            // Test create()
            Model result = assertDoesNotThrow(() -> testDAO.create(order), "Exception thrown");
    
            // Assert
            assertNotNull(result);
            Order actual = testDAO.get(order.getId());

            assertEquals(actual.getId(), order.getId());
            assertEquals(actual.getUserId(), order.getUserId());
            assertEquals(actual.getCardNumber(), order.getCardNumber());
            assertEquals(actual.getExpDate(), order.getExpDate());
            assertEquals(actual.getProducts(), order.getProducts());
            assertEquals(actual.getAddress(), order.getAddress());
        }
    
        /**
         * Test for update().
         * @throws IOException
         */
        @Test
        public void testUpdate() throws IOException
        {
            // Create new order
            Order order =  new Order(100, 12001, "001300", "exp3Dat2e", 13443,  new ArrayList<Product>(), new ArrayList<Address>());
            // Test update()
            Model result = assertDoesNotThrow(() -> testDAO.update(order), "Exception thrown");
    
            // Assert
            assertNotNull(result);
            Order actual = testDAO.get(order.getId());
            assertEquals(actual, order);
        }
    
        /**
         * Test for delete().
         */
        @Test
        public void testDelete()
        {
            // Test delete()
            boolean result = assertDoesNotThrow(() -> testDAO.delete(100), "Exception thrown");
    
            // Assert
            assertEquals(result, true);
            assertEquals(testDAO.orders.size(),testOrders.length-1);
        }
    
        /**
         * Test for an exception thrown from save().
         * @throws IOException
         */
        @Test
        public void testSaveException() throws IOException
        {
            doThrow(new IOException()).when(testMap).writeValue(any(File.class),any(Order[].class));
    
            Order order = new Order(104, 1000241, "00123400", "exp24Dat2e", 24134,  new ArrayList<Product>(), new ArrayList<Address>());
    
            assertThrows(IOException.class, () -> testDAO.create(order), "Exception not thrown");
        }
    
        /**
         * Test for getting a non-existent order.
         * @throws IOException
         */
        @Test
        public void testGetUnknownOrder() throws IOException
        {
            // Try to get order
            Order order = testDAO.get(99);
    
            // Assert
            assertEquals(order, null);
        }
    
        /**
         * Test for deleting a non-existent order.
         */
        @Test
        public void testDeleteUnknownOrder() 
        {
            // Create test
            boolean result = assertDoesNotThrow(() -> testDAO.delete(99), "Unexpected exception thrown");
    
            // Assert
            assertEquals(result, false);
            assertEquals(testDAO.orders.size(),testOrders.length);
        }
    
        /**
         * Test for updating a non-existent order.
         */
        @Test
        public void testUpdateUnknownOrder() 
        {
            // Create order
            Order order = new Order(99, 10031, "001030", "exp3Dat2e", 1343,  new ArrayList<Product>(), new ArrayList<Address>());
    
            // Test update() for unknown order
            Model result = assertDoesNotThrow(() -> testDAO.update(order), "Unexpected exception thrown");
    
            // Assert
            assertNull(result);
        }
    
        /**
         * Test for possible exception caused by order constructor.
         * @throws IOException
         */
        @Test
        public void testConstructorException() throws IOException 
        {
            // Create ObjectMapper
            ObjectMapper testMap = mock(ObjectMapper.class);
    
            doThrow(new IOException()).when(testMap).readValue(new File("test.txt"), Order[].class);
    
            // Assert
            assertThrows(IOException.class, () -> new OrderFileDAO("test.txt", testMap), "Exception not thrown");
        }
        
    }
