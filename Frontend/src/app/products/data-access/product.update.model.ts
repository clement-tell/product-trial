export interface ProductUpdateDto {
    name: string;
    description: string;
    image: string;
    category: string;
    price: number;
    shellId: number;
    inventoryStatus: "INSTOCK" | "LOWSTOCK" | "OUTOFSTOCK";
}
