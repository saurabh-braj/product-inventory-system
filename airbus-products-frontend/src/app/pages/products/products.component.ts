import { Component, OnInit } from '@angular/core';
import { NgbModal, NgbModalConfig, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AddProductComponent } from './add-product/add-product.component';
import { ConfirmationPopupComponent } from './confirmation-popup/confirmation-popup.component';
import { getProductPageModel } from './product.functions';
import { ProductCategory, Product, PagenatedProduct } from './products.model';
import { ProductsService } from './products.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.scss']
})
export class ProductsComponent implements OnInit {

  pagenatedProducts!: PagenatedProduct;
  productCategory!: ProductCategory[];
  currentPage = 0;

  constructor(private productsService: ProductsService,
    config: NgbModalConfig, private modalService: NgbModal) {
      config.backdrop = 'static';
      config.keyboard = false;
     }

  ngOnInit(): void {
    this.getProducts(this.currentPage);
    this.getProductCategories();
  }

  getProductCategories() {
    this.productsService.getProductCategories().subscribe((res: ProductCategory[]) => {
      this.productCategory = res;
    });
  }

  getProducts(currentPage: number = 0, product?: Product) {
    const pageRequestModel = getProductPageModel(currentPage);
    if (product) {
      pageRequestModel.product = product;
    }
    this.productsService.getProducts(pageRequestModel).subscribe((data: PagenatedProduct) => {
      this.pagenatedProducts = data;
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
    this.getProducts(0, product);
  }

}
