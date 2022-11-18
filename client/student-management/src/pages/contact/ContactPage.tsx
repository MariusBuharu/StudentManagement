import * as React from 'react';
import { Col, Row, Spinner } from 'reactstrap';
import { RefObject, useRef, useState } from 'react';
import GoogleMap from '../../components/contact/google-map/GoogleMap';
import ContactForm from '../../components/contact/contact-form/ContactForm';

export interface IInstitutionsPageProps {}

const InstitutionsPage: React.FunctionComponent<IInstitutionsPageProps> = (props: IInstitutionsPageProps) => {

    return (
        <>
            <h3>Contact page</h3>
            <hr />
            <GoogleMap/>
            <ContactForm className={'mt-4 bg-gradient p-sm-3 rounded-3'} />


        </>
    );
};

export default InstitutionsPage;
