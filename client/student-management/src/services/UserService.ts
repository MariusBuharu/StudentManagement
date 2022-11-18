import axios, { AxiosResponse } from 'axios';
import IUser from '../models/User';
import { CustomResponse, CustomResponseList } from '../models/responses';

const BASE_URL = `${process.env.REACT_APP_BASE_API_URL}/users`;

export const getUsers = async function (): Promise<IUser[]> {
    const resp: AxiosResponse<CustomResponseList<IUser>> = await axios.get(BASE_URL);
    return resp.data.data;
};

export const userById = async function (userId: number | string): Promise<IUser> {
    const url: string = `${BASE_URL}/${userId}`;
    const resp: AxiosResponse<CustomResponse<IUser>> = await axios.get(url);

    return resp.data.data;
};
