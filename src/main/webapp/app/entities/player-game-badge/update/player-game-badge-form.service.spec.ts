import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../player-game-badge.test-samples';

import { PlayerGameBadgeFormService } from './player-game-badge-form.service';

describe('PlayerGameBadge Form Service', () => {
  let service: PlayerGameBadgeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PlayerGameBadgeFormService);
  });

  describe('Service methods', () => {
    describe('createPlayerGameBadgeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPlayerGameBadgeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            gameBadge: expect.any(Object),
            player: expect.any(Object),
          })
        );
      });

      it('passing IPlayerGameBadge should create a new form with FormGroup', () => {
        const formGroup = service.createPlayerGameBadgeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            gameBadge: expect.any(Object),
            player: expect.any(Object),
          })
        );
      });
    });

    describe('getPlayerGameBadge', () => {
      it('should return NewPlayerGameBadge for default PlayerGameBadge initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPlayerGameBadgeFormGroup(sampleWithNewData);

        const playerGameBadge = service.getPlayerGameBadge(formGroup) as any;

        expect(playerGameBadge).toMatchObject(sampleWithNewData);
      });

      it('should return NewPlayerGameBadge for empty PlayerGameBadge initial value', () => {
        const formGroup = service.createPlayerGameBadgeFormGroup();

        const playerGameBadge = service.getPlayerGameBadge(formGroup) as any;

        expect(playerGameBadge).toMatchObject({});
      });

      it('should return IPlayerGameBadge', () => {
        const formGroup = service.createPlayerGameBadgeFormGroup(sampleWithRequiredData);

        const playerGameBadge = service.getPlayerGameBadge(formGroup) as any;

        expect(playerGameBadge).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPlayerGameBadge should not enable id FormControl', () => {
        const formGroup = service.createPlayerGameBadgeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPlayerGameBadge should disable id FormControl', () => {
        const formGroup = service.createPlayerGameBadgeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
