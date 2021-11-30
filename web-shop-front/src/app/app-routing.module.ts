import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RouteGuardService } from './authentication/authorization/route-guard.service';
import { LoginComponent } from './authentication/login/login.component';
import { RegistrationComponent } from './authentication/registration/registration.component';
import { UpdateInfoComponent } from './authentication/update-info/update-info.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { CreateProductComponent } from './seller/create-product/create-product.component';
import { SellerHomeComponent } from './seller/seller-home/seller-home.component';
import { UserHomeComponent } from './user/user-home/user-home.component';

const routes: Routes = [
  { path: '', component: LoginComponent },
  {
    path: 'user-home',
    component: UserHomeComponent,
    canActivate: [RouteGuardService],
    data: { expectedRoles: ['USER'] },
  },
  {
    path: 'seller-home',
    component: SellerHomeComponent,
    canActivate: [RouteGuardService],
    data: { expectedRoles: ['SELLER'] },
  },
  {
    path: 'create-product',
    component: CreateProductComponent,
    canActivate: [RouteGuardService],
    data: { expectedRoles: ['SELLER'] },
  },
  {
    path: 'update-info',
    component: UpdateInfoComponent,
    canActivate: [RouteGuardService],
    data: { expectedRoles: ['SELLER', 'USER'] },
  },
  { path: 'login', component: LoginComponent },
  { path: 'registration', component: RegistrationComponent },
  { path: '**', component: PageNotFoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
