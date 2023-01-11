import { NgModule, LOCALE_ID } from '@angular/core';
import { registerLocaleData } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import locale from '@angular/common/locales/en';
import { BrowserModule, Title } from '@angular/platform-browser';
import { ServiceWorkerModule } from '@angular/service-worker';
import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { NgxWebstorageModule } from 'ngx-webstorage';
import dayjs from 'dayjs/esm';
import { NgbDateAdapter, NgbDatepickerConfig } from '@ng-bootstrap/ng-bootstrap';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import './config/dayjs';
import { SharedModule } from 'app/shared/shared.module';
import { AppRoutingModule } from './app-routing.module';
import { HomeModule } from './home/home.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { NgbDateDayjsAdapter } from './config/datepicker-adapter';
import { fontAwesomeIcons } from './config/font-awesome-icons';
import { httpInterceptorProviders } from 'app/core/interceptor/index';
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';
import { AppComponent } from './app/app.component';
import { HeaderComponent } from './layouts/header/header.component';
import { AppFooterComponent } from './app-footer/app-footer.component';
import { AppLoginComponent } from './app-login/app-login.component';
import { AppRegisterComponent } from './app-register/app-register.component';
import { AppCarouselComponent } from './app-carousel/app-carousel.component';
import { CarouselModule } from 'ngx-owl-carousel-o';
import { FacebookLoginProvider, GoogleLoginProvider, SocialAuthServiceConfig, SocialLoginModule } from '@abacritt/angularx-social-login';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AdminPanelComponent } from './admin-panel/admin-panel.component';
import { SidebarComponent } from './layouts/sidebar/sidebar.component';
import { PlayerComponent } from './entities/player/list/player.component';
import { PlayerDetailComponent } from './entities/player/detail/player-detail.component';
import { PlayerUpdateComponent } from './entities/player/update/player-update.component';
import { PlayerDeleteDialogComponent } from './entities/player/delete/player-delete-dialog.component';
import { UserComponent } from './entities/jhi-user/list/user.component';
import { UserDetailComponent } from './entities/jhi-user/detail/user-detail.component';
import { UserUpdateComponent } from './entities/jhi-user/update/user-update.component';
import { GameComponent } from './entities/game/list/game.component';
import { GameDetailComponent } from './entities/game/detail/game-detail.component';
import { GameUpdateComponent } from './entities/game/update/game-update.component';
import { UserDeleteComponent } from './entities/jhi-user/delete/user-delete.component';
import { NotFoundComponent } from './layouts/error/not-found.component';

@NgModule({
  imports: [
    BrowserModule,
    SharedModule,
    HomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    AppRoutingModule,
    // Set this to true to enable service worker (PWA)
    ServiceWorkerModule.register('ngsw-worker.js', { enabled: false }),
    HttpClientModule,
    NgxWebstorageModule.forRoot({ prefix: 'jhi', separator: '-', caseSensitive: true }),
    BrowserAnimationsModule,
    CarouselModule,
    SocialLoginModule,
  ],
  providers: [
    Title,
    { provide: LOCALE_ID, useValue: 'en' },
    { provide: NgbDateAdapter, useClass: NgbDateDayjsAdapter },
    httpInterceptorProviders,
    {
      provide: 'SocialAuthServiceConfig',
      useValue: {
        autoLogin: false,
        providers: [
          {
            id: GoogleLoginProvider.PROVIDER_ID,
            provider: new GoogleLoginProvider('773632708179-psoan60e5kvdiiv5k29gj42u9hf29l18.apps.googleusercontent.com'),
          },
          {
            id: FacebookLoginProvider.PROVIDER_ID,
            provider: new FacebookLoginProvider('501032318451653'),
          },
        ],
        onError: err => {
          console.error(err);
        },
      } as SocialAuthServiceConfig,
    },
  ],
  declarations: [
    MainComponent,
    NavbarComponent,
    ErrorComponent,
    NotFoundComponent,
    PageRibbonComponent,
    FooterComponent,
    AppComponent,
    HeaderComponent,
    AppFooterComponent,
    AppLoginComponent,
    AppRegisterComponent,
    AppCarouselComponent,
    AdminPanelComponent,
    SidebarComponent,
    PlayerComponent,
    PlayerDetailComponent,
    PlayerUpdateComponent,
    PlayerDeleteDialogComponent,
    UserComponent,
    UserDetailComponent,
    UserUpdateComponent,
    UserDeleteComponent,
    GameComponent,
    GameDetailComponent,
    GameUpdateComponent,
  ],
  bootstrap: [MainComponent],
  exports: [NavbarComponent, SidebarComponent],
})
export class AppModule {
  constructor(applicationConfigService: ApplicationConfigService, iconLibrary: FaIconLibrary, dpConfig: NgbDatepickerConfig) {
    applicationConfigService.setEndpointPrefix(SERVER_API_URL);
    registerLocaleData(locale);
    iconLibrary.addIcons(...fontAwesomeIcons);
    dpConfig.minDate = { year: dayjs().subtract(100, 'year').year(), month: 1, day: 1 };
  }
}
