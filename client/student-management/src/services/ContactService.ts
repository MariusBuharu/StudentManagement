import axios, { AxiosResponse } from 'axios';
import IEmailModel from '../models/EmailModel';

const BASE_URL = `${process.env.REACT_APP_BASE_API_URL}/contact`;

export const sendMail = async function(mail: IEmailModel): Promise<string>{
    return await axios.post(BASE_URL, mail);
}
