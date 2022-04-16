import {
    HttpEvent,
    HttpHandler,
    HttpInterceptor,
    HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { mergeMap, Observable } from 'rxjs';
import { InterceptorSkipHeader } from '../constants';
import { TokenModel } from './login/login.model';
import { LoginService } from './login/login.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    constructor(private loginService: LoginService) { }

    intercept(req: HttpRequest<any>,next: HttpHandler): Observable<HttpEvent<any>> {
        if (req.headers.has(InterceptorSkipHeader)) {
            const headers = req.headers.delete(InterceptorSkipHeader);
            return next.handle(req.clone({ headers }));
        }
        return this.loginService.findAccessTokenOrRedirect().pipe(
            mergeMap((token: TokenModel) => {
                if (token) {
                    req = req.clone({
                        setHeaders: {
                            Authorization: `Bearer ${ token.accessToken }`,
                        },
                    });
                }
                return next.handle(req);
            })
        );
    }
}
