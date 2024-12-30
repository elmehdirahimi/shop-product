import { Component, ViewChild, computed, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { OverlayPanel } from 'primeng/overlaypanel';
import { OverlayPanelModule } from 'primeng/overlaypanel';
import { BadgeModule } from 'primeng/badge';
import { InputNumberModule } from 'primeng/inputnumber';
import { CartService } from '../../services/cart.service';

@Component({
    selector: 'app-cart-panel',
    templateUrl: './cart-panel.component.html',
    styleUrls: ['./cart-panel.component.scss'],
    standalone: true,
    imports: [
        CommonModule,
        FormsModule,
        ButtonModule,
        OverlayPanelModule,
        BadgeModule,
        InputNumberModule
    ]
})
export class CartPanelComponent {
    @ViewChild('op') overlayPanel!: OverlayPanel;
    private cartService = inject(CartService);

    cartCount = computed(() => this.cartService.getCount());
    items = computed(() => this.cartService.getItems());
    total = computed(() => this.cartService.getTotal());

    get cartItems() {
        return this.items();
    }

    updateQuantity(productId: number, value: any) {
        const quantity = typeof value === 'number' ? value : parseInt(value, 10);
        if (!isNaN(quantity)) {
            this.cartService.updateQuantity(productId, quantity);
        }
    }

    removeItem(productId: number) {
        this.cartService.removeItem(productId);
    }

    togglePanel(event: Event) {
        this.overlayPanel.toggle(event);
    }
}
