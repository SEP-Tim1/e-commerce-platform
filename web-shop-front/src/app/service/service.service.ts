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
  private newImageProductUpdateUrl = environment.backend + '/product/updateProductImage';
  private updateProductInfoUrl = environment.backend + '/product/updateProductInfo';
  private deleteProductUrl = environment.backend + '/product/deleteProduct';


  constructor(private _http : HttpClient) { }

  public postProduct(data : FormData) {
    return this._http.post(this.postProductUrl, data, { responseType: 'text'} );
  }
  public getStoreProducts() : Observable<Product[]>{
    return this._http.get<Product[]>(this.getProductsFromStoreUrl);
  }
  public getProduct(id : string) : Observable<Product> {
    return this._http.get<Product>(environment.backend + '/product/' + id);
  }
  public newImageProductUpdate(data : FormData) {
    return this._http.post(this.newImageProductUpdateUrl, data, { responseType: 'text'} );
  }
  public updateProductInfo(data : Product) {
    return this._http.post(this.updateProductInfoUrl, data, { responseType: 'text'} );
  }
  public deleteProduct(id : number)  {
    return this._http.post(this.deleteProductUrl, id, { responseType: 'text'});
  }

}
