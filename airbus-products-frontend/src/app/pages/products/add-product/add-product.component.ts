import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs';
import { Product, ProductCategory } from '../products.model';
import { ProductsService } from '../products.service';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.scss']
})
export class AddProductComponent implements OnInit {

  productForm!: FormGroup;
  productCategory!: ProductCategory[];
  product = {} as Product;
  header = '';
  action = 'Add';
  message = false;
  displayMessage = '';

  constructor(public activeModal: NgbActiveModal, private productsService: ProductsService) { }

  ngOnInit(): void {
    this.productForm = this.productsService.initializeProductForm(this.product);
  }

  close() {
    this.activeModal.close();
  }

  updateProduct() {
    const productData: Product = this.productsService.getProdoductFormValues(this.productForm);
    this.productsService.updateProduct(productData, this.product.id).subscribe((res: Product) => {
      this.message = true;
      this.action = '';
      this.displayMessage = `The Product has been Updated Successfully.`
    })
  }

  addProduct() {
    const productData: Product = this.productsService.getProdoductFormValues(this.productForm);
    this.productsService.addNewProduct(productData).subscribe((res: Product) => {
      this.message = true;
      this.action = '';
      this.displayMessage = `The Product has been Added Successfully.`;
    })
  }

}
