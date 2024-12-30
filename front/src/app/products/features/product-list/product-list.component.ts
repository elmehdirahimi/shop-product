import { Component, OnInit, inject, signal } from "@angular/core";
import { Product } from "app/products/data-access/product.model";
import { ProductsService } from "app/products/data-access/products.service";
import { ProductFormComponent } from "app/products/ui/product-form/product-form.component";
import { ButtonModule } from "primeng/button";
import { CardModule } from "primeng/card";
import { DataViewModule } from 'primeng/dataview';
import { DialogModule } from 'primeng/dialog';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { TagModule } from 'primeng/tag';
import { CartService } from "../../../shared/services/cart.service";
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';
import { FormControl, ReactiveFormsModule } from "@angular/forms";
import { InputTextModule } from 'primeng/inputtext';
import { DropdownModule } from 'primeng/dropdown';
import { PaginatorModule } from 'primeng/paginator';
import { InputNumberModule } from 'primeng/inputnumber';

const emptyProduct: Product = {
  id: 0,
  code: "",
  name: "",
  description: "",
  image: "",
  category: "",
  price: 0,
  quantity: 0,
  internalReference: "",
  shellId: 0,
  inventoryStatus: "INSTOCK",
  rating: 0,
  createdAt: 0,
  updatedAt: 0,
};

@Component({
  selector: "app-product-list",
  templateUrl: "./product-list.component.html",
  styleUrls: ["./product-list.component.scss"],
  standalone: true,
  imports: [
    CommonModule,
    DataViewModule,
    CardModule,
    ButtonModule,
    DialogModule,
    ProductFormComponent,
    CurrencyPipe,
    TagModule,
    ToastModule,
    InputTextModule,
    DropdownModule,
    PaginatorModule,
    ReactiveFormsModule,
    InputNumberModule
  ],
  providers: [MessageService]
})
export class ProductListComponent implements OnInit {
  private readonly productsService = inject(ProductsService);
  private readonly cartService = inject(CartService);
  private readonly messageService = inject(MessageService);

  public readonly products = this.productsService.products;

  public isDialogVisible = false;
  public isCreation = false;
  public readonly editedProduct = signal<Product>(emptyProduct);

  searchQuery = new FormControl('');
  selectedCategory = new FormControl('');

  first = 0;
  rows = 6;

  categories = [
    { label: 'Toutes les catégories', value: '' },
    { label: 'Accessories', value: 'Accessories' },
    { label: 'Fitness', value: 'Fitness' },
    { label: 'Clothing', value: 'Clothing' },
    { label: 'Electronics', value: 'Electronics' }
  ];


  ngOnInit() {
    this.productsService.get().subscribe();
  }

  public onCreate() {
    this.isCreation = true;
    this.isDialogVisible = true;
    this.editedProduct.set(emptyProduct);
  }

  public onUpdate(product: Product) {
    this.isCreation = false;
    this.isDialogVisible = true;
    this.editedProduct.set(product);
  }

  public onDelete(product: Product) {
    this.productsService.delete(product.id).subscribe();
  }

  public onSave(product: Product) {
    if (this.isCreation) {
      this.productsService.create(product).subscribe();
    } else {
      this.productsService.update(product).subscribe();
    }
    this.closeDialog();
  }

  public onCancel() {
    this.closeDialog();
  }

  private closeDialog() {
    this.isDialogVisible = false;
  }

  public onAddToCart(product: Product, quantity: number = 1) {
    this.cartService.addItem(product, quantity);
    this.messageService.add({
      severity: 'success',
      summary: 'Succès',
      detail: 'Élément ajouté dans le panier avec succès'
    });
  }

  onPageChange(event: any) {
    this.first = event.first;
    this.rows = event.rows;
  }

  get filteredProducts() {
    return this.products().filter(product => {
      const matchesSearch = product.name.toLowerCase()
        .includes(this.searchQuery.value?.toLowerCase() ?? '');

      const matchesCategory = !this.selectedCategory.value ||
        product.category === this.selectedCategory.value;

      return matchesSearch && matchesCategory;
    });
  }

  get paginatedProducts() {
    return this.filteredProducts
      .slice(this.first, this.first + this.rows);
  }
}
