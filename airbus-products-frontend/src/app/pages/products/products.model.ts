export enum productFields {
    ID = 'id',
    PRODUCT_ID = 'productId',
    CATEGORY = 'category',
    NAME = 'name',
    DESCRIPTION = 'description',
    UNITS = 'units'
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