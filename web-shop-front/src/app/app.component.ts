import { Component } from '@angular/core';
import { AuthService } from './authentication/service/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'web-shop-front';

  constructor(public authService: AuthService) {}
}
