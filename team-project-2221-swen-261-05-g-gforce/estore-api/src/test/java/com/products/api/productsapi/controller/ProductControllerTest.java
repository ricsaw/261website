package com.products.api.productsapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.products.api.productsapi.persistence.ProductFileDAO;
import com.products.api.productsapi.model.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the Product Controller class
 * 
 * @author Robert
 */
@Tag("Controller-tier")
public class ProductControllerTest {
    private ProductController productController;
    private ProductFileDAO mockDAO;

    /**
     * Before each test, create a new ProductController object and inject
     * a mock Product DAO
     */
    @BeforeEach
    public void setupProductController() {
        mockDAO = mock(ProductFileDAO.class);
        productController = new ProductController(mockDAO);
    }

    @Test
    public void testGetProduct() throws IOException { // get may throw IOException
        // Setup
        Product product = new Product(99, "super food", "super cool food", 10.0f, 3, "");
        // When the same id is passed in, our mock Product DAO will return the Product
        // object
        when(mockDAO.get(product.getId())).thenReturn(product);

        // Invoke
        ResponseEntity<Product> response = productController.get(product.getId());

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    public void testGetProductNotFound() throws Exception { // create may throw IOException
        // Setup
        int ProductId = 99;
        // When the same id is passed in, our mock Product DAO will return null,
        // simulating
        // no Product found
        when(mockDAO.get(ProductId)).thenReturn(null);

        // Invoke
        ResponseEntity<Product> response = productController.get(ProductId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetProductHandleException() throws Exception { // create may throw IOException
        // Setup
        int ProductId = 99;
        // When get is called on the Mock Product DAO, throw an IOException
        doThrow(new IOException()).when(mockDAO).get(ProductId);

        // Invoke
        ResponseEntity<Product> response = productController.get(ProductId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /*****************************************************************
     * The following tests will fail until all ProductController methods
     * are implemented.
     ****************************************************************/

    @Test
    public void testCreateProduct() throws IOException { // create may throw IOException
        // Setup
        Product product = new Product(99, "Raddest Food", "super cool-est food", 10.0f, 3, "");

        // when create is called, return true simulating successful
        // creation and save
        when(mockDAO.create(product)).thenReturn(product);

        // Invoke
        ResponseEntity<Product> response = productController.create(product);

        // Analyze
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    public void testCreateDuplicate() throws IOException { // create may throw IOException
        // Setup
        Product product = new Product(99, "Raddest Food", "super cool-est food", 10.0f, 3, "");
        Product product2 = new Product(98, "Super Raddest food", "pretty okay food", 10.0f, 3, "");
        Product product3 = new Product(97, "boring food", "kinda bland but in the Raddest way", 10.0f, 3, "");
        Product product4 = new Product(99, "Raddest Food", "super cool-est food", 10.0f, 3, "");

        Product[] listWithDuplicates = new Product[] { product, product2, product3, product4 };

        // when create is called, return true simulating successful
        // creation and save
        when(mockDAO.search(product.getName())).thenReturn(listWithDuplicates);

        // Invoke
        ResponseEntity<Product> response = productController.create(product);
        response = productController.create(product);

        // Analyze
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testCreateProductFailed() throws IOException { // create may throw IOException
        // Setup
        Product product = new Product(99, "bad food", "nasty af", 10.0f, 3, "");

        // when create is called, return false simulating failed
        // creation and save
        when(mockDAO.create(product)).thenReturn(null);

        // Invoke
        ResponseEntity<Product> response = productController.create(product);

        // Analyze
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    // @Test
    // public void testCreateInvalidProduct() throws IOException { // create may
    // throw IOException
    // // Setup
    // User user = new User(99,"Coolest user", "coolDude", "password", true);

    // // Invoke
    // ResponseEntity<Product> response = productController.create(user);

    // // Analyze
    // assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    // }

    @Test
    public void testCreateProductHandleException() throws IOException { // create may throw IOException
        // Setup
        Product product = new Product(99, "expired food", "a week old", 10.0f, 3, "");

        // When create is called on the Mock Product DAO, throw an IOException
        doThrow(new IOException()).when(mockDAO).create(product);

        // Invoke
        ResponseEntity<Product> response = productController.create(product);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    // @Test
    // public void testUpdateProduct() throws IOException { // update may throw
    // IOException
    // // Setup
    // Product product = new Product(99,"super food", "super cool food", 10.0f, 3);

    // // when update is called, return true simulating successful
    // // update and save
    // when(mockDAO.update(product)).thenReturn(product);
    // ResponseEntity<Model> response = ProductController.update(product);
    // product.setName("Super Nasty Food");

    // // Invoke
    // response = ProductController.update(product);

    // // Analyze
    // assertEquals(HttpStatus.OK,response.getStatusCode());
    // assertEquals(product,response.getBody());
    // }

    @Test
    public void testUpdateProduct() throws IOException { // create may throw IOException
        // Setup
        Product product = new Product(100, "Raddest Food", "super cool-est food", 10.0f, 3, "");

        // when create is called, return true simulating successful
        // creation and save
        when(mockDAO.update(product)).thenReturn(product);
        when(mockDAO.get(product.getId())).thenReturn(product);
        // Invoke
        ResponseEntity<Product> response = productController.update(product);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    public void testUpdateProductFailed() throws IOException { // update may throw IOException
        // Setup
        Product product = new Product(99, "super food", "super cool food", 10.0f, 3, "");

        // when update is called, return true simulating successful
        // update and save
        when(mockDAO.update(product)).thenReturn(null);

        // Invoke
        ResponseEntity<Product> response = productController.update(product);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateProductHandleException() throws IOException { // update may throw IOException
        // Setup
        Product product = new Product(99, "vegan food", "no shmeat", 10.0f, 3, "");

        // When update is called on the Mock Product DAO, throw an IOException
        doThrow(new IOException()).when(mockDAO).update(product);

        // Invoke
        ResponseEntity<Product> response = productController.update(product);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    // @Test
    // public void testUpdateInvalidProduct() throws IOException { // create may
    // throw IOException
    // // Setup
    // User user = new Product(99,"Coolest user", "coolDude", "password", true);

    // // Invoke
    // ResponseEntity<Product> response = productController.update(user);

    // // Analyze
    // assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    // }

    @Test
    public void testGetproducts() throws IOException { // getAll may throw IOException
        // Setup
        Product[] products = new Product[2];
        products[0] = new Product(99, "super food", "super cool food", 10.0f, 3, "");
        products[1] = new Product(100, "Junk food", "Greasy but good", 10.0f, 3, "");

        // When getAll is called return the products created above
        when(mockDAO.getAll()).thenReturn(products);

        // Invoke
        ResponseEntity<Product[]> response = productController.getAll();

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products, response.getBody());
    }

    @Test
    public void testGetproductsHandleException() throws IOException { // getAll may throw IOException
        // Setup
        // When getAll is called on the Mock Product DAO, throw an IOException
        doThrow(new IOException()).when(mockDAO).getAll();

        // Invoke
        ResponseEntity<Product[]> response = productController.getAll();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testSearchproducts() throws IOException { // search may throw IOException
        // Setup
        String searchString = "la";
        Product[] products = new Product[2];
        products[0] = new Product(99, "super food", "super cool food", 10.0f, 3, "");
        products[1] = new Product(100, "Junk food", "Greasy but good", 10.0f, 3, "");

        // When search is called with the search string, return the two
        /// products above
        when(mockDAO.search(searchString)).thenReturn(products);

        // Invoke
        ResponseEntity<Product[]> response = productController.search(searchString);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products, response.getBody());
    }

    @Test
    public void testSearchproductsHandleException() throws IOException { // search may throw IOException
        // Setup
        String searchString = "an";
        // When create is called on the Mock Product DAO, throw an IOException
        doThrow(new IOException()).when(mockDAO).search(searchString);

        // Invoke
        ResponseEntity<Product[]> response = productController.search(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteProduct() throws IOException { // delete may throw IOException
        // Setup
        int ProductId = 99;
        // when delete is called return true, simulating successful deletion
        when(mockDAO.delete(ProductId)).thenReturn(true);

        // Invoke
        ResponseEntity<Product> response = productController.delete(ProductId);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteProductNotFound() throws IOException { // delete may throw IOException
        // Setup
        int ProductId = 99;
        // when delete is called return false, simulating failed deletion
        when(mockDAO.delete(ProductId)).thenReturn(false);

        // Invoke
        ResponseEntity<Product> response = productController.delete(ProductId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteProductHandleException() throws IOException { // delete may throw IOException
        // Setup
        int ProductId = 99;
        // When delete is called on the Mock Product DAO, throw an IOException
        doThrow(new IOException()).when(mockDAO).delete(ProductId);

        // Invoke
        ResponseEntity<Product> response = productController.delete(ProductId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

}
