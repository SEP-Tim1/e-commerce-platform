import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from './authentication/service/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'web-shop-front';

  constructor(public authService: AuthService, private router: Router) {}

  homeButtonClick() {
    if (this.authService.isLoggedIn()) {
      this.router.navigate([
        this.authService.getRole().toLowerCase() + '-home',
      ]);
    }
  }

}
