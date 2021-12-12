import { Component, Input, OnInit } from '@angular/core';
import { AuthService } from 'src/app/authentication/service/auth.service';
import { ProductQuantity } from '../../dto/product-quantity';
import { Purchase } from '../../dto/purchase';

@Component({
  selector: 'app-purchase-report',
  templateUrl: './purchase-report.component.html',
  styleUrls: ['./purchase-report.component.css'],
})
export class PurchaseReportComponent implements OnInit {
  constructor(public authService: AuthService) {}

  @Input() purchase: Purchase | null = null;
  @Input() pproducts: ProductQuantity[] | null = null;
  displayedColumns: string[] = ['name', 'price', 'quantity', 'total'];
  products: ProductQuantity[] = [];

  ngOnInit(): void {
    console.log('OVO NOVO:');
    console.log(this.purchase);
    console.log(' PPRODUCTS:');
    console.log(this.pproducts);
    if (this.purchase != null) {
      this.products = this.purchase.products;
      console.log('MILIJANA:');
      console.log(this.products);
    }
  }
}
