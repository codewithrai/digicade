import { IPlayer, NewPlayer } from './player.model';

export const sampleWithRequiredData: IPlayer = {
  id: 92110,
};

export const sampleWithPartialData: IPlayer = {
  id: 90690,
  gamePlayCredits: 22348,
  comp: 24081,
  level: 70291,
};

export const sampleWithFullData: IPlayer = {
  id: 28559,
  gamePlayCredits: 3535,
  tix: 8595,
  comp: 97222,
  level: 47305,
  walletAddress: 'Somalia 1080p',
};

export const sampleWithNewData: NewPlayer = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
