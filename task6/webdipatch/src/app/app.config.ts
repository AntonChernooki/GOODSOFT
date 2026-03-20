import {
  ApplicationConfig,
  importProvidersFrom,
  provideBrowserGlobalErrorListeners,
} from '@angular/core';
import { provideRouter } from '@angular/router';
import {
  HTTP_INTERCEPTORS,
  HttpClient,
  provideHttpClient,
  withInterceptorsFromDi,
} from '@angular/common/http';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { MessageService } from 'primeng/api';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { providePrimeNG } from 'primeng/config';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import LaraLightBlue from '@primeuix/themes/lara';
import { AuthInterceptor } from './interceptors/auth.interceptor';
import { routes } from './app.routes';
import { AuthService } from './service/auth-service';
import { provideAppInitializer, inject } from '@angular/core';



export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, '/assets/', '.json');
}

export const appConfig: ApplicationConfig = {
  providers: [
    provideAnimationsAsync(),
    providePrimeNG({
      theme: {
        preset: LaraLightBlue,
        options: {
          darkModeSelector: false,
        },
      },
    }),

    provideBrowserGlobalErrorListeners(),
    provideRouter(routes),
    provideHttpClient(withInterceptorsFromDi()),
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    },
    importProvidersFrom(
      TranslateModule.forRoot({
        loader: {
          provide: TranslateLoader,
          useFactory: HttpLoaderFactory,
          deps: [HttpClient],
        },
      }),
    ),

    provideAppInitializer(() => {
      const authService = inject(AuthService);
      return authService.restoreUserFromSession();
    }),

    MessageService,
  ],
};
