import { Component, OnInit } from '@angular/core';
import { GameService } from '../entities/game/service/game.service';
import { Router } from '@angular/router';
import { AccountService } from '../core/auth/account.service';
import { IGame } from '../entities/game/game.model';

@Component({
  selector: 'jhi-games',
  templateUrl: './games.component.html',
  styleUrls: ['./games.component.scss'],
})
export class GamesComponent implements OnInit {
  games?: IGame[] | null;

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
