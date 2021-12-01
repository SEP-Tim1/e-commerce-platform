import { Component, OnInit } from '@angular/core';
import { PurchaseService } from 'src/app/service/purchase.service';
import { ProductQuantity } from '../../dto/product-quantity';
import { Purchase } from '../../dto/purchase';

@Component({
  selector: 'app-purchases-page',
  templateUrl: './purchases-page.component.html',
  styleUrls: ['./purchases-page.component.css']
})
export class PurchasesPageComponent implements OnInit {

  constructor(private service: PurchaseService) { }

  purchases: Purchase[] = [];

  ngOnInit(): void {
    this.getAll();
  }

  getAll() {
    this.service.getAll().subscribe(
      result => this.purchases = result
    )
  }
}
