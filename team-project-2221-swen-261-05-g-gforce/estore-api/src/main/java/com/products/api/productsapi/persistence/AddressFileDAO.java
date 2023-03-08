package com.products.api.productsapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.products.api.productsapi.model.Address;


/**
 * Handles the REST API requests for address
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author Antar Narayan Chowdhury
 */
@Component
public class AddressFileDAO implements DAO<Address>{
    Map<Integer,Address> addresses;   // Provides a local cache of the address objects
                                // so that we don't need to read from the file
                                // each time
    private ObjectMapper objectMapper;  // Provides conversion between Address
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;  // The next Id to assign to a new address
    private String filename;    // Filename to read from and write to

    /**
     * Creates a Address File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public AddressFileDAO(@Value("${address.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        load();  // load the addresss from the file
    }

    /**
     * Generates the next id for a new {@linkplain Address address}
     * 
     * @return The next id
     */
    public synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain Address addresss} from the tree map
     * 
     * @return  The array of {@link Address addresss}, may be empty
     */
    private Address[] getAddresssArray() {
        return getAddresssArray(null);
    }

    /**
     * Generates an array of {@linkplain Address addresss} from the tree map for any
     * {@linkplain Address addresss} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Address addresss}
     * in the tree map
     * 
     * @return  The array of {@link Address addresss}, may be empty
     */
    private Address[] getAddresssArray(String containsText) { // if containsText == null, no filter
        ArrayList<Address> addressArrayList = new ArrayList<>();

        for (Address address : addresses.values()) {
            if (containsText == null || address.getName().contains(containsText) || address.getLastname().contains(containsText)  || address.getAddress().contains(containsText)
                    || address.getCity().contains(containsText) || address.getState().contains(containsText)) {
                addressArrayList.add(address);
            }
        }

        Address[] addressArray = new Address[addressArrayList.size()];
        addressArrayList.toArray(addressArray);
        return addressArray;
    }

    /**
     * Saves the {@linkplain Address addresss} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Address addresss} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Address[] addressArray = getAddresssArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),addressArray);
        return true;
    }

    /**
     * Loads {@linkplain Address addresss} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        addresses = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of addresss
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Address[] addressArray = objectMapper.readValue(new File(filename),Address[].class);

        // Add each address to the tree map and keep track of the greatest id
        for (Address address : addressArray) {
            addresses.put(address.getId(),address);
            if (address.getId() > nextId)
                nextId = address.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }
    
    /**
    ** {@inheritDoc}
    *@return a list of addresss 
     */
    @Override
    public Address[] getAll() throws IOException {
        synchronized(addresses) {
            return getAddresssArray();
        }
    }
    
    /**
    ** {@inheritDoc}
    *@return return a list of address which satisfies the condition 
     */
    @Override
    public Address[] search(String containsText) throws IOException {
        synchronized(addresses) {
            return getAddresssArray(containsText);
        }
    }
    
    /**
    ** {@inheritDoc}
     * @return the address which has the provided id
     */
    @Override
    public Address get(int id) throws IOException {
        synchronized(addresses) {
            if (addresses.containsKey(id))
                return addresses.get(id);
            else
                return null;
        }
    }
    
    /**
    ** {@inheritDoc}
    * @return return created address 
     */
    @Override
    public Address create(Address model) throws IOException {
        synchronized(addresses) {
            // Throws IO exception if address with the same name already exists
            Address address = model; 
        
            // We create a new address object because the id field is immutable
            // and we need to assign the next unique id
            Address newAddress = new Address(nextId(), address.getName(),  address.getLastname(), address.getAddress(), address.getCity(), 
                                             address.getState(), address.getZip(), address.getPhone(), address.getDefaultOrNot());
            addresses.put(newAddress.getId(),newAddress);
            save(); // may throw an IOException
            return newAddress;
        }
    }

    /**
    ** {@inheritDoc}
    * @return the updated address
     */
    @Override
    public Address update(Address model) throws IOException {
        synchronized(addresses) {
            Address address = model; 
            if (addresses.containsKey(address.getId()) == false)
                return null;  // address does not exist   
            addresses.put(address.getId(),address);
            save(); // may throw an IOException
            return address;
        }
    }   
    /**
    ** {@inheritDoc}
    * @return a boolean after the address deleted
     */
    @Override
    public boolean delete(int id) throws IOException {
        synchronized(addresses) {
            if (addresses.containsKey(id)) {
                addresses.remove(id);
                return save();
            }
            else
                return false;
        }
    }

}