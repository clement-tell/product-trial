import { Injectable, signal } from "@angular/core";
import {Product} from "../products/data-access/product.model";

@Injectable({
    providedIn: "root"
}) export class CartService {

  private readonly _products = signal<Product[]>([]);

  public readonly products = this._products.asReadonly();

  public getCartSize() {
    return this._products().reduce((total, product) => total + product.quantity, 0);
  }

  public addToCart(product: Product) {
    this._products.update(products => [product, ...products]);
  }

  public removeFromCart(product: Product) {
    this._products.update(products => products.filter(p => p.id !== product.id));
  }

  public updateProductQuantity(product: Product) {
    if (product.quantity === 0) {
      this.removeFromCart(product);
    } else {
      this._products.update(products => products.map(p => p.id === product.id ? product : p));
    }
  }

  public isAlreadyInTheCart(requestedProduct: Product): boolean {
    return this.products().find(product => requestedProduct.id === product.id) !== undefined;
  }

  public getCartPrice() {
    return this._products().reduce((total, product) => total + product.quantity * product.price, 0);
  }

}
