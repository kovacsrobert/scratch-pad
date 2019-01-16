import { Injectable } from '@angular/core';
import { Hero } from './heroes/hero';
import { HEROES } from './heroes/mock-heroes';

@Injectable({
  providedIn: 'root'
})
export class HeroService {

  heroes: Hero[];

  constructor() {
  	this.heroes = HEROES;
  }
}
