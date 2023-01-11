import { Component, OnInit } from '@angular/core';
import { OwlOptions } from 'ngx-owl-carousel-o';
import { EntityArrayResponseType, GameService } from '../entities/game/service/game.service';
import { IGame } from '../entities/game/game.model';
import { Router } from '@angular/router';
import { AccountService } from '../core/auth/account.service';

@Component({
  selector: 'jhi-app-carousel',
  templateUrl: './app-carousel.component.html',
  styleUrls: ['./app-carousel.component.scss'],
})
export class AppCarouselComponent implements OnInit {
  games?: IGame[] | null;

  customOptions: OwlOptions = {
    autoplay: true,
    autoplayHoverPause: true,
    loop: true,
    mouseDrag: true,
    touchDrag: true,
    pullDrag: true,
    dots: false,
    navText: ['&#8249', '&#8250;'],
    nav: false,
    responsive: {
      0: {
        items: 1,
      },
      400: {
        items: 2,
      },
      760: {
        items: 3,
      },
      1000: {
        items: 4,
      },
    },
  };

  constructor(protected gameService: GameService, private router: Router, private accountService: AccountService) {}

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.gameService.get().subscribe({
      next: res => {
        console.log('game response', res.body);
        this.games = res.body;
      },
      error: err => {},
    });
  }

  playGame(): void {
    if (this.accountService.isAuthenticated()) {
      this.router.navigate(['/game']);
    } else {
      this.router.navigate(['/login']);
    }
  }
}
