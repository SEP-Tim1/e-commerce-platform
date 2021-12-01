import { ProductQuantity } from "./product-quantity";

export class ShoppingCart {
    constructor(
        public id: number,
        public storeName: string,
        public products: ProductQuantity[],
        public total: number
    ) {}
}
