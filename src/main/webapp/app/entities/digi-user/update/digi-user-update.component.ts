import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { DigiUserFormService, DigiUserFormGroup } from './digi-user-form.service';
import { IDigiUser } from '../digi-user.model';
import { DigiUserService } from '../service/digi-user.service';
import { IPlayer } from 'app/entities/player/player.model';
import { PlayerService } from 'app/entities/player/service/player.service';

@Component({
  selector: 'jhi-digi-user-update',
  templateUrl: './digi-user-update.component.html',
})
export class DigiUserUpdateComponent implements OnInit {
  isSaving = false;
  digiUser: IDigiUser | null = null;

  playersCollection: IPlayer[] = [];

  editForm: DigiUserFormGroup = this.digiUserFormService.createDigiUserFormGroup();

  constructor(
    protected digiUserService: DigiUserService,
    protected digiUserFormService: DigiUserFormService,
    protected playerService: PlayerService,
    protected activatedRoute: ActivatedRoute
  ) {}

  comparePlayer = (o1: IPlayer | null, o2: IPlayer | null): boolean => this.playerService.comparePlayer(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ digiUser }) => {
      this.digiUser = digiUser;
      if (digiUser) {
        this.updateForm(digiUser);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const digiUser = this.digiUserFormService.getDigiUser(this.editForm);
    if (digiUser.id !== null) {
      this.subscribeToSaveResponse(this.digiUserService.update(digiUser));
    } else {
      this.subscribeToSaveResponse(this.digiUserService.create(digiUser));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDigiUser>>): void {
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

  protected updateForm(digiUser: IDigiUser): void {
    this.digiUser = digiUser;
    this.digiUserFormService.resetForm(this.editForm, digiUser);

    this.playersCollection = this.playerService.addPlayerToCollectionIfMissing<IPlayer>(this.playersCollection, digiUser.player);
  }

  protected loadRelationshipsOptions(): void {
    this.playerService
      .query({ filter: 'digiuser-is-null' })
      .pipe(map((res: HttpResponse<IPlayer[]>) => res.body ?? []))
      .pipe(map((players: IPlayer[]) => this.playerService.addPlayerToCollectionIfMissing<IPlayer>(players, this.digiUser?.player)))
      .subscribe((players: IPlayer[]) => (this.playersCollection = players));
  }
}
