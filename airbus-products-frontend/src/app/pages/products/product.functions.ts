import { sortDirections } from "src/app/constants";
import { productColumns } from "./products.constants";
import { PageRequestModel, Product } from "./products.model";

export function getProductPageModel(currentPage: number = 0, sortColumn: string = productColumns.NAME,
    sortDirection: sortDirections = sortDirections.ASC): PageRequestModel {
    return {
        pageNumber: currentPage,
        pageSize: 10,
        sortColumn: sortColumn,
        sortDirection: sortDirection,
        product: {
            productId: '',
            name: '',
            description: ''
        } as Product
    };
}