import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Product, ProductCategory, productFields } from '../products.model';
import { ProductsService } from '../products.service';

@Component({
  selector: 'app-product-filter',
  templateUrl: './product-filter.component.html',
  styleUrls: ['./product-filter.component.scss']
})
export class ProductFilterComponent implements OnInit {

  @Input()
  productCategory!: ProductCategory[];

  @Output() filterEmmiter: EventEmitter<Product> = new EventEmitter();
  @Output() resetEmmiter: EventEmitter<void> = new EventEmitter();

  filterForm!: FormGroup;

  constructor(private productService: ProductsService) { }

  ngOnInit(): void {
    this.filterForm = this.productService.initializeFilterForm();
    this.filterForm.valueChanges.subscribe(() => {
      this.onFilterData();
    })
  }

  onFilterData() {
    let product: Product = {} as Product;
    product.productId = this.filterForm.get(productFields.PRODUCT_ID)?.value;
    product.name = this.filterForm.get(productFields.NAME)?.value;
    product.description = this.filterForm.get(productFields.DESCRIPTION)?.value;
    if (this.filterForm && this.filterForm.get(productFields.CATEGORY)?.value > 0) {
      product.category = {} as ProductCategory;
      product.category.id = +this.filterForm.get(productFields.CATEGORY)?.value;
    }
    this.filterEmmiter.emit(product)
  }

  onClearFilters() {
    this.filterForm.reset();
    this.filterForm.get(productFields.CATEGORY)?.setValue(-1);
    this.resetEmmiter.emit()
  }

}
