import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPlayerGameBadge, NewPlayerGameBadge } from '../player-game-badge.model';

export type PartialUpdatePlayerGameBadge = Partial<IPlayerGameBadge> & Pick<IPlayerGameBadge, 'id'>;

export type EntityResponseType = HttpResponse<IPlayerGameBadge>;
export type EntityArrayResponseType = HttpResponse<IPlayerGameBadge[]>;

@Injectable({ providedIn: 'root' })
export class PlayerGameBadgeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/player-game-badges');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(playerGameBadge: NewPlayerGameBadge): Observable<EntityResponseType> {
    return this.http.post<IPlayerGameBadge>(this.resourceUrl, playerGameBadge, { observe: 'response' });
  }

  update(playerGameBadge: IPlayerGameBadge): Observable<EntityResponseType> {
    return this.http.put<IPlayerGameBadge>(`${this.resourceUrl}/${this.getPlayerGameBadgeIdentifier(playerGameBadge)}`, playerGameBadge, {
      observe: 'response',
    });
  }

  partialUpdate(playerGameBadge: PartialUpdatePlayerGameBadge): Observable<EntityResponseType> {
    return this.http.patch<IPlayerGameBadge>(`${this.resourceUrl}/${this.getPlayerGameBadgeIdentifier(playerGameBadge)}`, playerGameBadge, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPlayerGameBadge>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPlayerGameBadge[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPlayerGameBadgeIdentifier(playerGameBadge: Pick<IPlayerGameBadge, 'id'>): number {
    return playerGameBadge.id;
  }

  comparePlayerGameBadge(o1: Pick<IPlayerGameBadge, 'id'> | null, o2: Pick<IPlayerGameBadge, 'id'> | null): boolean {
    return o1 && o2 ? this.getPlayerGameBadgeIdentifier(o1) === this.getPlayerGameBadgeIdentifier(o2) : o1 === o2;
  }

  addPlayerGameBadgeToCollectionIfMissing<Type extends Pick<IPlayerGameBadge, 'id'>>(
    playerGameBadgeCollection: Type[],
    ...playerGameBadgesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const playerGameBadges: Type[] = playerGameBadgesToCheck.filter(isPresent);
    if (playerGameBadges.length > 0) {
      const playerGameBadgeCollectionIdentifiers = playerGameBadgeCollection.map(
        playerGameBadgeItem => this.getPlayerGameBadgeIdentifier(playerGameBadgeItem)!
      );
      const playerGameBadgesToAdd = playerGameBadges.filter(playerGameBadgeItem => {
        const playerGameBadgeIdentifier = this.getPlayerGameBadgeIdentifier(playerGameBadgeItem);
        if (playerGameBadgeCollectionIdentifiers.includes(playerGameBadgeIdentifier)) {
          return false;
        }
        playerGameBadgeCollectionIdentifiers.push(playerGameBadgeIdentifier);
        return true;
      });
      return [...playerGameBadgesToAdd, ...playerGameBadgeCollection];
    }
    return playerGameBadgeCollection;
  }
}
