import * as React from 'react';
import { Outlet } from 'react-router-dom';
import InstitutionList from '../../components/institutions/InstitutionList';

export interface IInstitutionsPageProps {}

const InstitutionsPage: React.FunctionComponent<IInstitutionsPageProps> = (props: IInstitutionsPageProps) => {
    return (
        <>
            <InstitutionList />
        </>
    );
};

export default InstitutionsPage;
