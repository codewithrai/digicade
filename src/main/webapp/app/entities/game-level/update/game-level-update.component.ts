import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { GameLevelFormService, GameLevelFormGroup } from './game-level-form.service';
import { IGameLevel } from '../game-level.model';
import { GameLevelService } from '../service/game-level.service';
import { IPlayer } from 'app/entities/player/player.model';
import { PlayerService } from 'app/entities/player/service/player.service';
import { IGame } from 'app/entities/game/game.model';
import { GameService } from 'app/entities/game/service/game.service';

@Component({
  selector: 'jhi-game-level-update',
  templateUrl: './game-level-update.component.html',
})
export class GameLevelUpdateComponent implements OnInit {
  isSaving = false;
  gameLevel: IGameLevel | null = null;

  playersSharedCollection: IPlayer[] = [];
  gamesSharedCollection: IGame[] = [];

  editForm: GameLevelFormGroup = this.gameLevelFormService.createGameLevelFormGroup();

  constructor(
    protected gameLevelService: GameLevelService,
    protected gameLevelFormService: GameLevelFormService,
    protected playerService: PlayerService,
    protected gameService: GameService,
    protected activatedRoute: ActivatedRoute
  ) {}

  comparePlayer = (o1: IPlayer | null, o2: IPlayer | null): boolean => this.playerService.comparePlayer(o1, o2);

  compareGame = (o1: IGame | null, o2: IGame | null): boolean => this.gameService.compareGame(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gameLevel }) => {
      this.gameLevel = gameLevel;
      if (gameLevel) {
        this.updateForm(gameLevel);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const gameLevel = this.gameLevelFormService.getGameLevel(this.editForm);
    if (gameLevel.id !== null) {
      this.subscribeToSaveResponse(this.gameLevelService.update(gameLevel));
    } else {
      this.subscribeToSaveResponse(this.gameLevelService.create(gameLevel));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGameLevel>>): void {
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

  protected updateForm(gameLevel: IGameLevel): void {
    this.gameLevel = gameLevel;
    this.gameLevelFormService.resetForm(this.editForm, gameLevel);

    this.playersSharedCollection = this.playerService.addPlayerToCollectionIfMissing<IPlayer>(
      this.playersSharedCollection,
      gameLevel.player
    );
    this.gamesSharedCollection = this.gameService.addGameToCollectionIfMissing<IGame>(this.gamesSharedCollection, gameLevel.game);
  }

  protected loadRelationshipsOptions(): void {
    this.playerService
      .query()
      .pipe(map((res: HttpResponse<IPlayer[]>) => res.body ?? []))
      .pipe(map((players: IPlayer[]) => this.playerService.addPlayerToCollectionIfMissing<IPlayer>(players, this.gameLevel?.player)))
      .subscribe((players: IPlayer[]) => (this.playersSharedCollection = players));

    this.gameService
      .query()
      .pipe(map((res: HttpResponse<IGame[]>) => res.body ?? []))
      .pipe(map((games: IGame[]) => this.gameService.addGameToCollectionIfMissing<IGame>(games, this.gameLevel?.game)))
      .subscribe((games: IGame[]) => (this.gamesSharedCollection = games));
  }
}
