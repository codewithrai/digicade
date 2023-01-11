export interface IGame {
  id: number;
  name?: string | null;
  image?: string | null;
  title?: string | null;
  url?: string | null;
  logoUrl?: string | null;
}

export type NewGame = Omit<IGame, 'id'> & { id: null };
