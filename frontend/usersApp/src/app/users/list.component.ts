import { Component, OnInit } from '@angular/core';
import { first } from 'rxjs/operators';

import { User } from 'src/app/_models/user';
import { UserService } from 'src/app/_services';

@Component({
    templateUrl: './list.component.html'
})
export class ListComponent implements OnInit {

    constructor(private userService: UserService) { }

    public users: User[] = [];

    ngOnInit() : void {

        this.userService.getAll()
            .pipe(first())
            .subscribe(users => this.users = users); 
    }

    deleteUser(id: number) {
        const user = this.users.find(x => x.id === id);
        this.userService.delete(id)
            .pipe(first())
            .subscribe(() => this.users = this.users.filter(x => x.id !== id));
    }
    
}
