// Keep the Input import for now, we'll remove it later:
import { Component, EventEmitter, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Params }   from '@angular/router';
import { Registration } from './registration';
import { Subject } from 'rxjs/Subject';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';

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
	term$ = new Subject<string>();

	constructor(
		private hospitalService: HospitalService,
		private route: ActivatedRoute
	) { }

	getCities(term: string): void {
		this.hospitalService.getCities(term)
			.subscribe((cities) => {
				return this.cities = cities;
			});
	}

	getSources($event: string): void {
		if ($event && !/self/i.test($event)) {
			this.hospitalService.getSources($event)
				.then((sources) => {
					return this.sources = sources;
				}, (err) => {
					Materialize.toast('Could not fetch Sources. Try later.', 4000);
				})
		}
	}

	getTurnNumber(): void {
		this.hospitalService.getTurnNumber()
			.then(turnNum => {
				this.registration.hospitalInfo.turn = turnNum
				this.updateTextFields();
			});
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
		this.updateTextFields();
	}

	registerPatient(): void {
		this.registration.basicInfo.dob = $('#dob').val();
		this.hospitalService.registerPatient(this.registration)
			.then((response) => {
				if (/success/i.test(response.status)) {
					Materialize.toast('Registration Successful.', 4000);
					this.registration = new Registration();
				} else {
					Materialize.toast('Registration Failure.', 4000);
				}
			});
	}

	ngOnInit(): void {
		$('.datepicker').pickadate({
			selectMonths: true, // Creates a dropdown to control month
			selectYears: 100, // Creates a dropdown of 15 years to control year
			max: true,
			format: 'yyyy-mm-dd',
			onSet: function(arg: any) {
				if ('select' in arg) { //prevent closing on selecting month/year
					this.close();
				}
			}
		});
		this.term$
			.debounceTime(400)
			.distinctUntilChanged()
			.subscribe(term => this.getCities(term))
		this.getTurnNumber();
		this.getDepartments();
		this.registration = new Registration();
	}


}
