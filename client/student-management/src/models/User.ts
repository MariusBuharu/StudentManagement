import IRole from './Role';
import IAddress  from './Address';


export default interface IUser {
    id: number;
    role: IRole;
    address: IAddress;
    firstName: string;
    lastName: string;
    username: string;
    email: string;
    phoneNumber: string;
    dob: string;
    age: number;
    isActive: boolean;
}
