import { Product } from "./Product";
import { Address } from "./Address";

export class Order {
     /**
     * Create an Order with the given traits:
     * @param id            The id of the order
     * @param userId        The id of the user who placed the order
     * @param cardNumber    The card number of the user who placed the order
     * @param expDate       The expiration date associated with the card
     * @param securityCode  The security code associated with the card
     * @param products      The list of products in order
     */
    id: number;
    userId: number;
    cardNumber: string;
    expDate: string;
    securityCode: number;
    products: Product[];
    address: Array<Address>;
}
