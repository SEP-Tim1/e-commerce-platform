import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoginDTO } from '../dto/LoginDTO';
import { AuthService } from '../service/auth.service';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit, OnDestroy {
  helper = new JwtHelperService();
  username: string = '';
  password: string = '';
  constructor(
    private router: Router,
    private _snackBar: MatSnackBar,
    private _auth: AuthService
  ) {}

  ngOnDestroy(): void {
    this._auth.loginComponent = false;
  }

  ngOnInit(): void {
    if (this._auth.isLoggedIn()) {
      this.router.navigate([this._auth.getRole().toLowerCase() + '-home']);
    } else this._auth.loginComponent = true;
  }

  login() {
    const dto = new LoginDTO(this.username, this.password);
    this._auth
      .logIn(dto)
      .subscribe(response => {
        console.log(response)
        localStorage.setItem('token', response["jwt"]);

        let decoded = this.helper.decodeToken(response["jwt"]);

        localStorage.setItem('role', decoded.role);
        localStorage.setItem('id', decoded.id);
        localStorage.setItem('username', decoded.sub);

        if (decoded.role === 'USER') {
          this.router.navigate(['user-home']);
        } else if (decoded.role === 'SELLER') {
          this.router.navigate(['seller-home']);
        } else {
          this.router.navigate(['not-found']);
        }
      },
      error => {
        console.log(error);
        this.openSnackBar(error.error, 'Ok');
      });
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 10000,
    });
  }
}
