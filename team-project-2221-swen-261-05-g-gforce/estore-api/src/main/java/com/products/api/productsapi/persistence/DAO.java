package com.products.api.productsapi.persistence;

import java.io.IOException;

/**
 * Defines the interface for Product object persistence
 * I don't create the getPrice and getDescriptions, because
 * we can access them via Product instance
 * @author Antar Narayan Chowdhury
 */
public interface DAO<E> {
    /**
     * Retrieves all {@linkplain E Es}
     * 
     * @return An array of {@link E (generic) E model} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    E[] getAll() throws IOException;

    /**
     * Finds all {@linkplain E (generic) E model} whose name contains the given text
     * 
     * @param containsText The text to match against
     * 
     * @return An array of {@link E (generic) E model} whose nemes contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    E[] search(String containsText) throws IOException;

    /**
     * Retrieves a {@linkplain E (generic) E model} with the given id
     * 
     * @param id The id of the {@link E (generic) E model} to get
     * 
     * @return a {@link E (generic) E model} object with the matching id
     * <br>
     * null if no {@link E (generic) E model} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    E get(int id) throws IOException;

    /**
     * Creates and saves a {@linkplain E (generic) E model}
     * 
     * @param E (generic) {@linkplain E (generic) model} object to be created and saved
     * <br>
     * The id of the product object is ignored and a new uniqe id is assigned
     *
     * @return new {@link E (generic) E model} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    E create(E model) throws IOException;

    /**
     * Updates and saves a {@linkplain E (generic) model}
     * 
     * @param {@link E (generic) E model} object to be updated and saved
     * 
     * @return updated {@link E (generic) model} if successful, null if
     * {@link E (generic) model} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    E update(E model) throws IOException;

    /**
     * Deletes a {@linkplain E (generic) model} with the given id
     * 
     * @param id The id of the {@link E (generic) model}
     * 
     * @return true if the {@link  E (generic) model} was deleted
     * <br>
     * false if product with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean delete(int id) throws IOException;
}