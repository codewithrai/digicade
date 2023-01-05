import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'panel/digi-user',
        data: { pageTitle: 'DigiUsers' },
        loadChildren: () => import('./digi-user/digi-user.module').then(m => m.DigiUserModule),
      },
      {
        path: 'panel/player',
        data: { pageTitle: 'Players' },
        loadChildren: () => import('./player/player.module').then(m => m.PlayerModule),
      },
      {
        path: 'panel/game-badge',
        data: { pageTitle: 'GameBadges' },
        loadChildren: () => import('./game-badge/game-badge.module').then(m => m.GameBadgeModule),
      },
      {
        path: 'panel/game-level',
        data: { pageTitle: 'GameLevels' },
        loadChildren: () => import('./game-level/game-level.module').then(m => m.GameLevelModule),
      },
      {
        path: 'panel/game',
        data: { pageTitle: 'Games' },
        loadChildren: () => import('./game/game.module').then(m => m.GameModule),
      },
      {
        path: 'panel/game-score',
        data: { pageTitle: 'GameScores' },
        loadChildren: () => import('./game-score/game-score.module').then(m => m.GameScoreModule),
      },
      {
        path: 'panel/high-score',
        data: { pageTitle: 'HighScores' },
        loadChildren: () => import('./high-score/high-score.module').then(m => m.HighScoreModule),
      },
      {
        path: 'panel/coin-package',
        data: { pageTitle: 'CoinPackages' },
        loadChildren: () => import('./coin-package/coin-package.module').then(m => m.CoinPackageModule),
      },
      {
        path: 'panel/transaction',
        data: { pageTitle: 'Transactions' },
        loadChildren: () => import('./transaction/transaction.module').then(m => m.TransactionModule),
      },
      {
        path: 'panel/coupon-reward',
        data: { pageTitle: 'CouponRewards' },
        loadChildren: () => import('./coupon-reward/coupon-reward.module').then(m => m.CouponRewardModule),
      },
      {
        path: 'panel/coupon-image',
        data: { pageTitle: 'CouponImages' },
        loadChildren: () => import('./coupon-image/coupon-image.module').then(m => m.CouponImageModule),
      },
      {
        path: 'panel/player-coupon-reward',
        data: { pageTitle: 'PlayerCouponRewards' },
        loadChildren: () => import('./player-coupon-reward/player-coupon-reward.module').then(m => m.PlayerCouponRewardModule),
      },
      {
        path: 'panel/nft-reward',
        data: { pageTitle: 'NftRewards' },
        loadChildren: () => import('./nft-reward/nft-reward.module').then(m => m.NftRewardModule),
      },
      {
        path: 'panel/player-nft-reward',
        data: { pageTitle: 'PlayerNftRewards' },
        loadChildren: () => import('./player-nft-reward/player-nft-reward.module').then(m => m.PlayerNftRewardModule),
      },
      {
        path: 'panel/daily-reward',
        data: { pageTitle: 'DailyRewards' },
        loadChildren: () => import('./daily-reward/daily-reward.module').then(m => m.DailyRewardModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
