import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from '../service/auth.service';
import { Router } from '@angular/router';
import {
  Validators,
  FormControl,
  FormGroupDirective,
  NgForm,
} from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { RegistrationDTO } from '../dto/RegistrationDTO';

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(
    control: FormControl | null,
    form: FormGroupDirective | NgForm | null
  ): boolean {
    const isSubmitted = form && form.submitted;
    return !!(
      control &&
      control.invalid &&
      (control.dirty || control.touched || isSubmitted)
    );
  }
}

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css'],
})
export class RegistrationComponent implements OnInit, OnDestroy {
  username = '';
  password = '';
  email = '';
  role = '';
  emailControl = new FormControl('', [Validators.required, Validators.email]);
  usernameControl = new FormControl('', [
    Validators.required,
    Validators.minLength(2),
    Validators.maxLength(50),
  ]);
  passwordControl = new FormControl('', [
    Validators.required,
    Validators.minLength(8),
    Validators.maxLength(200),
  ]);
  matcher = new MyErrorStateMatcher();

  constructor(
    private router: Router,
    private _snackBar: MatSnackBar,
    private _auth: AuthService
  ) {}
  ngOnDestroy(): void {
    this._auth.registerComponent = false;
  }

  ngOnInit(): void {
    if (this._auth.isLoggedIn()) {
      this.router.navigate([this._auth.getRole().toLowerCase() + '-home']);
    } else this._auth.registerComponent = true;
  }

  register() {
    let dto = new RegistrationDTO(
      this.username,
      this.password,
      this.email,
      this.role
    );
    this._auth
      .register(dto)
      .then((response) => {
        this.openSnackBar('Registration was successful.', 'Ok');
        this.router.navigate(['login']);
        console.log(response);
      })
      .catch((error) => {
        console.log(error.response.data);
        if (error.response.data.cause === 'username') {
          this.openSnackBar(error.response.data.message, 'Ok');
          this.username = '';
        } else if (error.response.data.cause === 'email') {
          this.openSnackBar(error.response.data.message, 'Ok');
          this.email = '';
        } else {
          this.openSnackBar('Something went wrong... Try again later.', 'Ok');
        }
      });
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 10000,
    });
  }
}
