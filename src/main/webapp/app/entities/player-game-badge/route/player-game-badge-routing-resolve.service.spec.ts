import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IPlayerGameBadge } from '../player-game-badge.model';
import { PlayerGameBadgeService } from '../service/player-game-badge.service';

import { PlayerGameBadgeRoutingResolveService } from './player-game-badge-routing-resolve.service';

describe('PlayerGameBadge routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: PlayerGameBadgeRoutingResolveService;
  let service: PlayerGameBadgeService;
  let resultPlayerGameBadge: IPlayerGameBadge | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(PlayerGameBadgeRoutingResolveService);
    service = TestBed.inject(PlayerGameBadgeService);
    resultPlayerGameBadge = undefined;
  });

  describe('resolve', () => {
    it('should return IPlayerGameBadge returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPlayerGameBadge = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPlayerGameBadge).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPlayerGameBadge = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPlayerGameBadge).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IPlayerGameBadge>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPlayerGameBadge = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPlayerGameBadge).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
