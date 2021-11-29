import { Component, OnDestroy, OnInit } from '@angular/core';
import { AuthService } from '../service/auth.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css'],
})
export class RegistrationComponent implements OnInit, OnDestroy {
  constructor(private _auth: AuthService) {}
  ngOnDestroy(): void {
    this._auth.loginComponent = false;
  }

  ngOnInit(): void {
    this._auth.loginComponent = true;
  }
}
