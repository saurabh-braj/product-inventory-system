import { NgModule } from '@angular/core';
import { LoginComponent } from './login/login.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { CoreModule } from '../core/core.module';
import { LoginFormComponent } from './login/login-form/login-form.component';



@NgModule({
  declarations: [
    LoginComponent,
    PageNotFoundComponent,
    LoginFormComponent
  ],
  imports: [
    CoreModule
  ]
})
export class SecurityModule { }
