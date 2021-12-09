import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PurchaseService } from 'src/app/service/purchase.service';

@Component({
  selector: 'app-success-page',
  templateUrl: './success-page.component.html',
  styleUrls: ['./success-page.component.css']
})
export class SuccessPageComponent implements OnInit {

  constructor(private route: ActivatedRoute, private service: PurchaseService) { }

  purchaseId = -1;

  ngOnInit(): void {
    this.purchaseId = this.getPurchaseId();
    this.service.success(this.purchaseId).subscribe(
      _ => console.log('ok')
    )
  }

  getPurchaseId(): number {
    return Number(this.route.snapshot.paramMap.get('id'));
  }
}
