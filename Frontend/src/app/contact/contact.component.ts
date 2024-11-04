import {Component} from '@angular/core';
import {DialogModule} from "primeng/dialog";
import {ProductFormComponent} from "../products/ui/product-form/product-form.component";
import {Button} from "primeng/button";
import {DropdownModule} from "primeng/dropdown";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {InputNumberModule} from "primeng/inputnumber";
import {InputTextModule} from "primeng/inputtext";
import {InputTextareaModule} from "primeng/inputtextarea";
import {ToastModule} from "primeng/toast";
import {MessageService} from "primeng/api";
import {Router} from "@angular/router";
import {AppComponent} from "../app.component";

@Component({
  selector: 'app-contact',
  standalone: true,
  imports: [
    DialogModule,
    ProductFormComponent,
    Button,
    DropdownModule,
    FormsModule,
    InputNumberModule,
    InputTextModule,
    InputTextareaModule,
    ReactiveFormsModule,
    ToastModule
  ],
  templateUrl: './contact.component.html',
  styleUrl: './contact.component.css'
})
export class ContactComponent {

  email?: string;
  message?: string;
  readonly maxLength: number = 300;

  constructor(private readonly appComponent: AppComponent, private readonly router: Router) {
  }

  get remainingCharacters(): number {
    return this.maxLength - (this.message?.length ?? 0);
  }

  onSend() {
    this.router.navigate(["/home"]).then(() =>
      this.appComponent.messageService.add({ severity: 'success', summary: 'Envoyé !', detail: 'Demande de contact envoyée avec succès.' })
    );
  }

}
