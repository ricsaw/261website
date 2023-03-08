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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.products.api.productsapi.model.Model;
import com.products.api.productsapi.model.Product;

/**
 * Test for the ProductFileDAO class.
 * 
 * @author James De Luca
 */
@Tag("Persistence-tier")
 public class ProductFileDAOTest {
    ProductFileDAO testDAO;
    Product[] testProducts;
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
        testProducts = new Product[3];
        testProducts[0] = new Product(100, "Peppers", "Test peppers", (float) 12.99, 2, "");
        testProducts[1] = new Product(101, "Bananas", "Test bananas", (float) 1.99, 1, "");
        testProducts[2] = new Product(102, "Bagels", "Test bagels", (float) 2.99, 3, "");

        when(testMap.readValue(new File("test.txt"),Product[].class))
                .thenReturn(testProducts);
            
        testDAO = new ProductFileDAO("test.txt", testMap);
        
    }

    /**
     * Test for getAll().
     * @throws IOException
     */
    @Test
    public void testGetAll() throws IOException
    {
        // Create product list
        Product[] products = testDAO.getAll();

        // Assert
        assertEquals(products.length, testProducts.length);
        for (int i = 0; i < testProducts.length;++i)
            assertEquals(products[i],testProducts[i]);
    }

    /**
     * Test for search().
     * @throws IOException
     */
    @Test
    public void testSearch() throws IOException
    {
        // Create model list
        Model[] models = testDAO.search("Ba");

        // Assert
        assertEquals(models.length, 2);
        assertEquals(models[0],testProducts[1]);
        assertEquals(models[1],testProducts[2]);
    }

    /**
     * Test for get().
     * @throws IOException
     */
    @Test
    public void testGet() throws IOException
    {
        // Get product
        Product product = testDAO.get(100);

        // Assert
        assertEquals(product, testProducts[0]);
    }

    /**
     * Test for create().
     * @throws IOException
     */
    @Test
    public void testCreate() throws IOException
    {
        // Create new product
        Product product = new Product(103, "Eggs", "Test eggs", (float) 10.99, 10, "");

        // Test create()
        Model result = assertDoesNotThrow(() -> testDAO.create(product), "Exception thrown");

        // Assert
        assertNotNull(result);
        Product actual = testDAO.get(product.getId());
        assertEquals(actual.getId(), product.getId());
        assertEquals(actual.getName(), product.getName());
    }

    /**
     * Test for update().
     * @throws IOException
     */
    @Test
    public void testUpdate() throws IOException
    {
        // Create new product
        Product product = new Product(100, "Habaneros", "Test habaneros", (float) 9.99, 10, "");

        // Test update()
        Model result = assertDoesNotThrow(() -> testDAO.update(product), "Exception thrown");

        // Assert
        assertNotNull(result);
        Product actual = testDAO.get(product.getId());
        assertEquals(actual, product);
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
        assertEquals(testDAO.products.size(),testProducts.length-1);
    }

    /**
     * Test for an exception thrown from save().
     * @throws IOException
     */
    @Test
    public void testSaveException() throws IOException
    {
        doThrow(new IOException()).when(testMap).writeValue(any(File.class),any(Product[].class));

        Product product = new Product(104, "Corn", "Corn on the cob", (float) 5.99, 3, "");

        assertThrows(IOException.class, () -> testDAO.create(product), "Exception not thrown");
    }

    /**
     * Test for getting a non-existent product.
     * @throws IOException
     */
    @Test
    public void testGetUnknownProduct() throws IOException
    {
        // Try to get product
        Product product = testDAO.get(99);

        // Assert
        assertEquals(product, null);
    }

    /**
     * Test for deleting a non-existent product.
     */
    @Test
    public void testDeleteUnknownProduct() 
    {
        // Create test
        boolean result = assertDoesNotThrow(() -> testDAO.delete(99), "Unexpected exception thrown");

        // Assert
        assertEquals(result, false);
        assertEquals(testDAO.products.size(),testProducts.length);
    }

    /**
     * Test for updating a non-existent product.
     */
    @Test
    public void testUpdateUnknownProduct() 
    {
        // Create product
        Product product = new Product(99, "Peanuts", "Test peanuts", (float) 1.99, 1, "");

        // Test update() for unknown product
        Model result = assertDoesNotThrow(() -> testDAO.update(product), "Unexpected exception thrown");

        // Assert
        assertNull(result);
    }

    /**
     * Test for possible exception caused by product constructor.
     * @throws IOException
     */
    @Test
    public void testConstructorException() throws IOException 
    {
        // Create ObjectMapper
        ObjectMapper testMap = mock(ObjectMapper.class);

        doThrow(new IOException()).when(testMap).readValue(new File("test.txt"), Product[].class);

        // Assert
        assertThrows(IOException.class, () -> new ProductFileDAO("test.txt", testMap), "Exception not thrown");
    }
    
}