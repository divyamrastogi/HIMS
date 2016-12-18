// Keep the Input import for now, we'll remove it later:
import { Component, EventEmitter, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Params }   from '@angular/router';
import { Registration } from './registration';
import { HospitalService } from '../services/hospital.service';

@Component({
	selector: 'register-patient',
	templateUrl: './registration.component.html',
	styleUrls: ['./registration.scss']
})
export class RegistrationComponent implements OnInit {
	registration: any;
	cities: Array<Object>;
	sources: Array<Object>;
	departments: Array<Object>;
	doctors: Array<Object>;
	doctor: any;
	authorizedPersons: any;
	banks: any;
	constructor(
		private hospitalService: HospitalService,
		private route: ActivatedRoute
	) { }

	getCities($event: string): void {
		$event ? this.hospitalService.getCities($event)
			.then((cities) => {
				return this.cities = cities;
			}) : '';
	}

	getSources($event: string): void {
		if ($event && !/self/i.test($event)) {
			this.hospitalService.getSources($event)
				.then((sources) => {
					return this.sources = sources;
				}, (err) => {
					console.log(err);
					return this.sources = [{
						sourceid: 'SMC123',
						name: 'Dr. Some one'
					}];
				})
		}
	}

	getTurnNumber(): void {
		this.hospitalService.getTurnNumber()
			.then(turnNum => this.registration.hospitalInfo.turn = turnNum);
	}

	getDepartments(): void {
		this.hospitalService.getDepartments()
			.then(departments => this.departments = departments);
	}

	getDoctors(departmentId: string): void {
		this.hospitalService.getDoctors(departmentId)
			.then(doctors => this.doctors = doctors);
	}

	getListOfBanks(): void {
		this.hospitalService.getListOfBanks()
			.then((banks: any) => this.banks = banks)
	}

	setFees(doctor: any): void {
		this.registration.paymentDetails.total = doctor.fee;
		this.registration.consultantDepartment.doctorId = doctor.id;
		this.updateTextFields();
	}

	setBalanceAmount(netAmount: number, paidAmount: number): void {
		if (paidAmount <= netAmount) {
			this.registration.paymentDetails.balance = netAmount - paidAmount;
		} else {
			this.registration.paymentDetails.paidAmount = '';
			Materialize.toast('You are trying adding more paid amount than the bill.', 4000);
		}
		this.updateTextFields();
	}

	updateTextFields(): void {
		window.setTimeout(() => {
			Materialize.updateTextFields();
		}, 100);
	}

	calculateDiscount(total: number, discount: number): void {
		let disc: number = this.registration.paymentDetails.discount = Math.floor(total * discount / 100);
		this.registration.paymentDetails.netAmount = total - disc;
		this.hospitalService.geDiscountAuthorization()
			.then(discountDetails => this.authorizedPersons = discountDetails);
		this.updateTextFields();
	}

	selectCity(location: any): void {
		this.registration.address = Object.assign(this.registration.address, location);
		this.cities = null;
		Materialize.updateTextFields();
	}

	registerPatient(): void {
		this.hospitalService.registerPatient(this.registration);
	}

	ngOnInit(): void {
		$('.datepicker').pickadate({
			selectMonths: true, // Creates a dropdown to control month
			selectYears: 100, // Creates a dropdown of 15 years to control year
			max: true,
			onSet: function(arg: any) {
				if ('select' in arg) { //prevent closing on selecting month/year
					this.close();
				}
			}
		});
		this.getTurnNumber();
		this.getDepartments();
		this.registration = new Registration();
	}


}
