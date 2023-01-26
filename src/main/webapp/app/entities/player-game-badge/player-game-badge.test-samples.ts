import { IPlayerGameBadge, NewPlayerGameBadge } from './player-game-badge.model';

export const sampleWithRequiredData: IPlayerGameBadge = {
  id: 20666,
};

export const sampleWithPartialData: IPlayerGameBadge = {
  id: 87300,
};

export const sampleWithFullData: IPlayerGameBadge = {
  id: 1641,
};

export const sampleWithNewData: NewPlayerGameBadge = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
