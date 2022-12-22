import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DigiUserFormService } from './digi-user-form.service';
import { DigiUserService } from '../service/digi-user.service';
import { IDigiUser } from '../digi-user.model';
import { IPlayer } from 'app/entities/player/player.model';
import { PlayerService } from 'app/entities/player/service/player.service';

import { DigiUserUpdateComponent } from './digi-user-update.component';

describe('DigiUser Management Update Component', () => {
  let comp: DigiUserUpdateComponent;
  let fixture: ComponentFixture<DigiUserUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let digiUserFormService: DigiUserFormService;
  let digiUserService: DigiUserService;
  let playerService: PlayerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DigiUserUpdateComponent],
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
      .overrideTemplate(DigiUserUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DigiUserUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    digiUserFormService = TestBed.inject(DigiUserFormService);
    digiUserService = TestBed.inject(DigiUserService);
    playerService = TestBed.inject(PlayerService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call player query and add missing value', () => {
      const digiUser: IDigiUser = { id: 456 };
      const player: IPlayer = { id: 64324 };
      digiUser.player = player;

      const playerCollection: IPlayer[] = [{ id: 79161 }];
      jest.spyOn(playerService, 'query').mockReturnValue(of(new HttpResponse({ body: playerCollection })));
      const expectedCollection: IPlayer[] = [player, ...playerCollection];
      jest.spyOn(playerService, 'addPlayerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ digiUser });
      comp.ngOnInit();

      expect(playerService.query).toHaveBeenCalled();
      expect(playerService.addPlayerToCollectionIfMissing).toHaveBeenCalledWith(playerCollection, player);
      expect(comp.playersCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const digiUser: IDigiUser = { id: 456 };
      const player: IPlayer = { id: 95058 };
      digiUser.player = player;

      activatedRoute.data = of({ digiUser });
      comp.ngOnInit();

      expect(comp.playersCollection).toContain(player);
      expect(comp.digiUser).toEqual(digiUser);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDigiUser>>();
      const digiUser = { id: 123 };
      jest.spyOn(digiUserFormService, 'getDigiUser').mockReturnValue(digiUser);
      jest.spyOn(digiUserService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ digiUser });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: digiUser }));
      saveSubject.complete();

      // THEN
      expect(digiUserFormService.getDigiUser).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(digiUserService.update).toHaveBeenCalledWith(expect.objectContaining(digiUser));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDigiUser>>();
      const digiUser = { id: 123 };
      jest.spyOn(digiUserFormService, 'getDigiUser').mockReturnValue({ id: null });
      jest.spyOn(digiUserService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ digiUser: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: digiUser }));
      saveSubject.complete();

      // THEN
      expect(digiUserFormService.getDigiUser).toHaveBeenCalled();
      expect(digiUserService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDigiUser>>();
      const digiUser = { id: 123 };
      jest.spyOn(digiUserService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ digiUser });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(digiUserService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
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
