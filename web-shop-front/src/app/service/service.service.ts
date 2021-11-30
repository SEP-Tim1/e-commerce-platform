import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ServiceService {

  private postProductUrl = environment.backend + '/product/createProduct';

  constructor(private _http : HttpClient) { }

  postProduct(data : FormData) {
    console.log('poslalaaa')
    return this._http.post(this.postProductUrl, data, { responseType: 'text'} );
    
  }
}
