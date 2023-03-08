/**
    File: Product.ts
    Author: Robert Tetreault
    Description: The typeset file for the product class. This file is what
                 Angular sees instead of the backend?
*/

/**
 * Define the attributes that Product has
 */
export interface Product{
    id: number;     // This is kind of gross, but typescript has number instead of int
    name: string;
    description: string;
    price: number; 
    quantity: number;
    imageUrl: string;
}