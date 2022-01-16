import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ServiceService } from 'src/app/service/service.service';
import { ProductDTO } from '../DTOs/product-dto';

@Component({
  selector: 'app-create-product',
  templateUrl: './create-product.component.html',
  styleUrls: ['./create-product.component.css']
})
export class CreateProductComponent implements OnInit {

  nameOfProduct : string = '';
  price : string = '';
  billingCycle : string = 'ONE_TIME';
  hasQuantity : boolean = true;
  quantity : number = 1;
  selectedFileHide = true;
  selectedFile: File = new File([],'');
  fileName = "";
  fileExtension = "";

  constructor(private service : ServiceService ,private _snackBar: MatSnackBar) { }

  ngOnInit(): void {
  }

  submit(){
    console.log('pritisnuto')
    const fd = new FormData();
    fd.append('imageFile', this.selectedFile,  this.selectedFile.name);
    console.log(this.selectedFile.name);
    var post =  new ProductDTO(this.nameOfProduct,this.price, this.billingCycle, this.hasQuantity, this.quantity);
    fd.append('post', JSON.stringify(post));
    console.log(post.name);

    this.service.postProduct(fd).subscribe(
      (response) => {
        this.openSnackBar('Created.', 'Ok');
      });
    
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
