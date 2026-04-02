import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpErrorResponse,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { ErrorModalService } from '../services/error-modal-service';

@Injectable({
  providedIn: 'root',
})

export class ErrorInterceptor implements HttpInterceptor {
  constructor(private errorModalService: ErrorModalService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        let errorMessage = 'Произошла неизвестная ошибка';

        if (error.error?.validationErrors) {
          errorMessage = Object.values(error.error.validationErrors).join(', ');
        } else if (error.error?.message) {
          errorMessage = error.error.message;
        } else if (error.message) {
          errorMessage = error.message;
        }

        this.errorModalService.showError(errorMessage);

        if (error.status === 401) {
        }

        return throwError(() => error);
      }),
    );
  }
}
