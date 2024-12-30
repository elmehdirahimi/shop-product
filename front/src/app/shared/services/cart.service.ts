import { Injectable, signal } from '@angular/core';
import { Product } from '../../products/data-access/product.model';

interface CartItem extends Product {
    quantity: number;
}

interface CartState {
    items: CartItem[];
    count: number;
    total: number;
}

@Injectable({
    providedIn: 'root'
})
export class CartService {
    private readonly STORAGE_KEY = 'cart_items';
    private state = signal<CartState>({
        items: [],
        count: 0,
        total: 0
    });

    constructor() {
        this.loadFromStorage();
    }

    private loadFromStorage(): void {
        const stored = localStorage.getItem(this.STORAGE_KEY);
        if (stored) {
            const items = JSON.parse(stored) as Product[];
            this.updateState(items);
        }
    }

    private saveToStorage(items: Product[]): void {
        localStorage.setItem(this.STORAGE_KEY, JSON.stringify(items));
    }

    private updateState(items: CartItem[]): void {
        const total = items.reduce((sum, item) => sum + (item.price * item.quantity), 0);
        const count = items.reduce((sum, item) => sum + item.quantity, 0);

        this.state.set({
            items,
            count,
            total
        });
        this.saveToStorage(items);
    }

    addItem(product: Product, quantity: number = 1): void {
        const currentItems = this.state().items;
        const existingItem = currentItems.find(item => item.id === product.id);

        if (existingItem) {
            const updatedItems = currentItems.map(item =>
                item.id === product.id
                    ? { ...item, quantity: item.quantity + quantity }
                    : item
            );
            this.updateState(updatedItems);
        } else {
            this.updateState([...currentItems, { ...product, quantity }]);
        }
    }

    updateQuantity(productId: number, quantity: number): void {
        const currentItems = this.state().items;
        if (quantity <= 0) {
            this.removeItem(productId);
            return;
        }

        const updatedItems = currentItems.map(item =>
            item.id === productId
                ? { ...item, quantity }
                : item
        );
        this.updateState(updatedItems);
    }

    removeItem(productId: number): void {
        const currentItems = this.state().items;
        const updatedItems = currentItems.filter(item => item.id !== productId);
        this.updateState(updatedItems);
    }

    getItems(): Product[] {
        return this.state().items;
    }

    getCount(): number {
        return this.state().count;
    }

    getTotal(): number {
        return this.state().total;
    }

    clearCart(): void {
        this.updateState([]);
    }
}
