import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Product } from 'app/products/data-access/product.model';
import { BehaviorSubject, Observable, catchError, from, tap } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class CartService {
    private cart: any[] = [];

    private cartVisible = new BehaviorSubject<boolean>(false);
    cartVisible$ = this.cartVisible.asObservable();

    showCart() {
        this.cartVisible.next(true);
    }

    hideCart() {
        this.cartVisible.next(false);
    }

    private readonly http = inject(HttpClient);

    private readonly path = "/api/products";

    private readonly _products = signal<Product[]>([]);

    public readonly products = this._products.asReadonly();

    private cartSubject = new BehaviorSubject<Product[]>(this.cart);
    private cartCount = new BehaviorSubject<number>(0);

    public get(): any {
        return this.cart;
    }

    getCartCount() {
        return this.cartCount.asObservable();
    }   

    addToCart(product: any) {
        const item = this.cart.find(p => p.name === product.name);
        if (item) {
            item.quantity += 1;
        } else {
            this.cart.push({ ...product, quantity: 1 });
        }
        //this.cart.push(product);
        this.cartSubject.next(this.cart);
        this.updateCartCount()
        console.log('Produit ajouté au panier:', this.cart);
        
    }

    removeFromCart(product: any) {
        const index = this.cart.findIndex(p => p.name === product.name);
        if (index !== -1) {
            if (this.cart[index].quantity > 1) {
                this.cart[index].quantity -= 1;
            } else {
                this.cart.splice(index, 1); // Supprimer complètement si quantité = 1
            }
        }
        this.updateCartCount()
        console.log('Produit supprimé du panier:', this.cart);
    }

    private updateCartCount() {
        const totalQuantity = this.cart.reduce((sum, item) => sum + item.quantity, 0);
        this.cartCount.next(totalQuantity);
    }
    

    getCart() {
        return this.cart;
    }
}
