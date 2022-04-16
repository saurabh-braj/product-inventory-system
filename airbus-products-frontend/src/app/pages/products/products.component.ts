import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { NgbModal, NgbModalConfig, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AddProductComponent } from './add-product/add-product.component';
import { ConfirmationPopupComponent } from './confirmation-popup/confirmation-popup.component';
import { ProductCategory, Product } from './products.model';
import { ProductsService } from './products.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.scss']
})
export class ProductsComponent implements OnInit {

  products: Product[] = [];
  productCategory!: ProductCategory[];
  totalProducts!: number;

  constructor(private productsService: ProductsService,
    config: NgbModalConfig, private modalService: NgbModal) {
      config.backdrop = 'static';
      config.keyboard = false;
     }

  ngOnInit(): void {
    this.getProducts();
    this.getProductCategories();
  }

  getProductCategories() {
    this.productsService.getProductCategories().subscribe((res: ProductCategory[]) => {
      this.productCategory = res;
    });
  }

  getProducts() {
    this.productsService.getProducts().subscribe((data: Product[]) => {
      this.products = data;
      this.totalProducts = this.products.length;
    });
  }

  addProduct() {
    const modalRef: NgbModalRef = this.modalService.open(AddProductComponent, { centered: true });
    (modalRef.componentInstance as AddProductComponent).header = 'Add New Product';
    (modalRef.componentInstance as AddProductComponent).productCategory = this.productCategory;
    modalRef.result.then(() => this.getProducts());
  }

  update(product: Product) {
    const modalRef: NgbModalRef = this.modalService.open(AddProductComponent, { centered: true });
    (modalRef.componentInstance as AddProductComponent).product = product;
    (modalRef.componentInstance as AddProductComponent).header = 'Update Product';
    (modalRef.componentInstance as AddProductComponent).action = 'Update';
    (modalRef.componentInstance as AddProductComponent).productCategory = this.productCategory;
    modalRef.result.then(() => this.getProducts());
  }

  onDelete(id: number) {
    this.modalService.open(ConfirmationPopupComponent, { centered: true, size: 'sm' }).result.then((res) => {
      if (res) {
        this.productsService.deleteProduct(id).subscribe(() => this.getProducts());
      }
    });
  }

  onFilterChange(product: Product) {
    this.productsService.filterProducts(product).subscribe((res: Product[]) => {
        this.products = res;
        this.totalProducts = this.products.length;
    })
  }

}
