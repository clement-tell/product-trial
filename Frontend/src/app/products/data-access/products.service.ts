import { Injectable, inject, signal } from "@angular/core";
import { Product } from "./product.model";
import { HttpClient } from "@angular/common/http";
import {catchError, map, Observable, of, tap} from "rxjs";
import {environment} from "../../../environments/environment";
import {ProductCreateDto} from "./product.create.model";
import {ProductUpdateDto} from "./product.update.model";

@Injectable({
    providedIn: "root"
}) export class ProductsService {

  private readonly http = inject(HttpClient);

  private readonly _products = signal<Product[]>([]);

  public readonly products = this._products.asReadonly();

  public getAllProducts(): Observable<Product[] | undefined> {
    return this.http.get<Product[] | undefined>(environment.apiUrl).pipe(
      catchError(() => {
        return of(undefined);
      }),
      tap((products) => this._products.set(products!)),
    );
  }

  public createProduct(product: ProductCreateDto): Observable<Product | undefined> {
    return this.http.post<Product | undefined>(environment.apiUrl, product).pipe(
      catchError(() => {
        return of(undefined);
      }),
      tap((createdProduct) => this._products.update(products => [createdProduct!, ...products])),
    );
  }

  public updateProduct(productId: number, product: ProductUpdateDto): Observable<Product | undefined> {
    return this.http.patch<Product | undefined>(`${environment.apiUrl}/${productId}`, product).pipe(
      catchError(() => {
        return of(undefined);
      }),
      tap((updatedProduct) => this._products.update(products =>
        products.map(p => p.id === productId ? updatedProduct! : p)
      )),
    );
  }

  public deleteProduct(productId: number): Observable<boolean> {
    return this.http.delete<boolean>(`${environment.apiUrl}/${productId}`).pipe(
      map(() => true),
      catchError(() => {
        return of(false);
      }),
      tap(() => this._products.update(products => products.filter(product => product.id !== productId))),
    );
  }
}
