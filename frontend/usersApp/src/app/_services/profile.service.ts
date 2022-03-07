import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Profile } from "../_models/profile";

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

const PROFILES_ENDPOINT = "http://localhost:8080/profiles";

@Injectable({
    providedIn: 'root'
})
export class ProfileService {

    constructor(private http: HttpClient) { }

    getAll() {
        return this.http.get<Profile[]>(PROFILES_ENDPOINT, httpOptions);
    }

    getById(id: number) {
        return this.http.get<Profile>(`${PROFILES_ENDPOINT}/${id}`, httpOptions);
    }

    create(params: any) {
        return this.http.post(PROFILES_ENDPOINT, params, httpOptions);
    }

    update(id: number, params: any) {
        return this.http.put(`${PROFILES_ENDPOINT}/${id}`, params, httpOptions);
    }

    delete(id: number) {
        return this.http.delete(`${PROFILES_ENDPOINT}/${id}`, httpOptions);
    }
    
};