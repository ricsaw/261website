package com.products.api.productsapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A class representing a product sold on the EStore
 * 
 * Notes:
 *      - I went ahead and made getters/setters for each of the variables because
 *        I figured they would be needed for deserialization. 
 *      
 *      - Also I used //region later in the code, that just allows part of the code to
 *        be collapsed to save space and organize things
 * 
 * @author Robert Tetreault
 */
public class Product implements Model {
    // Package private for tests
    static final String STRING_FORMAT = "Product [name=%s, id=%d, price=%.2f, quantity=%d, description=\"%s\", imgUrl=\"%s\"]";

    
    // Get team's opinion on this kind of string formatting vvv
    //static final String STRING_FORMAT = "Product: %s\n\t- id: %d\n\t- price: %.2f\n\t- quantity: %d\n\t- description: \"%s\"";

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("description") private String description;
    @JsonProperty("price") private float price;
    @JsonProperty("quantity") private int quantity;
    @JsonProperty("imageUrl") private String imageUrl;
    
    

    /**
     * Create a product with the given traits:
     * @param id            The id of the product
     * @param name          The name of the product
     * @param description   The description of the product
     * @param price         The price of the product
     * @param quantity      The number of instances of the product
     * @param imageUrl      The image for the product 
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public  Product(@JsonProperty("id") int id,
                    @JsonProperty("name") String name,
                    @JsonProperty("description") String description,
                    @JsonProperty("price") float price,
                    @JsonProperty("quantity") int quantity,
                    @JsonProperty("imageUrl") String imageUrl)
    {                
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;  
    }

    //region Getters and Setters

    /**
     * Sets the name of the product - necessary for JSON object to Java object deserialization
     * @param name The name of the product
     */
    public void setName(String name) {this.name = name;}

    /**
     * Retrieves the name of the product
     * @return The name of the product
     */
    public String getName() {return name;}

    /**
     * Sets the id of the product - necessary for JSON object to Java object deserialization
     * @param id The id of the product
     */
    public void setId(int id) {this.id = id;}

    /**
     * Retrieves the name of the product
     * @return The name of the product
     */
    public int getId() {return id;}
    
    /**
     * Sets the description of the product - necessary for JSON object to Java object deserialization
     * @param description The description of the product
     */
    public void setDescription(String description) {this.description = description;}

    /**
     * Retrieves the description of the product
     * @return The description of the product
     */
    public String getDescription() {return description;}

    /**
     * Sets the price of the product - necessary for JSON object to Java object deserialization
     * @param price The price of the product
     */
    public void setPrice(float price) {this.price = price;}

    /**
     * Retrieves the price of the product
     * @return The price of the product
     */
    public float getPrice() {return price;}

    /**
     * Sets the quantity of the product - necessary for JSON object to Java object deserialization
     * @param quantity The quantity of the product
     */
    public void setQuantity(int quantity) {this.quantity = quantity;}

    /**
     * Retrieves the quantity of the product
     * @return The quantity of the product
     */
    public int getQuantity() {return quantity;}

    /**
     * Sets the image url of the product - necessary for JSON object to Java object deserialization
     * @param imageUrl The url to set
     */
    public void setImageUrl(String imageUrl) {this.imageUrl = imageUrl;}

    /**
     * Increase the Quantity by one 
     */
    public void increaseQuantity(){
      this.quantity +=1; 
    } 

    /**
     * Decrease the Quantity by one 
     */
    public void decreaseQuantity(){
        this.quantity -=1; 
      } 


    /**
     * Retrieves the image url of the product
     * @return The image url of the product
     */
    public String getImageUrl() {return imageUrl;}

    //endregion

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, name, id, price, quantity, description, imageUrl);
    }
}