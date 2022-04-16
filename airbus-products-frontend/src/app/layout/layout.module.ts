import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { CoreModule } from '../core/core.module';

const layoutComponents = [HeaderComponent, FooterComponent];

@NgModule({
  declarations: [ layoutComponents ],
  imports: [
    CommonModule,
    CoreModule
  ],
  exports: [ layoutComponents ]
})
export class LayoutModule { }
