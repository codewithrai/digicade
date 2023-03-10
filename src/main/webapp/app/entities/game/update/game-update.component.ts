import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { GameFormService, GameFormGroup } from './game-form.service';
import { IGame } from '../game.model';
import { GameService } from '../service/game.service';

@Component({
  selector: 'jhi-game-update',
  templateUrl: './game-update.component.html',
  styleUrls: ['./game-update.component.css'],
})
export class GameUpdateComponent implements OnInit {
  isSaving = false;
  game: IGame | null = null;
  base64Code?: any;
  file?: any;

  editForm: GameFormGroup = this.gameFormService.createGameFormGroup();

  constructor(protected gameService: GameService, protected gameFormService: GameFormService, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ game }) => {
      this.game = game;
      if (game) {
        this.updateForm(game);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const game = this.gameFormService.getGame(this.editForm);
    if (game.id !== null) {
      this.subscribeToSaveResponse(this.gameService.update(game));
    } else {
      this.subscribeToSaveResponse(this.gameService.create(game));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGame>>): void {
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

  protected updateForm(game: IGame): void {
    this.game = game;
    this.gameFormService.resetForm(this.editForm, game);
  }

  uploadImage(e: any): void {
    this.getBase64String(e);
  }

  getBase64String(event: any) {
    if (event.target.files && event.target.files[0]) {
      if (event.target.files[0].type) {
        this.file = event.target.files[0];
        var reader = new FileReader();
        reader.onload = (event: any) => {
          this.base64Code = event.target.result;
          this.editForm.get('image')?.setValue(event.target.result);
          this.editForm.value.image = event.target.result;
        };
        reader.readAsDataURL(event.target.files[0]);
      }
    }
  }
}
