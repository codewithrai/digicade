import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PlayerGameBadgeFormService, PlayerGameBadgeFormGroup } from './player-game-badge-form.service';
import { IPlayerGameBadge } from '../player-game-badge.model';
import { PlayerGameBadgeService } from '../service/player-game-badge.service';
import { IGameBadge } from 'app/entities/game-badge/game-badge.model';
import { GameBadgeService } from 'app/entities/game-badge/service/game-badge.service';
import { IPlayer } from 'app/entities/player/player.model';
import { PlayerService } from 'app/entities/player/service/player.service';

@Component({
  selector: 'jhi-player-game-badge-update',
  templateUrl: './player-game-badge-update.component.html',
})
export class PlayerGameBadgeUpdateComponent implements OnInit {
  isSaving = false;
  playerGameBadge: IPlayerGameBadge | null = null;

  gameBadgesSharedCollection: IGameBadge[] = [];
  playersSharedCollection: IPlayer[] = [];

  editForm: PlayerGameBadgeFormGroup = this.playerGameBadgeFormService.createPlayerGameBadgeFormGroup();

  constructor(
    protected playerGameBadgeService: PlayerGameBadgeService,
    protected playerGameBadgeFormService: PlayerGameBadgeFormService,
    protected gameBadgeService: GameBadgeService,
    protected playerService: PlayerService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareGameBadge = (o1: IGameBadge | null, o2: IGameBadge | null): boolean => this.gameBadgeService.compareGameBadge(o1, o2);

  comparePlayer = (o1: IPlayer | null, o2: IPlayer | null): boolean => this.playerService.comparePlayer(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ playerGameBadge }) => {
      this.playerGameBadge = playerGameBadge;
      if (playerGameBadge) {
        this.updateForm(playerGameBadge);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const playerGameBadge = this.playerGameBadgeFormService.getPlayerGameBadge(this.editForm);
    if (playerGameBadge.id !== null) {
      this.subscribeToSaveResponse(this.playerGameBadgeService.update(playerGameBadge));
    } else {
      this.subscribeToSaveResponse(this.playerGameBadgeService.create(playerGameBadge));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlayerGameBadge>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(playerGameBadge: IPlayerGameBadge): void {
    this.playerGameBadge = playerGameBadge;
    this.playerGameBadgeFormService.resetForm(this.editForm, playerGameBadge);

    this.gameBadgesSharedCollection = this.gameBadgeService.addGameBadgeToCollectionIfMissing<IGameBadge>(
      this.gameBadgesSharedCollection,
      playerGameBadge.gameBadge
    );
    this.playersSharedCollection = this.playerService.addPlayerToCollectionIfMissing<IPlayer>(
      this.playersSharedCollection,
      playerGameBadge.player
    );
  }

  protected loadRelationshipsOptions(): void {
    this.gameBadgeService
      .query()
      .pipe(map((res: HttpResponse<IGameBadge[]>) => res.body ?? []))
      .pipe(
        map((gameBadges: IGameBadge[]) =>
          this.gameBadgeService.addGameBadgeToCollectionIfMissing<IGameBadge>(gameBadges, this.playerGameBadge?.gameBadge)
        )
      )
      .subscribe((gameBadges: IGameBadge[]) => (this.gameBadgesSharedCollection = gameBadges));

    this.playerService
      .query()
      .pipe(map((res: HttpResponse<IPlayer[]>) => res.body ?? []))
      .pipe(map((players: IPlayer[]) => this.playerService.addPlayerToCollectionIfMissing<IPlayer>(players, this.playerGameBadge?.player)))
      .subscribe((players: IPlayer[]) => (this.playersSharedCollection = players));
  }
}
