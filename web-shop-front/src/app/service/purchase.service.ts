import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Purchase } from '../user/dto/purchase';
import { PurchaseDetails } from '../user/dto/purchase-details';
import { Transaction } from '../user/dto/transaction';

@Injectable({
  providedIn: 'root',
})
export class PurchaseService {
  constructor(private _http: HttpClient) {}

  private purchaseBaseUrl = environment.backend + '/purchase';
  private purchaseSuccess = this.purchaseBaseUrl + '/success';
  private purchaseFailure = this.purchaseBaseUrl + '/failure';
  private purchaseMessageUrl = this.purchaseBaseUrl + '/message/';

  public purchase(cartId: number, details: PurchaseDetails): Observable<any> {
    return this._http.post(this.purchaseBaseUrl + '/' + cartId, details);
  }

  public getAll(): Observable<Purchase[]> {
    return this._http.get<Purchase[]>(this.purchaseBaseUrl);
  }

  public success(purchaseId: number): Observable<Purchase> {
    return this._http.put<Purchase>(
      this.purchaseSuccess + '/' + purchaseId,
      null
    );
  }

  public failure(purchaseId: number) {
    return this._http.put(this.purchaseFailure + '/' + purchaseId, null);
  }

  public getMessage(purchaseId: number): Observable<Transaction> {
    return this._http.get<Transaction>(this.purchaseMessageUrl + purchaseId);
  }
}
