export class Product {
    constructor(
        public id : number,
        public name : string,
        public price : number,
        public billingCycle: string,
        public hasQuantity: boolean,
        public quantity : number,
        public imageUrl : string
    ){}
}
