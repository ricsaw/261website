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
import com.products.api.productsapi.persistence.DAO;
import com.products.api.productsapi.persistence.ProductFileDAO;
import com.products.api.productsapi.model.Product;
import com.products.api.productsapi.model.Model;


/**
 * Handles the REST API requests for products
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author Aidan, James and Antar
 */
@RestController
@RequestMapping("products")
public class ProductController implements Controller<Product> {
    private static final Logger LOG = Logger.getLogger(Product.class.getName());
    private ProductFileDAO pDAO;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param DAO The {@link DAO Product Data Access Object} to perform CRUD operationsF
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public ProductController(ProductFileDAO pDAO) {
        this.pDAO = pDAO;
    }

    /**
     * Responds to the GET request for a {@linkplain Product product} for the given id
     * 
     * @param id The id used to locate the {@link Product product}
     * 
     * @return ResponseEntity with {@link Product product} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> get(@PathVariable int id) {
        LOG.info("GET /products/" + id);
        try {
            Product product = (Product) pDAO.get(id);
            if (product != null)
                return new ResponseEntity<Product>(product,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Product products}
     * 
     * @return ResponseEntity with array of {@link Product product} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Product[]> getAll() {
        LOG.info("GET /products");

        // Replace below with your implementation
        try
        {
            Product[] products = pDAO.getAll();
            return new ResponseEntity<Product[]>(products, HttpStatus.OK);
        }
        catch(IOException e){
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Product products} whose name contains
     * the text in name
     * 
     * @param name The name parameter which contains the text used to find the {@link Product product}
     * 
     * @return ResponseEntity with array of {@link Product product} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * <p>
     * Example: Find all products that contain the text "ma"
     * GET http://localhost:8080/products/?name=ma
     */
    @GetMapping("/")
    public ResponseEntity<Product[]> search(@RequestParam String text) {
        LOG.info("GET /products/?name="+text);

        // Replace below with your implementation
        try
        {
            Product[] products = (Product[]) pDAO.search(text);
            return new ResponseEntity<Product[]>(products, HttpStatus.OK);
        }
        catch(IOException e)
        {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Product product} with the provided product object
     * 
     * @param product - The {@link Product product} to create
     * 
     * @return ResponseEntity with created {@link Product product} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Product product} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @Override
    @PostMapping("/add")
    public ResponseEntity<Product> create(@RequestBody Product product) {
        
        LOG.info("POST /products/add " + product);

        // Replace below with your implementation
        try
        {
            if (!(product instanceof Product)) {       // Ensure model is an instance of Product
                throw new IOException("Not instance of Product");
            }
            Model[] conflicts = pDAO.search(((Product)product).getName());

            if(conflicts != null && conflicts.length != 0){
                // Loop through conflicts and see if there are any duplicate product names
                for(Model i : conflicts){
                    if(((Product)i).getName().equalsIgnoreCase(((Product)product).getName())){
                        return new ResponseEntity<>(HttpStatus.CONFLICT);
                    }
                }
            }
            
            Product new_product = (Product) pDAO.create((Product) product);
            if (new_product != null){
                return new ResponseEntity<Product>(new_product, HttpStatus.CREATED);
            }else
            return new ResponseEntity<>(HttpStatus.CONFLICT);
            
            
        }
        catch(IOException e)
        {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain Product product} with the provided {@linkplain Product product} object, if it exists
     * 
     * @param product The {@link Product product} to update
     * 
     * @return ResponseEntity with updated {@link Product product} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @Override
    @PutMapping("/edit")
    public ResponseEntity<Product> update(@RequestBody Product product) {
        LOG.info("PUT /products/edit " + product);

        // Replace below with your implementation
        try
        {
            if (!(product instanceof Product)) {       // Ensure model is an instance of Product
                throw new IOException("Not instance of Product");
            }

            //int id = product.getId();
            //Product new_product = pDAO.get(id);
            Product new_product = pDAO.update(product);

            if(new_product != null)
            {
                return new ResponseEntity<Product>(new_product, HttpStatus.OK);
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
     * Deletes a {@linkplain Product product} with the given id
     * 
     * @param id The id of the {@link Product product} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Product> delete(@PathVariable int id) {
        LOG.info("DELETE /products/" + id);

        // Replace below with your implementation
        try
        {
            if(pDAO.delete(id))
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

}
