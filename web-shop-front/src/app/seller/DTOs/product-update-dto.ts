export class ProductUpdateDTO {
    constructor(
        public id : number,
        public name : string,
        public price : string,
        public quantity : number
    ){}
}
