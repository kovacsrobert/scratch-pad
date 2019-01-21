import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EvilLairComponent } from './evil-lair/evil-lair.component';

@NgModule({
  declarations: [EvilLairComponent],
  imports: [CommonModule],
  exports: [EvilLairComponent],
})
export class VillainsModule { }
