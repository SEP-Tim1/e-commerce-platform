export class ProductQuantity {
    constructor(
        public productId: number,
        public productName: string,
        public productPrice: number,
        public billingCycle: string,
        public hasQuantity: boolean,
        public quantity: number,
        public total: number
    ) {}
}
