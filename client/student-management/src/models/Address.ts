export default interface IAddress {
    id: number;
    country: string;
    city: string;
    addressLineOne: string;
    addressLineTwo?: string;
    dateAdded: string;
}
