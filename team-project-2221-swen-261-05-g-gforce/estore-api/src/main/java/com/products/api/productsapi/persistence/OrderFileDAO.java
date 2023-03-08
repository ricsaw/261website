package com.products.api.productsapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.products.api.productsapi.model.Order;
import com.products.api.productsapi.model.Product;

/**
 * Implements the functionality for JSON file-based peristance for Orders
 * 
 * @author Aidan Collins
 */
@Component
public class OrderFileDAO implements DAO<Order> {
    Map<Integer,Order> orders;   // Provides a local cache of the order objects
                                // so that we don't need to read from the file
                                // each time
    private ObjectMapper objectMapper;  // Provides conversion between Order
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;  // The next Id to assign to a new order
    private String filename;    // Filename to read from and write to

    /**
     * Creates a Order File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public OrderFileDAO(@Value("${orders.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        load();  // load the products from the file
    }

    /**
     * Generates the next id for a new {@linkplain Order order}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain Order orders} from the tree map
     * 
     * @return  The array of {@link Order orders}, may be empty
     */
    private Order[] getOrdersArray() {
        return getOrdersArray(null);
    }

    /**
     * Generates an array of {@linkplain Order orders} from the tree map for any
     * {@linkplain Order orders} that contains the a product that contains the given text containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Order orders}
     * in the tree map
     * 
     * @return  The array of {@link Order orders}, may be empty
     */
    private Order[] getOrdersArray(String containsText) { // if containsText == null, no filter
        ArrayList<Order> orderArrayList = new ArrayList<>();

        for (Order order : orders.values()) {
            if (containsText == null) {
                orderArrayList.add(order);
                continue;
            } 
            for (Product product : order.getProducts()) {
                if (product.getName().contains(containsText)) {
                    orderArrayList.add(order);
                    break;
                }
            }
        }

        Order[] orderArray = new Order[orderArrayList.size()];
        orderArrayList.toArray(orderArray);
        return orderArray;
    }

    /**
     * Saves the {@linkplain Order orders} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Order orders} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Order[] orderArray = getOrdersArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),orderArray);
        return true;
    }

    /**
     * Loads {@linkplain Order orders} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        orders = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of products
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Order[] orderArray = objectMapper.readValue(new File(filename),Order[].class);

        // Add each order to the tree map and keep track of the greatest id
        for (Order order : orderArray) {
            orders.put(order.getId(),order);
            if (order.getId() > nextId)
                nextId = order.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    
    /**
    * {@inheritDoc}
    * 
    * @return The list of the Orders
    */
    @Override
    public Order[] getAll() throws IOException {
        synchronized(orders) {
            return getOrdersArray();
        }
    }

    /**
    ** {@inheritDoc}
    * @return Return the order list which are satisfies the condtion
     */  
    @Override
    public
    Order[] search(String containsText) throws IOException {
        synchronized(orders){
            Order[] sModels = getOrdersArray(containsText); 
            return sModels;
        }
    }

    /**
    ** {@inheritDoc}
     * @return the order which satisifies the provided number
     */
    @Override
    public Order get(int id) throws IOException {
        synchronized(orders) {
            if (orders.containsKey(id))
                return orders.get(id);
            else
                return null;
        }
    }

    /**
    ** {@inheritDoc}
    * @return created order 
     */   
    @Override
    public Order create(Order model) throws IOException {
        synchronized(orders) {
            Order mOrder = (Order) model; 
            // We create a new product object because the id field is immutable
            // and we need to assign the next unique id
            Order newOrder = new Order(nextId(), mOrder.getUserId(), mOrder.getCardNumber(), mOrder.getExpDate(), mOrder.getSecurityCode(), mOrder.getProducts(), mOrder.getAddress());
            orders.put(newOrder.getId(),newOrder);
            save(); // may throw an IOException
            return newOrder;
        }
    }

    /**
    ** {@inheritDoc}
    * @return updated order
     */
    @Override
    public Order update(Order model) throws IOException {
        synchronized(orders) {
            Order order = (Order) model; 
            if (orders.containsKey(order.getId()) == false)
                return null;  // order does not exist   
            orders.put(order.getId(),order);
            save(); // may throw an IOException
            return order;
        }
    }

    /**
    ** {@inheritDoc}
    * @return a boolean after deleting the order 
     */
    @Override
    public boolean delete(int id) throws IOException {
        synchronized(orders) {
            if (orders.containsKey(id)) {
                orders.remove(id);
                return save();
            }
            else
                return false;
        }
    }
}
