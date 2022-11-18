import IUser from './User';
import IAddress from './Address';

export default interface IInstitution {
    id: number;
    institutionName: string;
    description: string;
    foundedDate: string;
    dateAdded: string;
    appUsers: IUser[];
    address: IAddress;
}
