export class User {
    id: number
    name: string;
    email: string;
    profileId: number;

    constructor(id: number, name: string, email: string, profileId: number) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.profileId = profileId;
    }
}