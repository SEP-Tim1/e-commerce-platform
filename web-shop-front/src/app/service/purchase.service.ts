import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Purchase } from '../user/dto/purchase';
import { PurchaseDetails } from '../user/dto/purchase-details';
import { PurchaseOutcome } from '../user/dto/purchase-outcome';

@Injectable({
  providedIn: 'root',
})
export class PurchaseService {
  constructor(private _http: HttpClient) {}

  private purchaseBaseUrl = environment.backend + '/purchase';
  private outcomeUrl = this.purchaseBaseUrl + '/outcome';

  public purchase(cartId: number, details: PurchaseDetails): Observable<any> {
    return this._http.post(this.purchaseBaseUrl + '/' + cartId, details);
  }

  public getAll(): Observable<Purchase[]> {
    return this._http.get<Purchase[]>(this.purchaseBaseUrl);
  }

  public get(id: number): Observable<Purchase> {
    return this._http.get<Purchase>(this.purchaseBaseUrl + '/' + id);
  }

  public getOutcome(id: number): Observable<PurchaseOutcome> {
    return this._http.get<PurchaseOutcome>(this.outcomeUrl + '/' + id);
  }
}
