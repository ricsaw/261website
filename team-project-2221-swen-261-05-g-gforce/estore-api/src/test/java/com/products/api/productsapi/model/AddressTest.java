package com.products.api.productsapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/*
 * Test class for Address
 * @author: Antar Narayan Chowdhury
 */
@Tag("Model-tier")
public class AddressTest {
    // Pakage private for test
    static final String STRING_FORMAT = "Address [id=%d, name=%s, lastname=%s, address=%s, city=%s, state=%s, zip=%d , phone=%s, defaultOrNot=%s]";

    @Test
    public void TestConstructor() {
        // Setup
        int id = 4;
        String name = "name";
        String lastname = "lastname";
        String address = "address";
        String city = "city";
        String state = "state";
        int zip = 12345;
        String phone = "123456789";
        boolean defaultOrNot = true;

        // Invoke
        Address addressObject = new Address(id, name, lastname, address, city, state, zip, phone, defaultOrNot);

        // Analyze
        assertEquals(id, addressObject.getId());
        assertEquals(name, addressObject.getName());
        assertEquals(lastname, addressObject.getLastname());
        assertEquals(address, addressObject.getAddress());
        assertEquals(city, addressObject.getCity());
        assertEquals(state, addressObject.getState());
        assertEquals(zip, addressObject.getZip());
        assertEquals(phone, addressObject.getPhone());
        assertEquals(defaultOrNot, addressObject.getDefaultOrNot());
    }

    @Test
    public void TestId() {
        // Setup
        int id = 4;
        String name = "name";
        String lastname = "lastname";
        String address = "address";
        String city = "city";
        String state = "state";
        int zip = 12345;
        String phone = "123456789";
        boolean defaultOrNot = true;
        Address addressObject = new Address(id, name, lastname, address, city, state, zip, phone, defaultOrNot);

        int expectedId = 99;

        // Invoke
        addressObject.setId(expectedId);

        // Analyze
        assertEquals(expectedId, addressObject.getId());

    }

    @Test
    public void TestName() {
        // Setup
        int id = 4;
        String name = "name";
        String lastname = "lastname";
        String address = "address";
        String city = "city";
        String state = "state";
        int zip = 12345;
        String phone = "123456789";
        boolean defaultOrNot = true;
        Address addressObject = new Address(id, name, lastname, address, city, state, zip, phone, defaultOrNot);

        String expectedName = "Antar";

        // Invoke
        addressObject.setName(expectedName);

        // Analyze
        assertEquals(expectedName, addressObject.getName());

    }

    @Test
    public void TestLastName() {
        // Setup
        int id = 4;
        String name = "name";
        String lastname = "lastname";
        String address = "address";
        String city = "city";
        String state = "state";
        int zip = 12345;
        String phone = "123456789";
        boolean defaultOrNot = true;
        Address addressObject = new Address(id, name, lastname, address, city, state, zip, phone, defaultOrNot);

        String expectedName = "Super";

        // Invoke
        addressObject.setLastname(expectedName);

        // Analyze
        assertEquals(expectedName, addressObject.getLastname());

    }

    @Test
    public void TestAddress() {
        // Setup
        int id = 4;
        String name = "name";
        String lastname = "lastname";
        String address = "address";
        String city = "city";
        String state = "state";
        int zip = 12345;
        String phone = "123456789";
        boolean defaultOrNot = true;
        Address addressObject = new Address(id, name, lastname, address, city, state, zip, phone, defaultOrNot);

        String expectedName = "Super";

        // Invoke
        addressObject.setAddress(expectedName);

        // Analyze
        assertEquals(expectedName, addressObject.getAddress());

    }

    @Test
    public void TestCity() {
        // Setup
        int id = 4;
        String name = "name";
        String lastname = "lastname";
        String address = "address";
        String city = "city";
        String state = "state";
        int zip = 12345;
        String phone = "123456789";
        boolean defaultOrNot = true;
        Address addressObject = new Address(id, name, lastname, address, city, state, zip, phone, defaultOrNot);

        String expectedName = "Super";

        // Invoke
        addressObject.setCity(expectedName);

        // Analyze
        assertEquals(expectedName, addressObject.getCity());

    }

    @Test
    public void TestState() {
        // Setup
        int id = 4;
        String name = "name";
        String lastname = "lastname";
        String address = "address";
        String city = "city";
        String state = "state";
        int zip = 12345;
        String phone = "123456789";
        boolean defaultOrNot = true;
        Address addressObject = new Address(id, name, lastname, address, city, state, zip, phone, defaultOrNot);

        String expectedName = "Super";

        // Invoke
        addressObject.setState(expectedName);

        // Analyze
        assertEquals(expectedName, addressObject.getState());

    }

    @Test
    public void TestZip() {
        // Setup
        int id = 4;
        String name = "name";
        String lastname = "lastname";
        String address = "address";
        String city = "city";
        String state = "state";
        int zip = 12345;
        String phone = "123456789";
        boolean defaultOrNot = true;
        Address addressObject = new Address(id, name, lastname, address, city, state, zip, phone, defaultOrNot);

        int expectedName = 123123;

        // Invoke
        addressObject.setZip(expectedName);

        // Analyze
        assertEquals(expectedName, addressObject.getZip());

    }

    @Test
    public void TestPhone() {
        // Setup
        int id = 4;
        String name = "name";
        String lastname = "lastname";
        String address = "address";
        String city = "city";
        String state = "state";
        int zip = 12345;
        String phone = "123456789";
        boolean defaultOrNot = true;
        Address addressObject = new Address(id, name, lastname, address, city, state, zip, phone, defaultOrNot);

        String expectedName = "123123";

        // Invoke
        addressObject.setPhone(expectedName);

        // Analyze
        assertEquals(expectedName, addressObject.getPhone());

    }

    @Test
    public void TestDefault() {
        // Setup
        int id = 4;
        String name = "name";
        String lastname = "lastname";
        String address = "address";
        String city = "city";
        String state = "state";
        int zip = 12345;
        String phone = "123456789";
        boolean defaultOrNot = true;
        Address addressObject = new Address(id, name, lastname, address, city, state, zip, phone, defaultOrNot);

        boolean expectedName = false;

        // Invoke
        addressObject.setDefaultOrNot(expectedName);

        // Analyze
        assertEquals(expectedName, addressObject.getDefaultOrNot());

    }

    @Test
    public void testToString() {
        // Setup
        int id = 4;
        String name = "name";
        String lastname = "lastname";
        String address = "address";
        String city = "city";
        String state = "state";
        int zip = 12345;
        String phone = "123456789";
        boolean defaultOrNot = true;
        Address addressObject = new Address(id, name, lastname, address, city, state, zip, phone, defaultOrNot);

        // Invoke
        String expected = String.format(STRING_FORMAT, id, name, lastname, address, city, state, zip, phone,
                defaultOrNot);

        // Analyze
        assertEquals(expected, addressObject.toString());

    }

}
