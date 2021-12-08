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
  storeDto: StoreNameDTO = new StoreNameDTO('', null);
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
            this.storeDto = response;
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
      .setStoreNameInfo({name: this.storeDto.name})
      .subscribe((response) => {
        console.log(response);
        this.openSnackBar('Store name updated.', 'Ok');
      });
  }

  setToken() {
    this._auth.setToken(this.storeDto.apiToken).subscribe(
      _ => this.openSnackBar("Token successfully set", "Ok")
    )
  }

  disable() {
    this._auth.deleteToken().subscribe(
      _ => {
        this.openSnackBar("You disabled payment services", "Ok");
        this.storeDto.apiToken = null;
      }
    )
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 10000,
    });
  }
}
