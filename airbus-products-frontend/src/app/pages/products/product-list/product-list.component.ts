import { LiveAnnouncer } from '@angular/cdk/a11y';
import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { productGridColumns } from '../products.constants';
import { Product } from '../products.model';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss']
})
export class ProductListComponent implements OnInit, OnChanges {

  @Input() productsData!: Product[];
  @Input() totalProducts!: number;

  @Output() updateEmmiter: EventEmitter<Product> = new EventEmitter();
  @Output() deleteEmmiter: EventEmitter<number> = new EventEmitter();
  @Output() addEmmiter: EventEmitter<void> = new EventEmitter();
  @Output() pageEmmiter: EventEmitter<PageEvent> = new EventEmitter();

  @ViewChild(MatSort) sort!: MatSort;

  displayedColumns = productGridColumns;
  filteredProductsData!: Product[];

  constructor() { }
  
  ngOnChanges(changes: SimpleChanges): void {
    if (this.productsData) {
      this.filteredProductsData = this.productsData;
    }
  }

  ngOnInit(): void {
  }

  onUpdate(product: Product) {
    this.updateEmmiter.emit(product);
  }

  onDelete(id: number) {
    this.deleteEmmiter.emit(id);
  }

  addNewProduct() {
    this.addEmmiter.emit();
  }

  onPageChange(event: PageEvent) {
    const start = event.pageIndex * event.pageSize;
    const end = (event.pageIndex + 1) * event.pageSize;
    this.filteredProductsData = this.productsData.slice(start, end);
  }

}
