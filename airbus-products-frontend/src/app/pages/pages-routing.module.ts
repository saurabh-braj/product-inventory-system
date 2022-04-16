import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PagesComponent } from './pages.component';
import { ProductsComponent } from './products/products.component';

const routes: Routes = [
  { path: '', component: PagesComponent,
  children: [
    { path: 'products', component: ProductsComponent}
  ]}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PagesRoutingModule { }
