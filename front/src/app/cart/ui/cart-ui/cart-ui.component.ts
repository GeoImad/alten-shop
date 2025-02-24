import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { CartService } from 'app/cart/data-access/cart.service';
import { Product } from 'app/products/data-access/product.model';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-cart',
  standalone: true,
  templateUrl: './cart-ui.component.html',
  styleUrl : './cart-ui.component.css',
  imports : [ CommonModule, ButtonModule]
})
export class CartComponent implements OnInit {
  cart: any[] = [];

  private readonly cartService = inject(CartService);

  public productInCart: Product[]= [];

    async ngOnInit(): Promise<void> {
        this.productInCart = await this.cartService.get()
        
    }

    addToCart(product: Product): void {
        this.cartService.addToCart(product);
      }

    removeFromCart(product: any): void {
        this.cartService.removeFromCart(product);
    }
     
}
