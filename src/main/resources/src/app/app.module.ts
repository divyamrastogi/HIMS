import { NgModule } from '@angular/core';
import { BrowserModule }  from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { FormsModule }   from '@angular/forms';
import { HttpModule }    from '@angular/http';
import { RegistrationComponent } from './registration/registration.component';
import { LoginComponent } from './login/login.component';
import { MaterializeDirective } from "angular2-materialize";
import { HospitalService } from './services/hospital.service';
import { AppRoutingModule } from './app-routing';

@NgModule({
	imports: [
		BrowserModule,
		AppRoutingModule,
		FormsModule,
		HttpModule
	],
	declarations: [
		AppComponent,
		MaterializeDirective,
		RegistrationComponent,
		LoginComponent
	],
	providers: [HospitalService],
	bootstrap: [AppComponent],
})
export class AppModule { }
