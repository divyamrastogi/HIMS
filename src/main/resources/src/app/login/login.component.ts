// Keep the Input import for now, we'll remove it later:
import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Params }   from '@angular/router';
import { Location }                 from '@angular/common';
import { HospitalService } from '../services/hospital.service';
import { Router } from '@angular/router';

@Component({
	selector: 'user-login',
	templateUrl: './login.component.html',
	styleUrls: ['./login.scss']
})
export class LoginComponent implements OnInit {
	credentials: any = {};
	constructor(
		private hospitalService: HospitalService,
		private route: ActivatedRoute,
		private router: Router
	) { }

	login(credentials: any): void {
		this.hospitalService.login(credentials)
			.then((response: any) => {
				if (/FAILURE/i.test(response.status)) {
					this.router.navigate(['login']);
				} else if (/SUCCESS/i.test(response.status)) {
					this.router.navigate(['registration']);
				}
			}, (response: any) => {
				Materialize.toast('Some error occured. ' + JSON.stringify(response), 1000);
			});
	}

	ngOnInit(): void {

	}


}
