import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGameBadge } from '../game-badge.model';

@Component({
  selector: 'jhi-game-badge-detail',
  templateUrl: './game-badge-detail.component.html',
  styleUrls: ['./game-badge-detail.component.css'],
})
export class GameBadgeDetailComponent implements OnInit {
  gameBadge: IGameBadge | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gameBadge }) => {
      this.gameBadge = gameBadge;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
