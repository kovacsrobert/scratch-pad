import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Hero } from './heroes/hero';
import { MessageService } from './message.service';

@Injectable({
  providedIn: 'root'
})
export class HeroService {

  private heroesUrl: string = 'api/heroes';

  constructor(
    private http: HttpClient, 
    private messageService: MessageService
  ) { }

  getHeroes(): Observable<Hero[]> {
  	this.messageService.addMessage('HeroService: fetched heroes');
  	return this.http.get<Hero[]>(this.heroesUrl)
      .pipe(
        tap(_ => this.log('fetched heroes')),
        catchError(this.handleError<Hero[]>('getHeroes', []))
      );
  }

  getHero(id: number): Observable<Hero> {
    const url = `${this.heroesUrl}/${id}`;
  	return this.http.get<Hero>(url)
      .pipe(
        tap(_ => this.log(`ffetched hero id=${id}`)),
        catchError(this.handleError<Hero>('getHero id=${id}'))
      );
  }

  updateHero(hero: Hero): Observable<any> {
    const httpOptions = { headers: new HttpHeaders({ 'Content-Type': 'application/json' }) };
     return this.http.put(this.heroesUrl, hero, httpOptions)
       .pipe(
         tap(_ => this.log(`updated hero id=${hero.id}`)),
         catchError(this.handleError<any>('updateHero'))
       );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      this.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }

  private log(message: string) {
    this.messageService.addMessage(`HeroService: ${message}`);
  }
}