export class QuantityChange {
    constructor(
        public cartId: number,
        public productId: number,
        public newQuantity: number
    ) {}
}
