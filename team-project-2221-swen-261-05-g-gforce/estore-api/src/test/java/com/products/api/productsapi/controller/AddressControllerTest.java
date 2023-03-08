package com.products.api.productsapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.products.api.productsapi.model.Address;
import com.products.api.productsapi.persistence.AddressFileDAO;

/**
 * This class is used to test the AddressController class.
 * 
 * @author Antar Narayan Chowdhury
 */
@Tag("Controller-tier")
public class AddressControllerTest {
    private AddressController addressController;
    // private AddressFileDAO addressFileDAO;
    private AddressFileDAO mockDAO;

    /**
     * Before each test, create a new AddressController object and inject
     * a mock Address DAO
     */
    @BeforeEach
    public void setupAddressController() {
        mockDAO = mock(AddressFileDAO.class);
        addressController = new AddressController(mockDAO);
    }

    @Test
    public void testGetAddress() throws IOException {
        // Setup
        Address address = new Address(99, "Antar", "Chowdhury", "123 Main St", "Anytown", "NY", 12345, "12345", false);
        when(mockDAO.get(99)).thenReturn(address);

        // When the same id is passed in, our mock Address DAO will return the Address
        // object
        when(mockDAO.get(address.getId())).thenReturn(address);

        // Invoke
        ResponseEntity<Address> response = addressController.get(address.getId());

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(address, response.getBody());
    }

    @Test
    public void testGetAddressNotFound() throws Exception { // create may throw IOException
        // Setup
        int addressID = 99;
        // When the same id is passed in, our mock Address DAO will return null,
        // simulating
        // no Address found
        when(mockDAO.get(addressID)).thenReturn(null);

        // Invoke
        ResponseEntity<Address> response = addressController.get(addressID);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetAddressHandleException() throws Exception { // create may throw IOException
        // Setup
        int addressId = 99;
        // When get is called on the Mock Address DAO, throw an IOException
        doThrow(new IOException()).when(mockDAO).get(addressId);

        // Invoke
        ResponseEntity<Address> response = addressController.get(addressId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCreateAddress() throws IOException { // create may throw IOException
        // Setup
        Address address = new Address(99, "Antar", "Chowdhury", "123 Main St", "Anytown", "NY", 12345, "12345", false);
        when(mockDAO.create(address)).thenReturn(address);

        // when create is called, return true simulating successful
        // creation and save
        when(mockDAO.create(address)).thenReturn(address);

        // Invoke
        ResponseEntity<Address> response = addressController.create(address);

        // Analyze
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(address, response.getBody());
    }

    @Test
    public void testCreateAddressHandleException() throws IOException { // create may throw IOException
        // Setup
        Address address = new Address(99, "Antar", "Chowdhury", "123 Main St", "Anytown", "NY", 12345, "12345", false);
        // When create is called on the Mock Address DAO, throw an IOException
        doThrow(new IOException()).when(mockDAO).create(address);

        // Invoke
        ResponseEntity<Address> response = addressController.create(address);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testUpdateAddress() throws IOException { // create may throw IOException
        // Setup
        Address address = new Address(99, "Antar", "Chowdhury", "123 Main St", "Anytown", "NY", 12345, "12345", false);
        // when create is called, return true simulating successful
        // creation and save
        when(mockDAO.update(address)).thenReturn(address);
        when(mockDAO.get(address.getId())).thenReturn(address);
        // Invoke
        ResponseEntity<Address> response = addressController.update(address);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(address, response.getBody());
    }

    @Test
    public void testUpdateAddressFailed() throws IOException { // update may throw IOException
        // Setup
        Address address = new Address(99, "Antar", "Chowdhury", "123 Main St", "Anytown", "NY", 12345, "12345", false);
        // when update is called, return true simulating successful
        // update and save
        when(mockDAO.update(address)).thenReturn(null);

        // Invoke
        ResponseEntity<Address> response = addressController.update(address);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateAddressHandleException() throws IOException { // update may throw IOException
        // Setup
        Address address = new Address(99, "Antar", "Chowdhury", "123 Main St", "Anytown", "NY", 12345, "12345", false);

        // When update is called on the Mock Product DAO, throw an IOException
        doThrow(new IOException()).when(mockDAO).update(address);

        // Invoke
        ResponseEntity<Address> response = addressController.update(address);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetAddresses() throws IOException { // getAll may throw IOException
        // Setup
        Address[] addresses = new Address[2];
        addresses[0] = new Address(99, "Antar", "Chowdhury", "123 Main St", "Anytown", "NY", 12345, "12345", false);
        addresses[1] = new Address(100, "Antares", "Cavduri", "123 eeMain St", "Anyteown", "NYC", 122245, "67899",
                false);

        // When getAll is called return the addresss created above
        when(mockDAO.getAll()).thenReturn(addresses);

        // Invoke
        ResponseEntity<Address[]> response = addressController.getAll();

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(addresses, response.getBody());
    }

    @Test
    public void testGetaddresssHandleException() throws IOException { // getAll may throw IOException
        // Setup
        // When getAll is called on the Mock Address DAO, throw an IOException
        doThrow(new IOException()).when(mockDAO).getAll();

        // Invoke
        ResponseEntity<Address[]> response = addressController.getAll();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testSearchAddresss() throws IOException { // search may throw IOException
        // Setup
        String searchString = "la";
        Address[] addresses = new Address[2];
        addresses[0] = new Address(99, "Antar", "Chowdhury", "123 Main St", "Anytown", "NY", 12345, "12345", false);
        addresses[1] = new Address(100, "Antares", "Cavduri", "123 eeMain St", "Anyteown", "NYC", 122245, "67899",
                false);

        // When search is called with the search string, return the two
        /// addresss above
        when(mockDAO.search(searchString)).thenReturn(addresses);

        // Invoke
        ResponseEntity<Address[]> response = addressController.search(searchString);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(addresses, response.getBody());
    }

    @Test
    public void testSearchAddresssHandleException() throws IOException { // search may throw IOException
        // Setup
        String searchString = "an";
        // When create is called on the Mock Address DAO, throw an IOException
        doThrow(new IOException()).when(mockDAO).search(searchString);

        // Invoke
        ResponseEntity<Address[]> response = addressController.search(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteAddress() throws IOException { // delete may throw IOException
        // Setup
        int addressId = 99;
        // when delete is called return true, simulating successful deletion
        when(mockDAO.delete(addressId)).thenReturn(true);

        // Invoke
        ResponseEntity<Address> response = addressController.delete(addressId);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteAddressNotFound() throws IOException { // delete may throw IOException
        // Setup
        int addressId = 99;
        // when delete is called return false, simulating failed deletion
        when(mockDAO.delete(addressId)).thenReturn(false);

        // Invoke
        ResponseEntity<Address> response = addressController.delete(addressId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteAddressHandleException() throws IOException { // delete may throw IOException
        // Setup
        int addressId = 99;
        // When delete is called on the Mock Address DAO, throw an IOException
        doThrow(new IOException()).when(mockDAO).delete(addressId);

        // Invoke
        ResponseEntity<Address> response = addressController.delete(addressId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

}
