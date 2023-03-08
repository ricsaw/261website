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
import com.products.api.productsapi.model.Address;
import com.products.api.productsapi.model.Model;

/**
 * Test for the AddressFileDAO class.
 * @author Antar Narayan Chowdhury
 */
@Tag("Persistence-tier")
public class AddressFileDAOTest {
    AddressFileDAO testDAO;
    Address[] testAddresses;
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
        testAddresses = new Address[3];
        testAddresses[0] = new Address(99, "Antar", "Chowdhury", "123 Main St", "Anytown", "NY", 12345, "12345", false);
        testAddresses[1] = new Address(100, "Antares", "Cavduri", "123 eeMain St", "Anyteown", "NYC", 122245, "67899", false);
        testAddresses[2] = new Address(101, "Elon", "Musk", "123 Main St", "Anytown", "NY", 12345, "12345", true);
        
        when(testMap.readValue(new File("test.txt"),Address[].class))
                    .thenReturn(testAddresses);

        testDAO = new AddressFileDAO("test.txt", testMap);


    }

    /**
     * Test for getAll().
     * @throws IOException
     */
    @Test
    public void testGetAll() throws IOException
    {
        // Create address list
        Address[] addresss = testDAO.getAll();

        // Assert
        assertEquals(addresss.length, testAddresses.length);
        for (int i = 0; i < testAddresses.length;++i)
            assertEquals(addresss[i],testAddresses[i]);
    }

    /**
     * Test for search().
     * @throws IOException
     */
    @Test
    public void testSearch() throws IOException
    {
        // Create model list
        Model[] models = testDAO.search("Antar");

        // Assert
        assertEquals(models.length, 2);
        assertEquals(models[0],testAddresses[0]);
        assertEquals(models[1],testAddresses[1]);
    }

     /**
     * Test for get().
     * @throws IOException
     */
    @Test
    public void testGet() throws IOException
    {
        // Get address
        Address address = testDAO.get(100);

        // Assert
        assertEquals(address, testAddresses[1]);
    }


 /**
     * Test for create().
     * @throws IOException
     */
    @Test
    public void testCreate() throws IOException
    {
        // Create new address

        Address address = new Address(102, "Martinez", "Martinez", "RIT GCCIS", "ROC", "NEWYORK MONREO", 14614, "585588555", true);

        // Test create()
        Model result = assertDoesNotThrow(() -> testDAO.create(address), "Exception thrown");

        // Assert
        assertNotNull(result);
        Address actual = testDAO.get(address.getId());
        assertEquals(actual.getId(), address.getId());
        assertEquals(actual.getName(), address.getName());
        assertEquals(actual.getLastname(), address.getLastname());
        assertEquals(actual.getAddress(), address.getAddress());
        assertEquals(actual.getCity(), address.getCity());
        assertEquals(actual.getState(), address.getState());
        assertEquals(actual.getZip(), address.getZip());
        assertEquals(actual.getPhone(), address.getPhone());
        assertEquals(actual.getDefaultOrNot(), address.getDefaultOrNot());
    }

    /**
     * Test for update().
     * @throws IOException
     */
    @Test
    public void testUpdate() throws IOException
    {
        // Create new address
        Address address = new Address(100, "Aidan", "Unknown", "1123 Main St", "Anyteown", "NYCity", 122245, "67899", false);


        // Test update()
        Model result = assertDoesNotThrow(() -> testDAO.update(address), "Exception thrown");

        // Assert
        assertNotNull(result);
        Address actual = testDAO.get(address.getId());
        assertEquals(actual, address);
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
        assertEquals(testDAO.addresses.size(),testAddresses.length-1);
    }

    /**
     * Test for an exception thrown from save().
     * @throws IOException
     */
    @Test
    public void testSaveException() throws IOException
    {
        doThrow(new IOException()).when(testMap).writeValue(any(File.class),any(Address[].class));

        Address address = new Address(104, "Aidadn", "Unkndown", "1123d Main St", "Anytedown", "NYCdity", 1223245, "674899", true);
        
        assertThrows(IOException.class, () -> testDAO.create(address), "Exception not thrown");
    }

    /**
     * Test for getting a non-existent address.
     * @throws IOException
     */
    @Test
    public void testGetUnknownAddress() throws IOException
    {
        // Try to get address
        Address address = testDAO.get(98);

        // Assert
        assertEquals(address, null);
    }

    /**
     * Test for deleting a non-existent address.
     */
    @Test
    public void testDeleteUnknownAddress() 
    {
        // Create test
        boolean result = assertDoesNotThrow(() -> testDAO.delete(98), "Unexpected exception thrown");

        // Assert
        assertEquals(result, false);
        assertEquals(testDAO.addresses.size(),testAddresses.length);
    }

    /**
     * Test for updating a non-existent address.
     */
    @Test
    public void testUpdateUnknownAddress() 
    {
        // Create address
        Address address = new Address(98, "Aidadn", "Unkndown", "1123d Main St", "Anytedown", "NYCdity", 1223245, "674899", true);

        // Test update() for unknown address
        Model result = assertDoesNotThrow(() -> testDAO.update(address), "Unexpected exception thrown");

        // Assert
        assertNull(result);
    }

    /**
     * Test for possible exception caused by address constructor.
     * @throws IOException
     */
    @Test
    public void testConstructorException() throws IOException 
    {
        // Create ObjectMapper
        ObjectMapper testMap = mock(ObjectMapper.class);

        doThrow(new IOException()).when(testMap).readValue(new File("test.txt"), Address[].class);

        // Assert
        assertThrows(IOException.class, () -> new AddressFileDAO("test.txt", testMap), "Exception not thrown");
    }











    
}
