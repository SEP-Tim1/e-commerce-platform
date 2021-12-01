import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Purchase } from '../user/dto/purchase';
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

  public getAll(): Observable<Purchase[]> {
    return this._http.get<Purchase[]>(this.purchaseBaseUrl);
  }
}
