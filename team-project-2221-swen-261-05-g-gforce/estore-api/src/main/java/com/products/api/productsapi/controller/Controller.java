package com.products.api.productsapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Interface to be implemented by Controller classes to allow the REST API requests
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author Aidan Collins
 */
// @RestController
// @RequestMapping("products")
public interface Controller<E> {

    /**
     * Responds to the GET request for a given id
     * 
     * @param id The id used to locate the object
     */
    // @GetMapping("/{id}")
    public ResponseEntity<E> get(@PathVariable int id);

    /**
     * Responds to the GET request for all objects
     */
    // @GetMapping("")
    public ResponseEntity<E[]> getAll();

    /**
     * Responds to the GET request for all objects associated with a given substring
     * 
     * @param text The parameter which contains the text used to find the ibjects
     */
    // @GetMapping("/")
    public ResponseEntity<E[]> search(@RequestParam String text);

    /**
     * Creates an object with the provided product object
     * 
     * @param model - The object to create
     */
    // @PostMapping("")
    public ResponseEntity<E> create(@RequestBody E model);

    /**
     * Updates the {@linkplain Product product} with the provided {@linkplain Product product} object, if it exists
     * 
     * @param model The object to update
     */
    // @PutMapping("")
    public ResponseEntity<E> update(@RequestBody E model);

    /**
     * Deletes an object with the given id
     * 
     * @param id The id of the object to deleted
     */
    // @DeleteMapping("/{id}")
    public ResponseEntity<E> delete(@PathVariable int id);
}
