import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Store } from '../../dto/store';

@Component({
  selector: 'app-store-card',
  templateUrl: './store-card.component.html',
  styleUrls: ['./store-card.component.css']
})
export class StoreCardComponent implements OnInit {

  constructor(private router: Router) { }

  @Input() store: Store | null = null;
  
  ngOnInit(): void {
  }

  visit() {
    this.router.navigate(['store/' + this.store?.id])
  }
}
