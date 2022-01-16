export class ProductUpdateDTO {
    constructor(
        public id : number,
        public name : string,
        public price : string,
        public billingCycle: string,
        public hasQuantity: boolean,
        public quantity : number
    ){}
}
