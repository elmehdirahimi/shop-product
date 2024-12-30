import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { ButtonModule } from 'primeng/button';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';

@Component({
    selector: 'app-contact',
    standalone: true,
    imports: [
        CommonModule,
        ReactiveFormsModule,
        InputTextModule,
        InputTextareaModule,
        ButtonModule,
        ToastModule
    ],
    providers: [MessageService],
    templateUrl: './contact.component.html'
})
export class ContactComponent {
    contactForm: FormGroup;

    constructor(
        private fb: FormBuilder,
        private messageService: MessageService
    ) {
        this.contactForm = this.fb.group({
            email: ['', [Validators.required, Validators.email]],
            message: ['', [Validators.required, Validators.maxLength(300)]]
        });
    }

    onSubmit(): void {
        if (this.contactForm.valid) {
            console.log(this.contactForm.value);

            this.messageService.add({
                severity: 'success',
                summary: 'Succès',
                detail: 'Demande de contact envoyée avec succès'
            });

            this.contactForm.reset();
        }
    }
}
