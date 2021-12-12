import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PurchaseService } from 'src/app/service/purchase.service';

@Component({
  selector: 'app-failure-page',
  templateUrl: './failure-page.component.html',
  styleUrls: ['./failure-page.component.css'],
})
export class FailurePageComponent implements OnInit {
  message = '';
  constructor(
    private route: ActivatedRoute,
    private service: PurchaseService
  ) {}

  purchaseId = -1;

  ngOnInit(): void {
    this.purchaseId = this.getPurchaseId();
    this.getTransactionMessage();
  }

  getPurchaseId(): number {
    return Number(this.route.snapshot.paramMap.get('id'));
  }

  getTransactionMessage() {
    this.service.getMessage(this.getPurchaseId()).subscribe(
      (response) => {
        console.log(response);
        this.message = response.message;
        this.service
          .failure(this.purchaseId)
          .subscribe((_) => console.log('ok'));
      },
      (error) => {
        this.message = '';
        this.service
          .failure(this.purchaseId)
          .subscribe((_) => console.log('ok'));
      }
    );
  }
}
