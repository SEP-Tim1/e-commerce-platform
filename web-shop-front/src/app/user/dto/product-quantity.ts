export class ProductQuantity {
    constructor(
        public productId: number,
        public productName: string,
        public productPrice: number,
        public quantity: number,
        public total: number
    ) {}
}
