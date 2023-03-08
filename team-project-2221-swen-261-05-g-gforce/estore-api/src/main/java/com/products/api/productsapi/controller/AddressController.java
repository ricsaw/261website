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

import com.products.api.productsapi.model.Address;
import com.products.api.productsapi.persistence.AddressFileDAO;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("address")
public class AddressController implements Controller<Address>{
    private static final Logger LOG = Logger.getLogger(Address.class.getName());   
    private AddressFileDAO addressFileDAO;

/**
     * Creates a REST API controller to reponds to requests
     * 
     * @param addressFileDAO The {@link AddressFileDAO Address Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public AddressController(AddressFileDAO addressFileDAO) {
        this.addressFileDAO = addressFileDAO;
    }

    /**
     * Responds to the GET request for a {@linkplain Address address} for the given id
     * 
     * @param id The id used to locate the {@link Address address}
     * 
     * @return ResponseEntity with {@link Address address} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Address> get(@PathVariable int id) {
        LOG.info("GET /address/" + id);
        try {
            Address address = addressFileDAO.get(id);
            if (address != null)
                return new ResponseEntity<Address>(address,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Responds to the GET request for all {@linkplain Address addresss}
     * 
     * @return ResponseEntity with array of {@link Address address} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Address[]> getAll() {
        LOG.info("GET /address");

        // Replace below with your implementation
        try
        {
            Address[] addresss = (Address[]) addressFileDAO.getAll();
            return new ResponseEntity<Address[]>(addresss, HttpStatus.OK);
        }
        catch(IOException e)
        {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Address addresss} whose name contains
     * the text in addressname
     * 
     * @param text The parameter which contains the text used to find the {@link Address address}
     * 
     * @return ResponseEntity with array of {@link Address address} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * <p>
     * Example: Find all addresss that contain the text "ma"
     * GET http://localhost:8080/addresss/?addressname=ma
     */
    @GetMapping("/search")
    public ResponseEntity<Address[]> search(@RequestParam String text) {
        LOG.info("GET /address/search/?addressname="+text);

        // Replace below with your implementation
        try
        {
            Address[] addresss = (Address[]) addressFileDAO.search(text);
            return new ResponseEntity<Address[]>(addresss, HttpStatus.OK);
        }
        catch(IOException e)
        {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Address address} with the provided address object
     * 
     * @param model - The {@link Address address} to create
     * 
     * @return ResponseEntity with created {@link Address address} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Address address} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @Override
    @PostMapping("/add")
    public ResponseEntity<Address> create(@RequestBody Address address) {
        LOG.info("POST /address/add " + address);
        
        try
        {
            Address createdAddress = (Address) addressFileDAO.create(address);
            return new ResponseEntity<Address>(createdAddress, HttpStatus.CREATED);
        }
        catch(IOException e)
        {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

 /**
     * Updates the {@linkplain Address address} with the provided {@linkplain Address address} object, if it exists
     * 
     * @param model The {@link Address address} to update
     * 
     * @return ResponseEntity with updated {@link Address address} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */

    @PutMapping("/update")
    public ResponseEntity<Address> update(@RequestBody Address address) {
        LOG.info("PUT /address/update" + address);

        
        try
        {
            //int id = address.getId();
            //Address new_address = addressFileDAO.get(id);
            Address new_address = (Address) addressFileDAO.update(address);

            if(new_address != null)
            {
                return new ResponseEntity<Address>(new_address, HttpStatus.OK);
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
     * Deletes a {@linkplain Address address} with the given id
     * 
     * @param id The id of the {@link Address address} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Address> delete(@PathVariable int id) {
        LOG.info("DELETE /address/delete/" + id);

        // Replace below with your implementation
        try
        {
            if(addressFileDAO.delete(id))
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
