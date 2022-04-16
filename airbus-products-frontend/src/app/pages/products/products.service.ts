import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Product, ProductCategory, productFields } from './products.model';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

  constructor(private http: HttpClient, private formBuilder: FormBuilder) { }

  initializeProductForm(product: Product): FormGroup {
    return this.formBuilder.group({
      [productFields.PRODUCT_ID]: [product.productId, Validators.required],
      [productFields.CATEGORY]: [(product.category && product.category.id) || '', Validators.required],
      [productFields.NAME]: [product.name, Validators.required],
      [productFields.DESCRIPTION]: [product.description, Validators.required],
      [productFields.UNITS]: [product.units, Validators.compose([Validators.required, Validators.min(1)])]
    });
  }

  getProdoductFormValues(productForm: FormGroup): Product {
    return {
      productId: productForm.get(productFields.PRODUCT_ID)?.value,
      category: {
        id: +productForm.get(productFields.CATEGORY)?.value
      },
      name: productForm.get(productFields.NAME)?.value,
      description: productForm.get(productFields.DESCRIPTION)?.value,
      units: productForm.get(productFields.UNITS)?.value
    } as Product;
  }

  initializeFilterForm(): FormGroup {
    return this.formBuilder.group({
      [productFields.PRODUCT_ID]: [''],
      [productFields.CATEGORY]: [-1],
      [productFields.NAME]: [''],
      [productFields.DESCRIPTION]: ['']
    });
  }

  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(`${environment.apiBaseUrl}/secured/api/products`);
  }

  addNewProduct(productData: Product): Observable<Product> {
    return this.http.post<Product>(`${environment.apiBaseUrl}/secured/api/products`, productData);
  }

  updateProduct(productData: Product, id: number): Observable<Product> {
    return this.http.put<Product>(`${environment.apiBaseUrl}/secured/api/products/${id}`, productData);
  }

  deleteProduct(id: number):Observable<void> {
    return this.http.delete<void>(`${environment.apiBaseUrl}/secured/api/products/${id}`);
  }

  searchProductByCategory(categoryId: number): Observable<Product[]> {
    return this.http.get<Product[]>(`${environment.apiBaseUrl}/secured/api/products/category/${categoryId}`)
  }

  getProductCategories(): Observable<ProductCategory[]> {
    return this.http.get<ProductCategory[]>(`${environment.apiBaseUrl}/secured/api/reference/product-categories`)
  }

  filterProducts(filterData: Product): Observable<Product[]> {
    return this.http.post<Product[]>(`${environment.apiBaseUrl}/secured/api/products/filter`, filterData)
  }
}
