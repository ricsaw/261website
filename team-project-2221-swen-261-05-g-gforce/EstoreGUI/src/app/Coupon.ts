
 export interface Coupon{
    id: number;     // This is kind of gross, but typescript has number instead of int
    code: string;
    discount: number;
    expirationDate: string;
    
}