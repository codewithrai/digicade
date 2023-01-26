import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlayerGameBadge } from '../player-game-badge.model';

@Component({
  selector: 'jhi-player-game-badge-detail',
  templateUrl: './player-game-badge-detail.component.html',
})
export class PlayerGameBadgeDetailComponent implements OnInit {
  playerGameBadge: IPlayerGameBadge | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ playerGameBadge }) => {
      this.playerGameBadge = playerGameBadge;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
