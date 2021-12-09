import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PurchaseService } from 'src/app/service/purchase.service';

@Component({
  selector: 'app-error-page',
  templateUrl: './error-page.component.html',
  styleUrls: ['./error-page.component.css']
})
export class ErrorPageComponent implements OnInit {

  constructor(private route: ActivatedRoute, private service: PurchaseService) { }

  purchaseId = -1;

  ngOnInit(): void {
    this.purchaseId = this.getPurchaseId();
    this.service.failure(this.purchaseId).subscribe(
      _ => console.log('ok')
    )
  }

  getPurchaseId(): number {
    return Number(this.route.snapshot.paramMap.get('id'));
  }

}
