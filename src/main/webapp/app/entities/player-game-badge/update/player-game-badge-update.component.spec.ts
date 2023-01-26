import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PlayerGameBadgeFormService } from './player-game-badge-form.service';
import { PlayerGameBadgeService } from '../service/player-game-badge.service';
import { IPlayerGameBadge } from '../player-game-badge.model';
import { IGameBadge } from 'app/entities/game-badge/game-badge.model';
import { GameBadgeService } from 'app/entities/game-badge/service/game-badge.service';
import { IPlayer } from 'app/entities/player/player.model';
import { PlayerService } from 'app/entities/player/service/player.service';

import { PlayerGameBadgeUpdateComponent } from './player-game-badge-update.component';

describe('PlayerGameBadge Management Update Component', () => {
  let comp: PlayerGameBadgeUpdateComponent;
  let fixture: ComponentFixture<PlayerGameBadgeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let playerGameBadgeFormService: PlayerGameBadgeFormService;
  let playerGameBadgeService: PlayerGameBadgeService;
  let gameBadgeService: GameBadgeService;
  let playerService: PlayerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PlayerGameBadgeUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PlayerGameBadgeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PlayerGameBadgeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    playerGameBadgeFormService = TestBed.inject(PlayerGameBadgeFormService);
    playerGameBadgeService = TestBed.inject(PlayerGameBadgeService);
    gameBadgeService = TestBed.inject(GameBadgeService);
    playerService = TestBed.inject(PlayerService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call GameBadge query and add missing value', () => {
      const playerGameBadge: IPlayerGameBadge = { id: 456 };
      const gameBadge: IGameBadge = { id: 18743 };
      playerGameBadge.gameBadge = gameBadge;

      const gameBadgeCollection: IGameBadge[] = [{ id: 24992 }];
      jest.spyOn(gameBadgeService, 'query').mockReturnValue(of(new HttpResponse({ body: gameBadgeCollection })));
      const additionalGameBadges = [gameBadge];
      const expectedCollection: IGameBadge[] = [...additionalGameBadges, ...gameBadgeCollection];
      jest.spyOn(gameBadgeService, 'addGameBadgeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ playerGameBadge });
      comp.ngOnInit();

      expect(gameBadgeService.query).toHaveBeenCalled();
      expect(gameBadgeService.addGameBadgeToCollectionIfMissing).toHaveBeenCalledWith(
        gameBadgeCollection,
        ...additionalGameBadges.map(expect.objectContaining)
      );
      expect(comp.gameBadgesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Player query and add missing value', () => {
      const playerGameBadge: IPlayerGameBadge = { id: 456 };
      const player: IPlayer = { id: 32728 };
      playerGameBadge.player = player;

      const playerCollection: IPlayer[] = [{ id: 58353 }];
      jest.spyOn(playerService, 'query').mockReturnValue(of(new HttpResponse({ body: playerCollection })));
      const additionalPlayers = [player];
      const expectedCollection: IPlayer[] = [...additionalPlayers, ...playerCollection];
      jest.spyOn(playerService, 'addPlayerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ playerGameBadge });
      comp.ngOnInit();

      expect(playerService.query).toHaveBeenCalled();
      expect(playerService.addPlayerToCollectionIfMissing).toHaveBeenCalledWith(
        playerCollection,
        ...additionalPlayers.map(expect.objectContaining)
      );
      expect(comp.playersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const playerGameBadge: IPlayerGameBadge = { id: 456 };
      const gameBadge: IGameBadge = { id: 93213 };
      playerGameBadge.gameBadge = gameBadge;
      const player: IPlayer = { id: 90561 };
      playerGameBadge.player = player;

      activatedRoute.data = of({ playerGameBadge });
      comp.ngOnInit();

      expect(comp.gameBadgesSharedCollection).toContain(gameBadge);
      expect(comp.playersSharedCollection).toContain(player);
      expect(comp.playerGameBadge).toEqual(playerGameBadge);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlayerGameBadge>>();
      const playerGameBadge = { id: 123 };
      jest.spyOn(playerGameBadgeFormService, 'getPlayerGameBadge').mockReturnValue(playerGameBadge);
      jest.spyOn(playerGameBadgeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ playerGameBadge });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: playerGameBadge }));
      saveSubject.complete();

      // THEN
      expect(playerGameBadgeFormService.getPlayerGameBadge).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(playerGameBadgeService.update).toHaveBeenCalledWith(expect.objectContaining(playerGameBadge));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlayerGameBadge>>();
      const playerGameBadge = { id: 123 };
      jest.spyOn(playerGameBadgeFormService, 'getPlayerGameBadge').mockReturnValue({ id: null });
      jest.spyOn(playerGameBadgeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ playerGameBadge: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: playerGameBadge }));
      saveSubject.complete();

      // THEN
      expect(playerGameBadgeFormService.getPlayerGameBadge).toHaveBeenCalled();
      expect(playerGameBadgeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlayerGameBadge>>();
      const playerGameBadge = { id: 123 };
      jest.spyOn(playerGameBadgeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ playerGameBadge });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(playerGameBadgeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareGameBadge', () => {
      it('Should forward to gameBadgeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(gameBadgeService, 'compareGameBadge');
        comp.compareGameBadge(entity, entity2);
        expect(gameBadgeService.compareGameBadge).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePlayer', () => {
      it('Should forward to playerService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(playerService, 'comparePlayer');
        comp.comparePlayer(entity, entity2);
        expect(playerService.comparePlayer).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
