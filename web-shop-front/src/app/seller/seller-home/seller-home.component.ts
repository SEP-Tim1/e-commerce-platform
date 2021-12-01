import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ServiceService } from 'src/app/service/service.service';
import { environment } from 'src/environments/environment';
import { Product } from '../DTOs/product';

@Component({
  selector: 'app-seller-home',
  templateUrl: './seller-home.component.html',
  styleUrls: ['./seller-home.component.css']
})
export class SellerHomeComponent implements OnInit {

  products : any;
  imageObject : any;

  constructor(private service : ServiceService, private snackBar : MatSnackBar) { }

  ngOnInit(): void {
    this.service.getStoreProducts().subscribe(
      data => { this.products = data; this.constructSliderObjectsForPosts(); },
      error => this.openSnackBar(error.error.message)
    );
  }
  
  constructSliderObjectsForPosts() {
    for(const post of this.products) {
      const storyObject = new Array<Object>();
      storyObject.push( {image: environment.backend +'/' + post.imageUrl, thumbImage: environment.backend + '/'+ post.imageUrl});
      post['slider'] = storyObject;
      
    }
    
  }

  public delete(id : number){

  }

  openSnackBar(message: string) {
    this.snackBar.open(message, "Okay", {
      duration: 5000,
    });
  }
}
