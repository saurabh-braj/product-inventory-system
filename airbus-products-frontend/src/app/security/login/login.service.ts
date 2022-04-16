import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { map, mergeMap, Observable, of } from 'rxjs';
import { SECURITY_KEY } from 'src/app/constants';
import { environment } from 'src/environments/environment';
import { TokenModel, LoginForm, loginFields } from './login.model';
import { LocalStorage } from '@ngx-pwa/local-storage';
import * as moment from 'moment';
import jwt_decode from 'jwt-decode';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private formBuilder: FormBuilder, private http: HttpClient,
    private localstorage: LocalStorage, private router: Router) { }

  initializeLoginForm(): FormGroup {
    return this.formBuilder.group({
      [loginFields.USERNAME]: ['', Validators.compose([Validators.required, Validators.email])],
      [loginFields.PASSWORD]: ['', Validators.required]
    });
  }

  getLoginFormValues(loginForm: FormGroup): LoginForm {
    return {
      username: loginForm.get(loginFields.USERNAME)?.value,
      password: loginForm.get(loginFields.PASSWORD)?.value
    }
  }

  authenticateAndLogin(loginForm: LoginForm): Observable<TokenModel> {
    var formData: any = new FormData();
    formData.append('username', loginForm.username);
    formData.append('password', loginForm.password);
    const httpOptions = {
      headers: new HttpHeaders({
        InterceptorSkipHeader: ''
      })
    };
    return this.http.post<TokenModel>(`${environment.apiBaseUrl}/login`, formData, httpOptions)
  }


  findAccessTokenOrRedirect(): Observable<any> {
    return this.getToken().pipe(
      mergeMap((token: TokenModel) => (token && this.isTokenStillValid(token.expires_at)) ? of(token) : this.loginWithRefresh(token)),
      map((token: TokenModel) => token ? token : this.navigateToLoginPage())
    );
  }

  private isTokenStillValid(expire_date: string | undefined): boolean {
    return moment(expire_date).isAfter(moment());
  }

  public getToken(): Observable<any> {
    const token$ = this.localstorage.getItem<TokenModel>(SECURITY_KEY);
    return token$.pipe(map((token: any) => token ? this.populateToken(token) : null))
  }

  private populateToken(token: TokenModel): TokenModel {
    try {
      const decodedToken:any = jwt_decode(token.accessToken);
      const decodedRefreshToken: any = jwt_decode(token.refreshToken);
      token.expires_at = moment.unix(decodedToken.exp).format();
      token.refresh_expires_at = moment.unix(decodedRefreshToken.exp).format();
    } catch (e) {
    }
    return token;
  }

  public setToken(token: TokenModel): Observable<boolean> {
    return this.localstorage.setItem(SECURITY_KEY, token);
  }

  loginWithRefresh(token: TokenModel): Observable<any> {
    if (!token) {
      return this.navigateToLoginPage();
    }
    return this.isTokenStillValid(token.refresh_expires_at) ?
      this.http.get<TokenModel>(`${environment.apiBaseUrl}/unsecured/api/users/refresh-token`).pipe(
        mergeMap((rtoken: TokenModel) => {
          return this.refreshToken(rtoken).pipe(map(() => rtoken));
        })) : this.navigateToLoginPage();
  }

  private navigateToLoginPage() {
    return of(this.router.navigate(['/login']));
  }

  private refreshToken(rtoken: TokenModel): Observable<boolean> {
    return this.getToken().pipe(mergeMap((token: TokenModel) => {
      token.accessToken = rtoken.accessToken;
      token.refreshToken = rtoken.refreshToken;
      token.expires_in = rtoken.expires_in;
      token.refresh_expires_in = rtoken.expires_in;
      return this.setToken(token);
    }));
  }

  public logout() {
    this.localstorage.removeItem(SECURITY_KEY).subscribe(() => {
      this.navigateToLoginPage();
    });
  }

}
