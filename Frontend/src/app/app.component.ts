import {
  Component,
} from "@angular/core";
import { RouterModule } from "@angular/router";
import { SplitterModule } from 'primeng/splitter';
import { ToolbarModule } from 'primeng/toolbar';
import { PanelMenuComponent } from "./shared/ui/panel-menu/panel-menu.component";
import {ToastModule} from "primeng/toast";
import {MessageService} from "primeng/api";
import {ConfirmDialogModule} from "primeng/confirmdialog";
import {CartService} from "./cart/cart.service";
import {NgOptimizedImage} from "@angular/common";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"],
  standalone: true,
  imports: [RouterModule, SplitterModule, ToolbarModule, PanelMenuComponent, ToastModule, ConfirmDialogModule, NgOptimizedImage],
})
export class AppComponent {

  constructor(public readonly messageService: MessageService, public readonly cartService: CartService) {
  }
  title = "ALTEN SHOP";
}
