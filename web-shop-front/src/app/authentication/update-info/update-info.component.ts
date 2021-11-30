import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from '../service/auth.service';
import { UserInfoDTO } from '../dto/UserInfoDTO';
import { StoreNameDTO } from '../dto/StoreNameDTO';

@Component({
  selector: 'app-update-info',
  templateUrl: './update-info.component.html',
  styleUrls: ['./update-info.component.css'],
})
export class UpdateInfoComponent implements OnInit {
  dto: UserInfoDTO = new UserInfoDTO('', '', '', '');
  store = false;
  storeName = '';
  constructor(private _snackBar: MatSnackBar, private _auth: AuthService) {}

  ngOnInit(): void {
    this._auth.getInfo().subscribe((response) => {
      console.log(response);
      this.dto = response;
      console.log(this.dto);
      if (this._auth.getRole() === 'SELLER') {
        this.store = true;
        this._auth
          .getStoreNameInfo(this._auth.getId())
          .subscribe((response) => {
            this.storeName = response.name;
          });
      }
    });
  }

  update() {
    this._auth.updateInfo(this.dto).subscribe((response) => {
      this.openSnackBar('Updated.', 'Ok');
    });
  }

  updateStoreName() {
    this._auth
      .setStoreNameInfo(new StoreNameDTO(this.storeName))
      .subscribe((response) => {
        console.log(response);
        this.openSnackBar('Store name updated.', 'Ok');
      });
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 10000,
    });
  }
}
