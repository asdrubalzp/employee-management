import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { PersonModel } from '../models/person.model';

@Injectable({
  providedIn: 'root',
})
export class PersonService {
  private resourceName = 'persons';
  private url = environment.API_URL + '/' + this.resourceName;

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  };

  constructor(private http: HttpClient) {}

  getPersons(): Observable<PersonModel[]> {
    return this.http.get<PersonModel[]>(`${this.url}`);
  }

  deletePerson(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }

  addPerson(person: PersonModel): Observable<PersonModel> {
    return this.http.post<PersonModel>(`${this.url}`, person, this.httpOptions);
  }

  updatePerson(person: PersonModel): Observable<PersonModel> {
    return this.http.put<PersonModel>(
      `${this.url}/${person.id}`,
      person,
      this.httpOptions
    );
  }

  getEmptyPerson(): PersonModel {
    return {
      id: 0,
      name: '',
      lastName: '',
      email: '',
      salary: null,
      birthDate: null,
    };
  }

  //   getRequests(): Observable<Request[]> {
  //     // return of(REQUESTS);
  //     return this.http.get<Request[]>(`${this.url}/requests`);
  //   }

  //   getRequest(id: number): Observable<Request> {
  //     return this.http.get<Request>(`${this.url}/requests/${id}`);
  //   }

  //   reserveRequest(request: Request): Observable<any> {
  //     return this.http.put(`${this.url}/reserve`, request, this.httpOptions);
  //   }

  //   addRequest(request: Request): Observable<Request> {
  //     return this.http.post<Request>(
  //       `${this.url}/requests`,
  //       request,
  //       this.httpOptions
  //     );
  //   }

  //   cancelRequest(id: number): Observable<any> {
  //     return this.http.put(
  //       `${this.url}/cancelReservation/${id}`,
  //       this.httpOptions
  //     );
  //   }
}
