import { NgModule } from '@angular/core';
import { AuthModule } from 'angular-auth-oidc-client';


@NgModule({
    imports: [AuthModule.forRoot({
        config: {
            authority: 'https://dev-cgc62qkdknguds23.us.auth0.com',
            redirectUrl: window.location.origin,
            clientId: '3KLP2iw5h72eMtxY9aVMEmj9swVeozj8',
            scope: 'openid profile offline_access email',
            responseType: 'code',
            silentRenew: true,
            useRefreshToken: true,
            secureRoutes: ['http://localhost:8080'],
            customParamsAuthRequest:{
                audience: 'http://localhost:8080'
            }
        }
      })],
    exports: [AuthModule],
})
export class AuthConfigModule {}
