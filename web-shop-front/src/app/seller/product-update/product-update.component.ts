import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { ServiceService } from 'src/app/service/service.service';
import { Product } from '../DTOs/product';
import { ProductUpdateDTO } from '../DTOs/product-update-dto';

@Component({
  selector: 'app-product-update',
  templateUrl: './product-update.component.html',
  styleUrls: ['./product-update.component.css']
})
export class ProductUpdateComponent implements OnInit {

  nameOfProduct : string = '';
  price : number = 0;
  billingCycle : string = 'ONE_TIME';
  hasQuantity : boolean = true;
  quantity : number = 1;
  selectedFileHide = true;
  selectedFile: File = new File([],'');
  fileName = "";
  fileExtension = "";
  oldFileName = "";
  productId : any;

  constructor(private service : ServiceService, private route : ActivatedRoute, private _snackBar: MatSnackBar) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if(id!=null) {
      this.service.getProduct(id).subscribe(
        data => {
          this.nameOfProduct = data.name;
          this.price = data.price;
          this.billingCycle = data.billingCycle;
          this.hasQuantity = data.hasQuantity;
          this.quantity = data.quantity;
          this.oldFileName = data.imageUrl;
          this.productId = data.id;
      }
      );
    }
  }

  submit(){
    if(this.selectedFile.name != '')
    {
      const fd = new FormData();
      fd.append('imageFile', this.selectedFile,  this.selectedFile.name);
      var post =  new ProductUpdateDTO(this.productId,this.nameOfProduct,(this.price).toString(), this.billingCycle, this.hasQuantity, this.quantity);
      fd.append('post', JSON.stringify(post));

      this.service.newImageProductUpdate(fd).subscribe(
        (response) => {
          this.openSnackBar('Updated.', 'Ok');
        });
    }else 
    {
        this.service.updateProductInfo(new Product(this.productId,this.nameOfProduct,this.price, this.billingCycle, this.hasQuantity, this.quantity,this.oldFileName)).subscribe(
          (response) => {
            this.openSnackBar('Updated', 'Ok');
        });
    }
  }

  onFileSelected(event:any) {
    this.selectedFile = <File>event.target.files[0];
    this.selectedFileHide = false;
    this.fileName = "Data selected";
    console.log(this.selectedFile.name);
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 5000,
    });
  }
}
