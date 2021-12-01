import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
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

  constructor(private service : ServiceService, private snackBar : MatSnackBar, private router : Router) { }

  ngOnInit(): void {
    this.getStoreProducts(); 
  }
  
  getStoreProducts(){
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
    this.service.deleteProduct(id).subscribe(
      (response) => {
        this.openSnackBar('You deleted product.');
        this.getStoreProducts();
      }
    );
  }

  public update(productId : number){
    this.router.navigate(['./product-update/' + productId]);
  }

  openSnackBar(message: string) {
    this.snackBar.open(message, "Okay", {
      duration: 5000,
    });
  }
}
