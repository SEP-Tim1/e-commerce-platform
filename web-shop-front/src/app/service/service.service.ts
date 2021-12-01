import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Product } from '../seller/DTOs/product';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ServiceService {

  private postProductUrl = environment.backend + '/product/createProduct';
  private getProductsFromStoreUrl = environment.backend + '/product/storesProducts';

  constructor(private _http : HttpClient) { }

  postProduct(data : FormData) {
    return this._http.post(this.postProductUrl, data, { responseType: 'text'} );
  }
  getStoreProducts() : Observable<Product[]>{
    return this._http.get<Product[]>(this.getProductsFromStoreUrl);
  }
}
