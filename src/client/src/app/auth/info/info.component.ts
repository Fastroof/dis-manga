import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../_services/auth.service';
import {TokenStorageService} from '../../_services/token-storage.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-info',
  templateUrl: './info.component.html',
  styleUrls: ['./info.component.scss']
})
export class InfoComponent implements OnInit {

  constructor(private router: ActivatedRoute, private authService: AuthService, private tokenStorage: TokenStorageService) {}

  isLoggedIn = false;
  isLoginFailed = false;
  message = 'ERROR';

  ngOnInit(): void {
    this.authService.loginGoogle().subscribe(
      (data: any) => {
        this.tokenStorage.saveToken(data.token);
        this.tokenStorage.saveUser(data);
        this.isLoggedIn = true;
      },
      () => {
        this.isLoginFailed = true;
      }
    );
  }

}
