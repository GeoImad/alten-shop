import { inject } from "@angular/core";
import { ActivatedRouteSnapshot, Routes } from "@angular/router";
import { CartComponent } from "./ui/cart-ui/cart-ui.component";

export const CART_ROUTES: Routes = [
	{
		path: "list",
		component: CartComponent,
	},
	{ path: "**", redirectTo: "list" },
];
