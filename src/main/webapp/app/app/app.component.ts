import { Component, OnInit } from '@angular/core';
import { AccountService } from '../core/auth/account.service';
import { Router } from '@angular/router';

@Component({
  selector: 'jhi-app',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
  isLogin: boolean = false;

  constructor(private accountService: AccountService, private router: Router) {}

  ngOnInit(): void {
    if (this.accountService.isAuthenticated()) {
      this.isLogin = true;
    }
  }

  startJourney(): void {
    if (this.isLogin) {
      this.router.navigate(['/games']);
    } else {
      this.router.navigate(['/login']);
    }
  }
}
