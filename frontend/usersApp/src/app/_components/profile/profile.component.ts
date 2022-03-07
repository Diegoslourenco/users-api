import { Component, OnInit } from '@angular/core';
import { first } from 'rxjs/operators';

import { Profile } from 'src/app/_models/profile';
import { ProfileService } from 'src/app/_services/profile.service';

@Component({
    selector: 'app-profiles',
    templateUrl: './profile.component.html'
})
export class ProfileComponent implements OnInit {

    constructor(private profileService: ProfileService) { }

    public profiles: Profile[] = [];

    ngOnInit() : void {

        this.profileService.getAll()
            .pipe(first())
            .subscribe(profiles => this.profiles = profiles);
    }

}
