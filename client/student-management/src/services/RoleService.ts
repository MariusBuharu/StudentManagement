import axios from 'axios';
import IRole from '../models/Role';

const BASE_URL = `${process.env.REACT_APP_BASE_API_URL}/roles`;

const findAll = async function(): Promise<IRole[]>{
     const response = await axios.get(BASE_URL);

     return response.data.data;
}
