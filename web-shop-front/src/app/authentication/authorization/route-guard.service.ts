import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { AuthService } from '../service/auth.service';

@Injectable({
  providedIn: 'root',
})
export class RouteGuardService {
  constructor(public authService: AuthService, public router: Router) {}
  canActivate(route: ActivatedRouteSnapshot): boolean {
    const expectedRoles: string[] = route.data['expectedRoles'];
    if (
      !expectedRoles.includes(this.authService.getRole()) &&
      this.authService.isLoggedIn()
    ) {
      this.authService.logout();
      this.router.navigate(['login']);
      return false;
    }
    if (!this.authService.isLoggedIn() && expectedRoles.length > 0) {
      this.authService.logout();
      this.router.navigate(['login']);
      return false;
    }

    return true;
  }
}
