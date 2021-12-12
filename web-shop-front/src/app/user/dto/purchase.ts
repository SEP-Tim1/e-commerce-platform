import { ProductQuantity } from "./product-quantity";
import { PurchaseDetails } from "./purchase-details";

export class Purchase {
    constructor(
        public id: number,
        public userDetails: PurchaseDetails,
        public created: Date,
        public products: ProductQuantity[],
        public storeName: string
    ) {}
}
