import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CartService } from 'src/app/service/cart.service';
import { ShoppingCart } from '../../dto/shopping-cart';

@Component({
  selector: 'app-checkout-page',
  templateUrl: './checkout-page.component.html',
  styleUrls: ['./checkout-page.component.css']
})
export class CheckoutPageComponent implements OnInit {

  constructor(private route: ActivatedRoute, private cartService: CartService) { }

  cartId: number = -1;
  cart: ShoppingCart | undefined = undefined;

  ngOnInit(): void {
    this.cartId = this.getCartId();
    this.getCart();
  }

  getCartId(): number {
    return Number(this.route.snapshot.paramMap.get('id'));
  }

  getCart() {
    this.cartService.getAll().subscribe(
      carts => {
        this.cart = carts.find(c => c.id === this.cartId)
      }
    )
  }
}
