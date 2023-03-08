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
import com.products.api.productsapi.persistence.OrderFileDAO;
import com.products.api.productsapi.model.Order;


/**
 * Handles the REST API requests for orders
 * 
 * @author Aidan Collins
 */
@RestController
@RequestMapping("orders")
public class OrderController implements Controller<Order> {
    private static final Logger LOG = Logger.getLogger(Order.class.getName());
    private OrderFileDAO oDAO;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param oDAO The {@link DAO Order Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public OrderController(OrderFileDAO oDAO) {
        this.oDAO = oDAO;
    }

    /**
     * Responds to the GET request for a {@linkplain Order order} for the given id
     * 
     * @param id The id used to locate the {@link Order order}
     * 
     * @return ResponseEntity with {@link Order order} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Order> get(@PathVariable int id) {
        LOG.info("GET /orders/" + id);
        try {
            Order order = (Order) oDAO.get(id);
            if (order != null)
                return new ResponseEntity<Order>(order,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Order orders}
     * 
     * @return ResponseEntity with array of {@link Order orders} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Order[]> getAll() {
        LOG.info("GET /orders");
        try
        {
            Order[] orders = oDAO.getAll();
            return new ResponseEntity<Order[]>(orders, HttpStatus.OK);
        }
        catch(IOException e){
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Order orders} who contain a product with the
     * name that contains the given text
     * 
     * @param name The name parameter which contains the text used to find the {@link Order order}
     * 
     * @return ResponseEntity with array of {@link Order order} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * <p>
     * Example: Find all orders that contain a product with the name containing the text "ma"
     * GET http://localhost:8080/orders/?name=ma
     */
    @GetMapping("/")
    public ResponseEntity<Order[]> search(@RequestParam String text) {
        LOG.info("GET /orders/?name="+text);
        try
        {
            Order[] orders = (Order[]) oDAO.search(text);
            return new ResponseEntity<Order[]>(orders, HttpStatus.OK);
        }
        catch(IOException e)
        {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Order order} with the provided order object
     * 
     * @param order - The {@link Order order} to create
     * 
     * @return ResponseEntity with created {@link Order order} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @Override
    @PostMapping("/add")
    public ResponseEntity<Order> create(@RequestBody Order order) {
        
        LOG.info("POST /orders/add " + order);
        try
        {
            if (!(order instanceof Order)) {       // Ensure model is an instance of Order
                throw new IOException("Not instance of Order");
            }
            
            Order new_order = (Order) oDAO.create((Order) order);
            if (new_order != null){
                return new ResponseEntity<Order>(new_order, HttpStatus.CREATED);
            } else
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch(IOException e)
        {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain Order order} with the provided {@linkplain Order order} object, if it exists
     *
     * @param order The {@link Order order} to update
     * 
     * @return ResponseEntity with updated {@link Order order} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @Override
    @PutMapping("/edit")
    public ResponseEntity<Order> update(@RequestBody Order order) {
        LOG.info("PUT /orders/edit " + order);
        try
        {
            if (!(order instanceof Order)) {       // Ensure model is an instance of Order
                throw new IOException("Not instance of Order");
            }

            //int id = order.getId();
            //Order new_order = oDAO.get(id);
            Order new_order = oDAO.update(order);

            if(new_order != null)
            {
                return new ResponseEntity<Order>(new_order, HttpStatus.OK);
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
     * Deletes a {@linkplain Order order} with the given id
     * 
     * @param id The id of the {@link Order order} to delete
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Order> delete(@PathVariable int id) {
        LOG.info("DELETE /orders/" + id);
        try
        {
            if(oDAO.delete(id))
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
