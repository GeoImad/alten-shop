import {
  Component,
  inject,
  OnInit,
} from "@angular/core";
import { RouterModule } from "@angular/router";
import { SplitterModule } from 'primeng/splitter';
import { ToolbarModule } from 'primeng/toolbar';
import { PanelMenuComponent } from "./shared/ui/panel-menu/panel-menu.component";
import { Dialog, DialogModule } from "primeng/dialog";
import { CartService } from "./cart/data-access/cart.service";
import { CartComponent } from "./cart/ui/cart-ui/cart-ui.component";
import { CommonModule } from "@angular/common";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"],
  standalone: true,
  imports: [RouterModule, SplitterModule, ToolbarModule, PanelMenuComponent, DialogModule, CartComponent, CommonModule],
})
export class AppComponent implements OnInit{
  title = "ALTEN SHOP";

  public readonly cartService = inject(CartService);

  cartVisible = false;
  cartCount: number = 0;

  ngOnInit(): void {
    this.cartService.cartVisible$.subscribe(visible => {
      this.cartVisible = visible;
    });

    this.cartService.getCartCount().subscribe(count => {
      this.cartCount = count;
  });
    
  }
}
