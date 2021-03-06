import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RouteGuardService } from './authentication/authorization/route-guard.service';
import { LoginComponent } from './authentication/login/login.component';
import { RegistrationComponent } from './authentication/registration/registration.component';
import { UpdateInfoComponent } from './authentication/update-info/update-info.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { CreateProductComponent } from './seller/create-product/create-product.component';
import { ProductUpdateComponent } from './seller/product-update/product-update.component';
import { SellerHomeComponent } from './seller/seller-home/seller-home.component';
import { CheckoutPageComponent } from './user/pages/checkout-page/checkout-page.component';
import { ErrorPageComponent } from './user/pages/error-page/error-page.component';
import { FailurePageComponent } from './user/pages/failure-page/failure-page.component';
import { PurchasesPageComponent } from './user/pages/purchases-page/purchases-page.component';
import { ShoppingCartsPageComponent } from './user/pages/shopping-carts-page/shopping-carts-page.component';
import { StorePageComponent } from './user/pages/store-page/store-page.component';
import { StoresPageComponent } from './user/pages/stores-page/stores-page.component';
import { SuccessPageComponent } from './user/pages/success-page/success-page.component';

const routes: Routes = [
  { path: '', component: LoginComponent },
  {
    path: 'user-home',
    component: StoresPageComponent,
    canActivate: [RouteGuardService],
    data: { expectedRoles: ['USER'] },
  },
  {
    path: 'seller-home',
    component: SellerHomeComponent,
    canActivate: [RouteGuardService],
    data: { expectedRoles: ['SELLER'] },
  },
  {path: 'product-update/:id', 
  component: ProductUpdateComponent,
  canActivate: [RouteGuardService],
  data: { expectedRoles: ['SELLER']}
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
  {
    path: 'store/:id',
    component: StorePageComponent,
    canActivate: [RouteGuardService],
    data: { expectedRoles: ['USER']}
  },
  {
    path: 'checkout/:id',
    component: CheckoutPageComponent,
    canActivate: [RouteGuardService],
    data: { expectedRoles: ['USER']}
  },
  {
    path: 'shopping-carts',
    component: ShoppingCartsPageComponent,
    canActivate: [RouteGuardService],
    data: { expectedRoles: ['USER']}
  },
  {
    path: 'purchases',
    component: PurchasesPageComponent,
    canActivate: [RouteGuardService],
    data: { expectedRoles: ['SELLER']}
  },
  { path: 'login', component: LoginComponent },
  { path: 'registration', component: RegistrationComponent,  },
  { path: 'success/:id', component: SuccessPageComponent, data: { expectedRoles: ['USER'] } },
  { path: 'failure/:id', component: FailurePageComponent, data: { expectedRoles: ['USER'] } },
  { path: 'error/:id', component: ErrorPageComponent, data: { expectedRoles: ['USER'] } },
  { path: '**', component: PageNotFoundComponent, data: { expectedRoles: ['USER'] } },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
