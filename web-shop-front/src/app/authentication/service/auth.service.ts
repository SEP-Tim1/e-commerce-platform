import { Injectable } from '@angular/core';
import { LoginDTO } from '../dto/LoginDTO';
import axios from 'axios';

import { JwtHelperService } from '@auth0/angular-jwt';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { RegistrationDTO } from '../dto/RegistrationDTO';
import { UserInfoDTO } from '../dto/UserInfoDTO';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { StoreNameDTO } from '../dto/StoreNameDTO';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  loginUrl = environment.backend + '/auth/login';
  registrerUrl = environment.backend + '/auth/register';
  getUserInfoUrl = environment.backend + '/auth/info';
  updateInfoUrl = environment.backend + '/auth/update';
  getStoreNameUrl = environment.backend + '/store/name';
  setTokenUrl = environment.backend + '/store/token';
  helper = new JwtHelperService();
  loginComponent = false;
  registerComponent = false;

  constructor(private router: Router, private _http: HttpClient) {}

  logIn(user: LoginDTO): Observable<any> {
    return this._http.post(this.loginUrl, user);
  }

  register(user: RegistrationDTO) {
    return axios.post(this.registrerUrl, user, { responseType: 'text' });
  }

  getInfo(): Observable<UserInfoDTO> {
    return this._http.get<UserInfoDTO>(this.getUserInfoUrl);
  }

  getStoreNameInfo(id: number): Observable<StoreNameDTO> {
    return this._http.get<StoreNameDTO>(
      this.getStoreNameUrl + '/' + id.toString()
    );
  }

  setStoreNameInfo(name: any) {
    return this._http.put(this.getStoreNameUrl, name);
  }

  setToken(token: string | null) {
    return this._http.put(this.setTokenUrl + '/' + token, null, { responseType: 'text' });
  }

  deleteToken() {
    return this._http.delete(this.setTokenUrl, { responseType: 'text' });
  }

  updateInfo(user: UserInfoDTO) {
    return this._http.post(this.updateInfoUrl, user, {
      responseType: 'text',
    });
  }

  isLoggedIn() {
    if (localStorage.getItem('token') != null && !this.isExpired()) return true;
    else return false;
  }

  isExpired() {
    let decoded = this.decodeToken();
    if (Date.now() < decoded.exp * 1000) {
      return false;
    } else return true;
  }

  getRole() {
    if (this.isLoggedIn()) return this.decodeToken().role;
    return '';
  }

  getId() {
    if (this.isLoggedIn()) return this.decodeToken().id;
    return '';
  }

  getUsername() {
    if (this.isLoggedIn()) return this.decodeToken().sub;
    return '';
  }

  decodeToken() {
    let token = localStorage.getItem('token');
    if (token) return this.helper.decodeToken(token);
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('id');
    localStorage.removeItem('username');
    this.router.navigate(['login']);
  }
}
