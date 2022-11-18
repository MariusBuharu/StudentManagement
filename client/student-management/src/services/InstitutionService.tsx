import * as React from 'react';
import axios, { AxiosResponse } from 'axios';
import moment from 'moment';
import IInstitution from '../models/Institution';
import { CustomResponse } from '../models/responses';

const BASE_URL = `${process.env.REACT_APP_BASE_API_URL}/institutions`;

const saveInstitution = async function (institution: IInstitution, errorHandler?: Function) {
    const resp: AxiosResponse<IInstitution> = (await axios
        .post(BASE_URL, institution)
        .catch((err) => (errorHandler ? errorHandler() : console.error(`Failed to save institution`, institution, err)))) as AxiosResponse<IInstitution>;
    return resp.data;
};

const editInstitution = async function (institution: IInstitution, errorHandler?: Function) {
    const POST_URL = `${BASE_URL}/${institution.id}`;
    const resp: AxiosResponse<CustomResponse<IInstitution>> = (await axios
        .put(POST_URL, institution)
        .catch((err) => (errorHandler ? errorHandler() : console.error(`Unable to edit institution with id ${institution.id}`, err)))) as AxiosResponse<CustomResponse<IInstitution>>;
    return resp.data;
};
const getInstitutions = async function () {
    const resp = await axios.get(BASE_URL);
    const body = await resp.data;
    return body.data;
};
const institutionById = async function (institutionId: number | string) {
    const URL = `${BASE_URL}/${institutionId}`;
    const resp: AxiosResponse = (await axios.get(URL).catch((err) => console.error(`Cannot fetch institution by id = ${institutionId}`, err))) as AxiosResponse;
    return resp.data.data;
};
const deleteInstitutionById = async function (id: number) {
    const delete_url = BASE_URL + `/${id}`;
    const resp: AxiosResponse = (await axios.delete(delete_url).catch((err) => console.error(`Unable to remove institution with id ${id}`, err))) as AxiosResponse;
    const institutions = await getInstitutions().catch((err) => console.error(`Unable to fetch institutions!`, err));
    return {
        message: resp.data,
        institutions
    };
};
const deleteAllInstitutions = async function () {
    const resp: AxiosResponse = (await axios.delete(BASE_URL).catch((err) => console.error('Unable to remove all institutions!', err))) as AxiosResponse;
    const institutions = await getInstitutions();
    return { message: resp.data, institutions };
};
const parseDate = function (dateString: string, format?: string): Date {
    if (!format) format = 'DD-MM-yyyy';
    return moment(dateString, format).toDate();
};
export { getInstitutions, deleteInstitutionById, deleteAllInstitutions, saveInstitution, editInstitution, institutionById, parseDate };
