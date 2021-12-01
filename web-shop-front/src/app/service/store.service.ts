import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Store } from '../user/dto/store';

@Injectable({
  providedIn: 'root'
})
export class StoreService {

  constructor(private _http: HttpClient) { }

  private storeBaseUrl: string = environment.backend + "/store";

  private getUrl: string = this.storeBaseUrl + '/info';

  public getAll(): Observable<Store[]> {
    return this._http.get<Store[]>(this.getUrl);
  }

  public getById(id: number): Observable<Store> {
    return this._http.get<Store>(this.getUrl + '/' + id);
  }
}
