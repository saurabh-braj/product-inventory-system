import { sortDirections } from "src/app/constants";

export interface PageRequestModel {
    pageNumber: number;
	pageSize: number;
	sortColumn: string;
	sortDirection: SortDirections;
    product: Product;
}

export type SortDirections = sortDirections.ASC | sortDirections.DESC;

export interface PagenatedProduct {
    content: Product[];
    pageable: Pageable;
    totalElements: number;
    totalPages: number;
}

export interface Pageable {
    pageNumber: number;
    pageSize: number;
    offset: number;
}

export interface Product {
    id: number;
    productId: string;
    category: ProductCategory;
    name: string;
    description: string;
    units: number;
}

export interface ProductCategory {
    id: number;
    name: string;
}