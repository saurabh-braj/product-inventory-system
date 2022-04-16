import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PagesRoutingModule } from './pages-routing.module';
import { PagesComponent } from './pages.component';
import { ProductsComponent } from './products/products.component';
import { LayoutModule } from '../layout/layout.module';
import { CoreModule } from '../core/core.module';
import { NgbPagination } from '@ng-bootstrap/ng-bootstrap';
import { AddProductComponent } from './products/add-product/add-product.component';
import { ProductListComponent } from './products/product-list/product-list.component';
import { ConfirmationPopupComponent } from './products/confirmation-popup/confirmation-popup.component';
import { ProductFilterComponent } from './products/product-filter/product-filter.component';


@NgModule({
  declarations: [
    PagesComponent,
    ProductsComponent,
    AddProductComponent,
    ProductListComponent,
    ConfirmationPopupComponent,
    ProductFilterComponent,
  ],
  imports: [
    CommonModule,
    PagesRoutingModule,
    LayoutModule,
    CoreModule
  ]
})
export class PagesModule { }
