import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginForm, TokenModel } from '../login.model';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss']
})
export class LoginFormComponent implements OnInit {

  loginForm!: FormGroup;
  visible = false;

  constructor(private loginService: LoginService,
    private router: Router ) { }

  ngOnInit(): void {
    this.loginForm = this.loginService.initializeLoginForm();
  }

  login() {
    const loginModel: LoginForm = this.loginService.getLoginFormValues(this.loginForm);
    this.loginService.authenticateAndLogin(loginModel).subscribe((res: TokenModel) => {
      this.loginService.setToken(res).subscribe((value: boolean) => {
        this.router.navigate(['/pages/products']);
      });
    }, err => this.handleError(err));
  }


  private handleError(err: HttpErrorResponse) {
    console.log(err);
  }

}
