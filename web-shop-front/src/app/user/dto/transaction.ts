export class Transaction {
  constructor(
    public id: number,
    public purchaseId: number,
    public status: string,
    public invoiceId: number,
    public message: string
  ) {}
}
