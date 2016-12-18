import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HospitalService } from './services/hospital.service';
import '../../public/css/styles.css';
@Component({
	selector: 'my-app',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
	isLoggedIn: boolean;
	constructor(
		private hospitalService: HospitalService,
		private router: Router
	) { }

	ngOnInit(): void {
		this.hospitalService.login({})
			.then((response: any) => {
				if (/FAILURE/i.test(response.status)) {
					this.router.navigate(['login']);
				} else if (/SUCCESS/i.test(response.status)) {
					this.router.navigate(['register']);
				}
			});
	}
}
