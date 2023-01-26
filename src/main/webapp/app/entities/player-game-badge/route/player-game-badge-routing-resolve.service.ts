import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPlayerGameBadge } from '../player-game-badge.model';
import { PlayerGameBadgeService } from '../service/player-game-badge.service';

@Injectable({ providedIn: 'root' })
export class PlayerGameBadgeRoutingResolveService implements Resolve<IPlayerGameBadge | null> {
  constructor(protected service: PlayerGameBadgeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlayerGameBadge | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((playerGameBadge: HttpResponse<IPlayerGameBadge>) => {
          if (playerGameBadge.body) {
            return of(playerGameBadge.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
