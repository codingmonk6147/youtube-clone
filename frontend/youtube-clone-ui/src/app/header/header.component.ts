import { Component } from '@angular/core';
import { OidcSecurityService } from 'angular-auth-oidc-client';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {

  isAuthenticated : boolean = false;

  constructor(public oidcSecurityService: OidcSecurityService) {}

  ngOnInit(): void{
    this.oidcSecurityService.isAuthenticated$.subscribe(
      ({isAuthenticated}) =>
      {
        this.isAuthenticated =  isAuthenticated;
      }
    )
  }


  login(){
    this.oidcSecurityService.authorize();
  }

  
  logout() {
    this.oidcSecurityService
      .logoffAndRevokeTokens()
      .subscribe((result) => console.log(result));
  }


}