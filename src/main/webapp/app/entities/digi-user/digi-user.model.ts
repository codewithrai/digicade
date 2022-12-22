import dayjs from 'dayjs/esm';
import { IPlayer } from 'app/entities/player/player.model';

export interface IDigiUser {
  id: number;
  firstName?: string | null;
  lastName?: string | null;
  userName?: string | null;
  email?: string | null;
  dob?: dayjs.Dayjs | null;
  age?: number | null;
  promoCode?: string | null;
  player?: Pick<IPlayer, 'id'> | null;
}

export type NewDigiUser = Omit<IDigiUser, 'id'> & { id: null };
