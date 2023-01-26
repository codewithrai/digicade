import { IGameBadge } from 'app/entities/game-badge/game-badge.model';
import { IPlayer } from 'app/entities/player/player.model';

export interface IPlayerGameBadge {
  id: number;
  gameBadge?: Pick<IGameBadge, 'id'> | null;
  player?: Pick<IPlayer, 'id'> | null;
}

export type NewPlayerGameBadge = Omit<IPlayerGameBadge, 'id'> & { id: null };
