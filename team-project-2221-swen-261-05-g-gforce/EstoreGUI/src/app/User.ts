import { Address } from "./Address";
import { Coupon } from "./Coupon";
import { Order } from "./Order";
import { Product } from "./Product";

export class User{
    id: number;
    name: string;
    username: string;
    password: string;
    admin: boolean;
    darkMode: boolean;
    cart: Array<Product>;
    coupon: Array<Coupon>;
    address: Array<Address>;
    currentOrders: Array<Order>;
    previousOrders: Array<Order>;

}