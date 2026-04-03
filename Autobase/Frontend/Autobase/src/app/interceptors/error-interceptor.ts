import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { ErrorModalService } from '../services/error-modal-service';

@Injectable({ providedIn: 'root' })
export class ErrorInterceptor implements HttpInterceptor {
  private readonly errorModalService = inject(ErrorModalService);

  intercept(req: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        let errorMessage = 'Произошла неизвестная ошибка';

        if (error.error?.validationErrors) {
          const errors = Object.values(error.error.validationErrors);
          errorMessage = errors.join(', ');
        } else if (error.error?.message) {
          errorMessage = error.error.message;
        } else if (error.message) {
          errorMessage = error.message;
        }

        this.errorModalService.showError(errorMessage);

        return throwError(() => error);
      }),
    );
  }
}
