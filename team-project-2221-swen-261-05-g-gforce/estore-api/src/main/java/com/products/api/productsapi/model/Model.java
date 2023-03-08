package com.products.api.productsapi.model;

/**
 * A class representing an object to be stored sold on the EStore
 * 
 * @author Aidan Collins
 */
public interface Model {

    /**
     * Sets the id of the object - necessary for JSON object to Java object deserialization
     * @param id The id of the object
     */
    public void setId(int id);

    /**
     * Retrieves the id of the object
     * @return The id of the object
     */
    public int getId();

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString();
}
