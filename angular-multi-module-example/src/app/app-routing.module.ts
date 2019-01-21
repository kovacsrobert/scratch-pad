import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RosterComponent } from './heroes/roster/roster.component';
import { EvilLairComponent } from './villains/evil-lair/evil-lair.component';

const routes: Routes = [
	{ path: 'heroes', component: RosterComponent },
	{ path: 'villains', component: EvilLairComponent },
	{ path: '', redirectTo: '/heroes', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
