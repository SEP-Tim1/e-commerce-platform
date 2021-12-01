import { Component, Input, OnInit } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Product } from '../../dto/product';

@Component({
  selector: 'app-product-card',
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.css']
})
export class ProductCardComponent implements OnInit {

  constructor() { }

  @Input() product: Product | null = null;
  public imagePath: string = "";

  ngOnInit(): void {
    this.setImagePath();
  }

  setImagePath() {
    this.imagePath = environment.backend +'/' + this.product?.imageUrl;
  }
}
