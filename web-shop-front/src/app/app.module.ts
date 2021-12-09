import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './authentication/login/login.component';
import { RegistrationComponent } from './authentication/registration/registration.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatCardModule } from '@angular/material/card';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';
import { SellerHomeComponent } from './seller/seller-home/seller-home.component';
import { UpdateInfoComponent } from './authentication/update-info/update-info.component';
import { MatDividerModule } from '@angular/material/divider';
import { MatListModule } from '@angular/material/list';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatTableModule } from '@angular/material/table';

import { NgImageSliderModule } from 'ng-image-slider';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptorService } from './authentication/authorization/auth-interceptor.service';
import { CreateProductComponent } from './seller/create-product/create-product.component';
import { ProductUpdateComponent } from './seller/product-update/product-update.component';
import { StoreCardComponent } from './user/components/store-card/store-card.component';
import { StoresPageComponent } from './user/pages/stores-page/stores-page.component';
import { ProductCardComponent } from './user/components/product-card/product-card.component';
import { StorePageComponent } from './user/pages/store-page/store-page.component';
import { ShoppingCartsPageComponent } from './user/pages/shopping-carts-page/shopping-carts-page.component';
import { ShoppingCartComponent } from './user/components/shopping-cart/shopping-cart.component';
import { CheckoutFormComponent } from './user/components/checkout-form/checkout-form.component';
import { CheckoutPageComponent } from './user/pages/checkout-page/checkout-page.component';
import { PurchaseReportComponent } from './user/components/purchase-report/purchase-report.component';
import { PurchasesPageComponent } from './user/pages/purchases-page/purchases-page.component';
import { SuccessPageComponent } from './user/pages/success-page/success-page.component';
import { FailurePageComponent } from './user/pages/failure-page/failure-page.component';
import { ErrorPageComponent } from './user/pages/error-page/error-page.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent,
    PageNotFoundComponent,
    SellerHomeComponent,
    UpdateInfoComponent,
    CreateProductComponent,
    ProductUpdateComponent,
    StoreCardComponent,
    StoresPageComponent,
    ProductCardComponent,
    StorePageComponent,
    ShoppingCartsPageComponent,
    ShoppingCartComponent,
    CheckoutFormComponent,
    CheckoutPageComponent,
    PurchaseReportComponent,
    PurchasesPageComponent,
    SuccessPageComponent,
    FailurePageComponent,
    ErrorPageComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatSnackBarModule,
    MatCardModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatToolbarModule,
    MatIconModule,
    MatSelectModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgImageSliderModule,
    MatDividerModule,
    MatListModule,
    MatGridListModule,
    MatTableModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptorService,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
