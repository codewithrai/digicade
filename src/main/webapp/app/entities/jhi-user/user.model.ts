import { IPlayer } from '../player/player.model';

export interface IUser {
  id: number;
  login?: string | null;
  firstName?: string | null;
  lastName?: string | null;
  email?: string | null;
  imageUrl?: string | null;
  activated?: boolean | null;
}

export type NewUser = Omit<IUser, 'id'> & { id: null };
