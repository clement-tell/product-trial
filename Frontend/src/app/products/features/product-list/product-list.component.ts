import { Component, OnInit, inject, signal } from "@angular/core";
import { Product } from "app/products/data-access/product.model";
import { ProductsService } from "app/products/data-access/products.service";
import { ProductFormComponent } from "app/products/ui/product-form/product-form.component";
import { ButtonModule } from "primeng/button";
import { CardModule } from "primeng/card";
import { DataViewModule } from 'primeng/dataview';
import { DialogModule } from 'primeng/dialog';
import {ProductCreateDto} from "../../data-access/product.create.model";
import {AppComponent} from "../../../app.component";
import {ConfirmationService, SelectItem} from "primeng/api";
import {CartService} from "../../../cart/cart.service";
import {InputNumberModule} from "primeng/inputnumber";
import {FormsModule} from "@angular/forms";
import {CurrencyPipe, NgIf, NgOptimizedImage} from "@angular/common";
import {RatingModule} from "primeng/rating";
import {ProgressSpinnerModule} from "primeng/progressspinner";
import {ActivatedRoute} from "@angular/router";
import {firstValueFrom} from "rxjs";
import {DropdownModule} from "primeng/dropdown";
import {PaginatorModule} from "primeng/paginator";

const emptyProduct: ProductCreateDto = {
  name: "",
  description: "",
  image: "",
  category: "",
  price: 0,
  quantity: 0,
  internalReference: "",
  shellId: 0,
  inventoryStatus: "INSTOCK"
};

@Component({
  selector: "app-product-list",
  templateUrl: "./product-list.component.html",
  styleUrls: ["./product-list.component.scss"],
  standalone: true,
  imports: [DataViewModule, CardModule, ButtonModule, DialogModule, ProductFormComponent, InputNumberModule, FormsModule, CurrencyPipe, NgOptimizedImage, RatingModule, NgIf, ProgressSpinnerModule, DropdownModule, PaginatorModule]
})
export class ProductListComponent implements OnInit {

  constructor(private readonly appComponent: AppComponent, private readonly confirmationService: ConfirmationService, protected readonly cartService: CartService, private readonly route: ActivatedRoute) {
  }

  private readonly productsService = inject(ProductsService);

  public readonly products = this.productsService.products;

  public isDialogVisible = false;
  public isCreation = false;
  public editedProductId?: number;
  public readonly editedProduct = signal<ProductCreateDto>(emptyProduct);
  public waitProductAPI = true;
  public isAdmin?: boolean = undefined;

  public readonly categories: SelectItem[] = [
    { value: 'Accessories', label: 'Accessories' },
    { value: 'Fitness', label: 'Fitness' },
    { value: 'Clothing', label: 'Clothing' },
    { value: 'Electronics', label: 'Electronics' }
  ];

  public selectedCategory: string = '';
  paginatedProducts: Product[] = [];
  productsPerPage: number = 5;
  totalProducts: number = 0;
  currentPage: number = 0;

  async ngOnInit() {
    this.isAdmin = (await firstValueFrom(this.route.data))['isAdmin'];
    this.productsService.getAllProducts().subscribe((product) => {
        if (!product) {
          this.appComponent.messageService.add({
            severity: 'error',
            summary: 'Échec de la récupération des produits !',
            detail: 'Les produits n\'ont pas pu être récupérés à cause d\'une erreur interne.'
          })
        } else {
          for (let cartProduct of this.cartService.products()) {
            const foundProduct = this.products().find(p => p.id === cartProduct.id);
            if (foundProduct) {
              foundProduct.quantity = cartProduct.quantity;
            }
          }
          this.waitProductAPI = false;
        }
      }
    );
  }

  getFilteredProducts() {
    return this.products().filter(product => this.selectedCategory ? product.category === this.selectedCategory : true);
  }

  getPageProducts() {
    const filtered = this.getFilteredProducts();
    this.totalProducts = filtered.length;
    return filtered.slice(this.currentPage * this.productsPerPage, (this.currentPage + 1) * this.productsPerPage);
  }

  onPageChange(event: any) {
    this.currentPage = event.page;
    this.updatePaginatedProducts();
  }

  updateCategory() {
    this.currentPage = 0;
    this.updatePaginatedProducts();
  }

  updatePaginatedProducts() {
    this.paginatedProducts = this.getPageProducts();
  }

  public onCreate() {
    this.isCreation = true;
    this.isDialogVisible = true;
    this.editedProduct.set(emptyProduct);
  }

  public onUpdate(product: Product) {
    this.isCreation = false;
    this.isDialogVisible = true;
    this.editedProductId = product.id;
    this.editedProduct.set(product);
  }

  public onDelete(product: Product) {
    this.confirmationService.confirm({
      message: 'Êtes-vous sûr de vouloir supprimer ce produit ?',
      header: 'Confirmation',
      icon: 'pi pi-info-circle',
      acceptLabel: 'Oui',
      rejectLabel: 'Non',
      acceptButtonStyleClass: 'p-button-secondary',
      rejectButtonStyleClass: 'p-button-danger',
      accept: () => {
        this.productsService.deleteProduct(product.id).subscribe((success) => {
          this.appComponent.messageService.add(success ? {
            severity: 'success',
            summary: 'Produit supprimé !',
            detail: 'Le produit a été supprimé avec succès. Il n\'apparaitra  plus dans la liste de tous les produits.'
          } : {
            severity: 'error',
            summary: 'Échec de la suppression !',
            detail: 'Le produit n\'a pas pu être supprimé à cause d\'une erreur interne.'
          })
        });
      }
    });
  }

  public onSave(product: ProductCreateDto) {
    if (this.isCreation) {
      this.productsService.createProduct(product).subscribe((product) => {
          this.appComponent.messageService.add(product ? {
            severity: 'success',
            summary: 'Produit créé !',
            detail: 'Le produit a été créé avec succès. Vous pouvez le retrouver dans la liste de tous les produits.'
          } : {
            severity: 'error',
            summary: 'Échec de la création !',
            detail: 'Le produit n\'a pas pu être créé à cause d\'une erreur interne.'
          })
        });
    } else {
      this.productsService.updateProduct(this.editedProductId!, product).subscribe((product) => {
          this.appComponent.messageService.add(product ? {
            severity: 'success',
            summary: 'Produit modifié !',
            detail: 'Le produit a été modifié avec succès. Vous pouvez le retrouver dans la liste de tous les produits.'
          } : {
            severity: 'error',
            summary: 'Échec de la modification !',
            detail: 'Le produit n\'a pas pu être modifié à cause d\'une erreur interne.'
          })
        });
      this.editedProductId = undefined;
    }
    this.closeDialog();
  }

  addToCart(product: Product) {
    product.quantity = 1;
    this.cartService.addToCart(product);
  }

  public onCancel() {
    this.closeDialog();
  }

  private closeDialog() {
    this.isDialogVisible = false;
  }

  protected readonly Math = Math;
}
