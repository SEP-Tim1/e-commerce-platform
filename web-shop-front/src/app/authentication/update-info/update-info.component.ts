import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from '../service/auth.service';
import { UserInfoDTO } from '../dto/UserInfoDTO';

@Component({
  selector: 'app-update-info',
  templateUrl: './update-info.component.html',
  styleUrls: ['./update-info.component.css'],
})
export class UpdateInfoComponent implements OnInit {
  firstName: string = '';
  lastName: string = '';
  address: string = '';
  phone: string = '';
  dto: UserInfoDTO = new UserInfoDTO('', '', '', '');
  constructor(private _snackBar: MatSnackBar, private _auth: AuthService) {}

  ngOnInit(): void {
    this._auth.getInfo().subscribe((response) => {
      console.log(response);
      this.dto = response;
      console.log(this.dto);
    });
  }

  update() {
    this._auth.updateInfo(this.dto).subscribe((response) => {
      this.openSnackBar('Updated.', 'Ok');
    });
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 10000,
    });
  }
}
