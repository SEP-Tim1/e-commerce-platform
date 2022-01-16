export class ProductDTO {
    constructor(
        public name : string,
        public price : string,
        public billingCycle: string,
        public hasQuantity: boolean,
        public quantity : number
    ){}
}

