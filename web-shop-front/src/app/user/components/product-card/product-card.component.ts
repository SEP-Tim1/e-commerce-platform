import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CartService } from 'src/app/service/cart.service';
import { environment } from 'src/environments/environment';
import { Product } from '../../dto/product';

@Component({
  selector: 'app-product-card',
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.css']
})
export class ProductCardComponent implements OnInit {

  constructor(private service: CartService, private router: Router) { }

  @Input() product: Product | null = null;
  public imagePath: string = "";

  ngOnInit(): void {
    this.setImagePath();
  }

  setImagePath() {
    this.imagePath = environment.backend +'/' + this.product?.imageUrl;
  }

  addToCart() {
    if (this.product == null) {
      return;
    }
    this.service.addToCart(this.product.id).subscribe(
      _ => this.router.navigate(['shopping-carts'])
    )
  }
}
