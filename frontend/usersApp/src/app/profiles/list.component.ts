import { Component, OnInit } from '@angular/core';
import { first } from 'rxjs/operators';

import { Profile } from 'src/app/_models/profile';
import { ProfileService } from 'src/app/_services';

@Component({
    templateUrl: './list.component.html'
})
export class ListComponent implements OnInit {

    constructor(private profileService: ProfileService) { }

    public profiles: Profile[] = [];

    ngOnInit() : void {

        this.profileService.getAll()
            .pipe(first())
            .subscribe(profiles => this.profiles = profiles); 
    }

    deleteProfile(id: number) {
        const user = this.profiles.find(x => x.id === id);
        this.profileService.delete(id)
            .pipe(first())
            .subscribe(() => this.profiles = this.profiles.filter(x => x.id !== id));
    }
    
}
