import { Injectable } from '@angular/core';
import { Hero } from './heroes/hero';
import { HEROES } from './heroes/mock-heroes';
import { Observable, of } from 'rxjs';
import { MessageService } from './message.service';

@Injectable({
  providedIn: 'root'
})
export class HeroService {

  constructor(private messageService: MessageService) { }

  getHeroes(): Observable<Hero[]> {
  	this.messageService.addMessage('HeroService: fetched heroes');
  	return of(HEROES);
  }

  getHero(id: number) {
  	this.messageService.addMessage(`HeroService: fetched hero id=${id}`);
  	return of(HEROES.find(hero => hero.id === id));
  }
}