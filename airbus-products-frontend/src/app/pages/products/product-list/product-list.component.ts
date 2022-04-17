import { Component, EventEmitter, Input, Output } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { productGridColumns } from '../products.constants';
import { PagenatedProduct, Product } from '../products.model';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss']
})
export class ProductListComponent {

  @Input() pagenatedProducts!: PagenatedProduct;

  @Output() updateEmmiter: EventEmitter<Product> = new EventEmitter();
  @Output() deleteEmmiter: EventEmitter<number> = new EventEmitter();
  @Output() addEmmiter: EventEmitter<void> = new EventEmitter();
  @Output() pageEmmiter: EventEmitter<number> = new EventEmitter();

  displayedColumns = productGridColumns;

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
    this.pageEmmiter.emit(event.pageIndex);
  }

}
