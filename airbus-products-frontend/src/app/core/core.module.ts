import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import {MatCardModule} from '@angular/material/card';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatSortModule} from '@angular/material/sort';
import {MatTableModule} from '@angular/material/table';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

const MAT_MODULES = [MatCardModule, MatGridListModule, MatTableModule, MatPaginatorModule, MatSortModule]


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MAT_MODULES,
    NgbModule,
    FormsModule
  ], exports: [
    CommonModule,
    MAT_MODULES,
    ReactiveFormsModule,
    NgbModule,
    FormsModule
  ]
})
export class CoreModule { }
