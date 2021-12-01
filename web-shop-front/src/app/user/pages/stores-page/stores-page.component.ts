import { Component, OnInit } from '@angular/core';
import { StoreService } from 'src/app/service/store.service';
import { Store } from '../../dto/store';

@Component({
  selector: 'app-stores-page',
  templateUrl: './stores-page.component.html',
  styleUrls: ['./stores-page.component.css']
})
export class StoresPageComponent implements OnInit {

  constructor(private service: StoreService) { }

  public stores: Store[] = [];

  ngOnInit(): void {
    this.getAll();
  }

  private getAll() {
    this.service.getAll().subscribe(
      response => this.stores = response
    )
  }
}
