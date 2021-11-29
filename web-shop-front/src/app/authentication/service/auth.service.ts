import { Injectable } from '@angular/core';
import { LoginDTO } from '../dto/LoginDTO';
import axios from 'axios';

import { JwtHelperService } from '@auth0/angular-jwt';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  loginUrl = 'http://localhost:8050/auth/login';
  helper = new JwtHelperService();
  loginComponent = false;
  registerComponent = false;

  constructor(private router: Router) {}

  logIn(user: LoginDTO) {
    return axios.post(this.loginUrl, user, { responseType: 'text' });
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
