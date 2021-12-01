import { Component, OnInit } from '@angular/core';
import { CartService } from 'src/app/service/cart.service';
import { ShoppingCart } from '../../dto/shopping-cart';

@Component({
  selector: 'app-shopping-carts-page',
  templateUrl: './shopping-carts-page.component.html',
  styleUrls: ['./shopping-carts-page.component.css']
})
export class ShoppingCartsPageComponent implements OnInit {

  constructor(private service: CartService) { }

  carts: ShoppingCart[] = [];

  ngOnInit(): void {
    this.getAll();
  }

  getAll() {
    this.service.getAll().subscribe(
      result => this.carts = result
    )
  }

  onRefresh(refresh: boolean) {
    if (refresh) {
      this.getAll();
    }
  }
}
