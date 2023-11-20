import { Component } from '@angular/core';
import { UserService } from '../user.service';

@Component({
  selector: 'app-callback',
  templateUrl: './callback.component.html',
  styleUrls: ['./callback.component.css']
})
export class CallbackComponent {
  constructor(private userService : UserService) {
    this.userService.registerUser();
  }
}
