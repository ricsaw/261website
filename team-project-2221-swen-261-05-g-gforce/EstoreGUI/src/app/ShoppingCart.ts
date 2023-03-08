///
/// File: ShoppingCart.ts
/// Author: James De Luca
/// Description: The TypeScript file for the shopping cart.
///

import { Product } from "./Product"

export interface ShoppingCart 
{
    id: number
    user_id: number
    products: Array<Product>
}