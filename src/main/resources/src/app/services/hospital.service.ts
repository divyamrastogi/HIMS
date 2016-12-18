import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';
import { Headers, RequestOptions } from '@angular/http';
import { Registration } from '../registration/registration';
@Injectable()
export class HospitalService {
	// 192.168.1.11
	hostname: string = process.env.ENV === 'production' ? '' : 'http://localhost:3000';
	loginUrl: string = `${this.hostname}/login`;
	citiesUrl: string = `${this.hostname}/city?prefix=`;
	sourcesUrl: string = `${this.hostname}/source?source=`;
	turnUrl: string = `${this.hostname}/turnno`;
	departmentsUrl: string = `${this.hostname}/department`;
	doctorsUrl: string = `${this.hostname}/doctor?departmentid=`;
	getAuthorizedPersonUrl: string = `${this.hostname}/autorisation`;
	banksUrl: string = `${this.hostname}/bank`;
	registerUrl: string = `${this.hostname}/patient/register`;

	registerPatient(registration: Registration): Promise<any> {
		let headers = new Headers({ 'Content-Type': 'application/json' });
		let options = new RequestOptions({ headers: headers });
		return this.http.post(this.registerUrl, registration, options)
			.toPromise()
			.then((response) => response.json() as any)
			.catch(this.handleError);
	}

	login(credentials: any): Promise<Object> {
		let headers = new Headers({ 'Content-Type': 'application/json' });
		let options = new RequestOptions({ headers: headers });
		return this.http.post(this.loginUrl, credentials, options)
			.toPromise()
			.then((response: any) => {
				return response.json() as any;
			});
	}

	get(url: string): Promise<Object[]> {
		return this.http.get(url)
			.toPromise()
			.then((response) => response.json() as any)
			.catch(this.handleError);
	}

	getListOfBanks(): Promise<Object[]> {
		return this.get(this.banksUrl);
	}

	geDiscountAuthorization(): Promise<Object[]> {
		return this.get(this.getAuthorizedPersonUrl);
	}

	getTurnNumber(): Promise<number> {
		return this.http.get(this.turnUrl)
			.toPromise()
			.then((response) => {
				if (!response.json().turnno) {
					return Promise.resolve(Math.floor(Math.random() * 100));
				}
				return response.json().turnno as number
			})
			.catch(this.handleError);
	}

	getDepartments(): Promise<Object[]> {
		return this.get(this.departmentsUrl);
	}

	getDoctors(departmentId: string): Promise<Object[]> {
		return this.get(this.doctorsUrl + departmentId);
	}

	getSources(source: string): Promise<Object[]> {
		return this.get(this.sourcesUrl + source);
	}

	getCities(prefix: string) {
		return this.http.get(this.citiesUrl + prefix)
			.map(response => response.json());
	}

	private handleError(error: any): Promise<any> {
		console.error('An error occurred', error); // for demo purposes only
		return Promise.reject(error.message || error);
	}

	constructor(private http: Http) {
	}
}
