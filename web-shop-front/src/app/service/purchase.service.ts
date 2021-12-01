import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { PurchaseDetails } from '../user/dto/purchase-details';

@Injectable({
  providedIn: 'root'
})
export class PurchaseService {

  constructor(private _http: HttpClient) { }

  private purchaseBaseUrl = environment.backend + '/purchase';

  public purchase(cartId: number, details: PurchaseDetails): Observable<any> {
    return this._http.post(this.purchaseBaseUrl + '/' + cartId, details);
  }
}
