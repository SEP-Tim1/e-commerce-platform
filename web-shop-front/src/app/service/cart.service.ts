import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { QuantityChange } from '../user/dto/quantity-change';
import { ShoppingCart } from '../user/dto/shopping-cart';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  constructor(private _http: HttpClient) { }

  private cartBaseUrl: string = environment.backend + "/cart";

  public changeQuantity(change: QuantityChange): Observable<any> {
    return this._http.put(this.cartBaseUrl, change);
  }

  public getAll(): Observable<ShoppingCart[]> {
    return this._http.get<ShoppingCart[]>(this.cartBaseUrl);
  }

  public addToCart(productId: number): Observable<any> {
    return this._http.post(this.cartBaseUrl + '/' + productId, null);
  }

  public removeFromCart(productId: number): Observable<any> {
    return this._http.delete(this.cartBaseUrl + '/' + productId);
  }
}
