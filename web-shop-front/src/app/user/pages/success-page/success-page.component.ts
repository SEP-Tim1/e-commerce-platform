import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PurchaseService } from 'src/app/service/purchase.service';
import { Purchase } from '../../dto/purchase';
import { PurchaseDetails } from '../../dto/purchase-details';

@Component({
  selector: 'app-success-page',
  templateUrl: './success-page.component.html',
  styleUrls: ['./success-page.component.css']
})
export class SuccessPageComponent implements OnInit {

  constructor(private route: ActivatedRoute, private service: PurchaseService, private router: Router) { }

  purchaseId = -1;
  purchase: Purchase = new Purchase(0, new PurchaseDetails('', '', '', '', ''), new Date(), [], '');

  ngOnInit(): void {
    this.purchaseId = this.getPurchaseId();
    this.service.success(this.purchaseId).subscribe(
      purchase => this.purchase = purchase
    )
  }

  getPurchaseId(): number {
    return Number(this.route.snapshot.paramMap.get('id'));
  }
}
