import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { StoreService } from 'src/app/service/store.service';
import { Store } from '../../dto/store';

@Component({
  selector: 'app-store-page',
  templateUrl: './store-page.component.html',
  styleUrls: ['./store-page.component.css']
})
export class StorePageComponent implements OnInit {

  constructor(private route: ActivatedRoute, private service: StoreService) { }

  public store: Store | null = null;
  
  ngOnInit(): void {
    const id = this.getStoreId();
    this.get(id);
  }

  getStoreId(): number {
    return Number(this.route.snapshot.paramMap.get('id'));
  }

  get(id: number) {
    this.service.getById(id).subscribe(
      response => this.store = response
    )
  }
}
