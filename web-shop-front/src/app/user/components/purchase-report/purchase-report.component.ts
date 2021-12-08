import { Component, Input, OnInit } from '@angular/core';
import { ProductQuantity } from '../../dto/product-quantity';
import { Purchase } from '../../dto/purchase';

@Component({
  selector: 'app-purchase-report',
  templateUrl: './purchase-report.component.html',
  styleUrls: ['./purchase-report.component.css']
})
export class PurchaseReportComponent implements OnInit {

  constructor() { }

  @Input() purchase: Purchase | null = null;
  displayedColumns: string[] = ["name", "price", "quantity", "total"];
  products: ProductQuantity[] = [];
  
  ngOnInit(): void {
    if (this.purchase != null) {
      this.products = this.purchase.products
    }
  }

}