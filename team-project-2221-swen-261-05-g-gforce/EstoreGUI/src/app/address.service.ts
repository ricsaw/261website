import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";
import { Address } from "./Address";

@Injectable({
    providedIn: "root"
})
export class AddressService {
    address: Address = {
        id: 0,
        name: "",
        lastname: "",
        address: "",
        city: "",
        state: "",
        zip: 0,
        phone: "",
        defaultAddress: false
    };
    

    createAddress(value: any) {
        let addressId = value.addressId;
        let addressName = value.addressName;
        let addressLastname = value.addressLastname;
        let addressAddress = value.addressAddress;
        let addressCity = value.addressCity;
        let addressState = value.addressState;
        let addressZip = value.addressZip;
        let addressPhone = value.addressPhone;
        let addressDefault = value.addressDefault;

        this.address.id = addressId;
        this.address.name = addressName;
        this.address.lastname = addressLastname;
        this.address.address = addressAddress;
        this.address.city = addressCity;
        this.address.state = addressState;
        this.address.zip = addressZip;
        this.address.phone = addressPhone;
        this.address.defaultAddress = addressDefault;

        this.addAddress(this.address).subscribe( {
            next: (response) => console.log(response),
            error: (error) => console.log(error)
        });
    }
    private apiServerUrl = environment.apiBaseUrl;

    constructor(private http: HttpClient) {}

    public getAddress(addressId: number): Observable<Address> {
        return this.http.get<Address>(`${this.apiServerUrl}/address/${addressId}`);
    }

    public getAddresses(): Observable<Address[]> {
        return this.http.get<Address[]>(`${this.apiServerUrl}/address`);
    }

    public addAddress(address: Address): Observable<Address> {
        console.log(address);
        return this.http.post<Address>(`${this.apiServerUrl}/address/add`, address);
    }

    public updateAddress(address: Address): Observable<Address> {
        return this.http.put<Address>(`${this.apiServerUrl}/address/update`, address);
    }

    public deleteAddress(addressId: number): Observable<void> {
        return this.http.delete<void>(`${this.apiServerUrl}/address/delete/${addressId}`);
    }
    

}