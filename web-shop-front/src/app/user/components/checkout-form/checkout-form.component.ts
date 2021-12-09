import { Component, Input, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/authentication/service/auth.service';
import { PurchaseService } from 'src/app/service/purchase.service';
import { environment } from 'src/environments/environment';
import { PurchaseDetails } from '../../dto/purchase-details';

@Component({
  selector: 'app-checkout-form',
  templateUrl: './checkout-form.component.html',
  styleUrls: ['./checkout-form.component.css']
})
export class CheckoutFormComponent implements OnInit {

  constructor(private authService: AuthService, private service: PurchaseService, private router: Router, private snackBar: MatSnackBar) { }

  dto: PurchaseDetails = new PurchaseDetails("", "", "", "", "");

  @Input() cartId: number = -1;
  
  ngOnInit(): void {
    this.loadInitialInfo();
  }

  pay() {
    this.service.purchase(this.cartId, this.dto).subscribe(
      requestId => {
        window.location.href = environment.psp + '/payment/' + requestId
      },
      error => {
        this.displayMessage(error.error.message);
        this.router.navigate(['shopping-carts']);
      }
    )
  }

  loadInitialInfo() {
    this.authService.getInfo().subscribe(
      info => {
        this.dto.name = info.firstName;
        this.dto.surname = info.lastName;
        this.dto.phoneNumber = info.phone;
        this.dto.address = info.address;
      }
    )
  }

  displayMessage(message: string) {
    this.snackBar.open(message, "Okay", {
      duration: 10000,
    });
  }
}
