import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPlayerGameBadge, NewPlayerGameBadge } from '../player-game-badge.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPlayerGameBadge for edit and NewPlayerGameBadgeFormGroupInput for create.
 */
type PlayerGameBadgeFormGroupInput = IPlayerGameBadge | PartialWithRequiredKeyOf<NewPlayerGameBadge>;

type PlayerGameBadgeFormDefaults = Pick<NewPlayerGameBadge, 'id'>;

type PlayerGameBadgeFormGroupContent = {
  id: FormControl<IPlayerGameBadge['id'] | NewPlayerGameBadge['id']>;
  gameBadge: FormControl<IPlayerGameBadge['gameBadge']>;
  player: FormControl<IPlayerGameBadge['player']>;
};

export type PlayerGameBadgeFormGroup = FormGroup<PlayerGameBadgeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PlayerGameBadgeFormService {
  createPlayerGameBadgeFormGroup(playerGameBadge: PlayerGameBadgeFormGroupInput = { id: null }): PlayerGameBadgeFormGroup {
    const playerGameBadgeRawValue = {
      ...this.getFormDefaults(),
      ...playerGameBadge,
    };
    return new FormGroup<PlayerGameBadgeFormGroupContent>({
      id: new FormControl(
        { value: playerGameBadgeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      gameBadge: new FormControl(playerGameBadgeRawValue.gameBadge),
      player: new FormControl(playerGameBadgeRawValue.player),
    });
  }

  getPlayerGameBadge(form: PlayerGameBadgeFormGroup): IPlayerGameBadge | NewPlayerGameBadge {
    return form.getRawValue() as IPlayerGameBadge | NewPlayerGameBadge;
  }

  resetForm(form: PlayerGameBadgeFormGroup, playerGameBadge: PlayerGameBadgeFormGroupInput): void {
    const playerGameBadgeRawValue = { ...this.getFormDefaults(), ...playerGameBadge };
    form.reset(
      {
        ...playerGameBadgeRawValue,
        id: { value: playerGameBadgeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PlayerGameBadgeFormDefaults {
    return {
      id: null,
    };
  }
}
