import * as React from 'react';
import { Button, Col, Form, FormGroup, Input, Label } from 'reactstrap';
import { RefObject, useRef } from 'react';
import IEmailModel from '../../../models/EmailModel';
import { sendMail } from '../../../services/ContactService';

export interface IContactFormProps {
    className?: string;
}

const ContactForm: React.FunctionComponent<IContactFormProps> = (props: IContactFormProps) => {
    const formRef: RefObject<HTMLFormElement> = useRef(null);

    const contactHandler = async function(this: HTMLFormElement, e: any) {
        e.preventDefault();
        if (!formRef.current) return;
        const mail: IEmailModel = {
            // @ts-ignore
            firstName: formRef.current.elements.firstName.value,
            // @ts-ignore
            lastName: formRef.current.elements.lastName.value,
            // @ts-ignore
            email: formRef.current.elements.email.value,
            // @ts-ignore
            subject: formRef.current.elements.subject.value,
            // @ts-ignore
            message: formRef.current.elements.message.value
        };
        const msg:string = await sendMail(mail);
        console.log(msg);
        console.log(mail.email);
    };
    return (
        <>
            <Form innerRef={formRef}  className={props.className ?? ''} onSubmit={contactHandler}>
                <FormGroup row>
                    <Label for='firstName' sm={2}>First name:</Label>
                    <Col sm={10}>
                        <Input type='text' name='firstName' id='firstName' placeholder='First name' required />
                    </Col>
                </FormGroup>
                <FormGroup row>
                    <Label for="lastName" sm={2}>Last name:</Label>
                    <Col sm={10}>
                        <Input  type="text" name="lastName" id="lastName" placeholder="Last name" required />
                    </Col>
                </FormGroup>
                <FormGroup row>
                    <Label for="email" sm={2}>Email:</Label>
                    <Col sm={10}>
                        <Input type="email" name="email" id="email" placeholder="Email" required />
                    </Col>
                </FormGroup>

                <FormGroup row>
                    <Label for="subject" sm={2}>Subject:</Label>
                    <Col sm={10}>
                        <Input type="text" name="subject" id="subject" placeholder="Subject" required />
                    </Col>
                </FormGroup>

                <FormGroup row>
                    <Label for="message" sm={2}>Message:</Label>
                    <Col sm={10}>
                        <Input type="textarea" name="message" id="message" placeholder="Message" required />
                    </Col>
                </FormGroup>

                <FormGroup check row>
                    <Col sm={{ size: 10, offset: 2 }}>
                        <Button color={'primary'}>Send</Button>
                    </Col>
                </FormGroup>
            </Form>
        </>
    );
};

export default ContactForm;
