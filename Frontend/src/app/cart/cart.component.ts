import { Component } from '@angular/core';
import {CartService} from "./cart.service";
import {Button} from "primeng/button";
import {CardModule} from "primeng/card";
import {CurrencyPipe, NgIf, NgOptimizedImage} from "@angular/common";
import {DataViewModule} from "primeng/dataview";
import {DialogModule} from "primeng/dialog";
import {InputNumberModule} from "primeng/inputnumber";
import {PrimeTemplate} from "primeng/api";
import {ProductFormComponent} from "../products/ui/product-form/product-form.component";
import {RatingModule} from "primeng/rating";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [
    Button,
    CardModule,
    CurrencyPipe,
    DataViewModule,
    DialogModule,
    InputNumberModule,
    NgOptimizedImage,
    PrimeTemplate,
    ProductFormComponent,
    RatingModule,
    FormsModule,
    NgIf
  ],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent {

  constructor(protected readonly cartService: CartService) {
  }

  public readonly products = this.cartService.products;

}
