package com.products.api.productsapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A class representing an Adress
 * 
 * Notes:
 *      - I went ahead and made getters/setters for each of the variables because
 *        I figured they would be needed for deserialization. 
 *      
 *      - Also I used //region later in the code, that just allows part of the code to
 *        be collapsed to save space and organize things
 * 
 * @author Antar Narayan Chowdhury
 */
public class Address implements Model {
    static final String STRING_FORMAT = "Address [id=%d, name=%s, lastname=%s, address=%s, city=%s, state=%s, zip=%d , phone=%s, defaultOrNot=%s]";

    @JsonProperty("id") int id;
    @JsonProperty("name") String name;
    @JsonProperty("lastname") String lastname;
    @JsonProperty("address") String address;
    @JsonProperty("city") String city;
    @JsonProperty("state") String state;
    @JsonProperty("zip") Integer zip;
    @JsonProperty("phone") String phone;
    @JsonProperty("defaultOrNot") boolean defaultOrNot; 

    
    /**
     * Create a user with the given traits:
     * @param id            The id of the user
     * @param name          The name of the user
     * @param lastame      The description of the user
     * @param address       The price of the user
     * @param city          The quantity of the user
     * @param state         The quantity of the user
     * @param zip           The quantity of the user
     * @param phone         The quantity of the user
     * @param defaultOrNot  The quantity of the user
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     * @return 
     */
    public Address(@JsonProperty("id") int id,
                   @JsonProperty("name") String name,
                   @JsonProperty("lastname") String lastname,
                   @JsonProperty("address") String address,
                   @JsonProperty("city") String city,
                   @JsonProperty("state") String state,
                   @JsonProperty("zip") Integer zip,
                   @JsonProperty("phone") String phone,
                   @JsonProperty("defaultOrNot") boolean defaultOrNot){                
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.defaultOrNot = defaultOrNot;
    }

    //get and setters for each of the variables
   
    /*
    * @return the id
    */
    public int getId() {
        return id;
    }

    /*
    * @param id the id to set
    */
    public void setId(int id) {
        this.id = id;
    }

    /*
    * @return the name
    */
    public String getName() {
        return name;
    }

    /*
    * @param name the name to set
    */
    public void setName(String name) {
        this.name = name;
    }

    /*
    * @return the lastname
    */
    public String getLastname() {
        return lastname;
    }

    /*
    * @param lastname the lastname to set
    */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /*
    * @return the address
    */
    public String getAddress() {
        return address;
    }

    /*
    * @param address the address to set
    */
    public void setAddress(String address) {
        this.address = address;
    }

    /*
    * @return the city
    */
    public String getCity() {
        return city;
    }

    /*
    * @param city the city to set
    */
    public void setCity(String city) {
        this.city = city;
    }

    /*
    * @return the state
    */
    public String getState() {
        return state;
    }

    /*
    * @param state the state to set
    */
    public void setState(String state) {
        this.state = state;
    }

    /*
    * @return the zip
    */
    public Integer getZip() {
        return zip;
    }

    /*
    * @param zip the zip to set
    */
    public void setZip(Integer zip) {
        this.zip = zip;
    }

    /*
    * @return the phone
    */
    public String getPhone() {
        return phone;
    }

    /*
    * @param phone the phone to set
    */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /*
    * @return the defaultOrNot
    */
    public boolean getDefaultOrNot() {
        return defaultOrNot;
    }

    /*
    * @param defaultOrNot the defaultOrNot to set
    */
    public void setDefaultOrNot(boolean defaultOrNot) {
        this.defaultOrNot = defaultOrNot;
    }

    //endregion
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, id, name, lastname, address, city, state, zip, phone, defaultOrNot);
    }



    

}
 
    

