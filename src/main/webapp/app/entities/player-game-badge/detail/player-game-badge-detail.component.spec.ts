import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PlayerGameBadgeDetailComponent } from './player-game-badge-detail.component';

describe('PlayerGameBadge Management Detail Component', () => {
  let comp: PlayerGameBadgeDetailComponent;
  let fixture: ComponentFixture<PlayerGameBadgeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PlayerGameBadgeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ playerGameBadge: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PlayerGameBadgeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PlayerGameBadgeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load playerGameBadge on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.playerGameBadge).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
