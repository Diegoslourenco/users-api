import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { User } from "../_models/user";

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

const USERS_ENDPOINT = "http://localhost:8080/users";

@Injectable({
    providedIn: 'root'
})
export class UserService {

    constructor(private http: HttpClient) { }

    getAll() {
        return this.http.get<User[]>(USERS_ENDPOINT, httpOptions);
    }

    getById(id: number) {
        return this.http.get<User>(`${USERS_ENDPOINT}/${id}`, httpOptions);
    }

    create(params: any) {
        return this.http.post(USERS_ENDPOINT, params, httpOptions);
    }

    update(id: number, params: any) {
        return this.http.put(`${USERS_ENDPOINT}/${id}`, params, httpOptions);
    }

    delete(id: number) {
        return this.http.delete(`${USERS_ENDPOINT}/${id}`, httpOptions);
    }
    
};