export class Product {
    constructor(
        public id: number,
        public name: string,
        public price: number,
        public billingCycle: string,
        public imageUrl: string,
        public available: boolean
    ) {}
}
