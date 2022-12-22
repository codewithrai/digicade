export interface IPlayer {
  id: number;
  gamePlayCredits?: number | null;
  tix?: number | null;
  comp?: number | null;
  level?: number | null;
  walletAddress?: string | null;
}

export type NewPlayer = Omit<IPlayer, 'id'> & { id: null };
