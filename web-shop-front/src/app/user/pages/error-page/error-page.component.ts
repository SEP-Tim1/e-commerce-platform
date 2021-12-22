import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PurchaseService } from 'src/app/service/purchase.service';

@Component({
  selector: 'app-error-page',
  templateUrl: './error-page.component.html',
  styleUrls: ['./error-page.component.css'],
})
export class ErrorPageComponent implements OnInit {
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
    this.service.getOutcome(this.purchaseId).subscribe(
      response => this.message = response.message,
      _ => this.message = ''
    )
  }
}
