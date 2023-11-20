import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  
  constructor(private httpClient: HttpClient) {
  }

  subscribeToUser(userId: string): Observable<boolean> {
    return this.httpClient.post("http://localhost:8080/api/user/subscribe/"+userId, null);
  }

  registerUser() {
    throw new Error('Method not implemented.');
  }

}
