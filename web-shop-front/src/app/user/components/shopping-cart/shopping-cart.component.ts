import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CartService } from 'src/app/service/cart.service';
import { ProductQuantity } from '../../dto/product-quantity';
import { QuantityChange } from '../../dto/quantity-change';
import { ShoppingCart } from '../../dto/shopping-cart';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {

  constructor(private service: CartService) { }

  @Input() cart: ShoppingCart | null = null;
  @Output() refresh = new EventEmitter<boolean>();

  displayedColumns: string[] = ["name", "price", "dec-button", "quantity", "inc-button", "total", "remove-button"];
  products: ProductQuantity[] = [];

  ngOnInit(): void {
    if (this.cart != null) {
      this.products = this.cart.products
    }
  }

  changeQuantity(newQuantity: number, productId: number) {
    if (this.cart === null || newQuantity < 1) {
      return;
    }
    this.service.changeQuantity(new QuantityChange(this.cart?.id, productId, newQuantity)).subscribe(
      _ => {
        const product = this.cart?.products.find(p => p.productId === productId)
        if (product != undefined && this.cart != null) {
          this.cart.total -= product.total;
          product.quantity = newQuantity;
          product.total = newQuantity * product.productPrice;
          this.cart.total += product.total;
        }
      }
    )
  }

  remove(productId: number) {
    this.service.removeFromCart(productId).subscribe(
      _ => this.refresh.emit(true)
    )
  }
}
