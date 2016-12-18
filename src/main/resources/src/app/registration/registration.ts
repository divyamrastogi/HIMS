class BasicInfo {
	public title: string;
	public name: string;
	public dob: string;
	public sex: string;
	public relation: string;
	public relativeName: string;
}

class Address {
	public address: string;
	public city: string;
	public state: string;
	public district: string;
	public pinCode: number;
	public phone: string;
}

class Source {
	public source: string;
	public id: string;
}

class HospitalInfo {
	public sourceType: string;
	public sourceId: string;
	public turn: number;
	public category: string;
}

export class Department {
	public name: string;
	public id: string;
}

class ConsultantDepartment {
	public department: string;
	public doctorId: string;
}

class PaymentDetails {
	public total: number;
	public netAmount: number;
	public discount: number;
	public discountDetails: string;
	public paidAmount: number;
	public paymentMode: string;
	public bankName: string;
	public cardNumber: string;
	public others: string;
}

export class Registration {
	public basicInfo: any | BasicInfo = {};
	public address: any | Address = {};
	public hospitalInfo: any | HospitalInfo = {};
	public consultantDepartment: any | ConsultantDepartment = {};
	public paymentDetails: any | PaymentDetails = {};
	constructor() {

	}
}
