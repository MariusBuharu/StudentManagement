import * as React from 'react';
import { Button, Col, Modal, ModalBody, ModalFooter, ModalHeader, Row } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faUser } from '@fortawesome/free-solid-svg-icons';
import IUser from '../../../models/User';
import IAddress from '../../../models/Address';
export interface IUserModalProps {
    user?: IUser;
    modalOpen: boolean;
    closeHandler: React.MouseEventHandler<HTMLButtonElement>;
}

const UserModal: React.FunctionComponent<IUserModalProps> = (props: IUserModalProps) => {
    const user: IUser | undefined = props.user;
    const address: IAddress | undefined = user?.address;
    const modalOpen: boolean = props.modalOpen;
    const closeUserModalHandler = props.closeHandler;
    return (
        <>
            <Modal isOpen={modalOpen}>
                <ModalHeader className={'d-flex justify-content-between'}>
                    <div>
                        <FontAwesomeIcon icon={faUser} />
                        &nbsp; {user?.firstName} {user?.lastName}
                    </div>
                </ModalHeader>
                <ModalBody>
                    <Row>
                        <Col sm={12} md={4} lg={4}>
                            <h6>First name:</h6>
                            <p>{user?.firstName}</p>
                        </Col>
                        <Col sm={12} md={4} lg={4}>
                            <h6>Last name:</h6>
                            <p>{user?.lastName}</p>
                        </Col>
                        <Col sm={12} md={4} lg={4}>
                            <h6>Age:</h6>
                            <p>{user?.age}</p>
                        </Col>
                    </Row>
                    <hr />
                    {address && (
                        <>
                            <h6>Address:</h6>
                            <Row>
                                <Col sm={12} md={12} lg={6}>
                                    <p>
                                        <strong>Country:</strong> {address.country}
                                    </p>
                                </Col>
                                <Col sm={12} lg={6}>
                                    <p>
                                        <strong>City:</strong> {address.city}
                                    </p>
                                </Col>
                            </Row>
                            <Row>
                                <Col sm={12} md={12} lg={12}>
                                    <h6>Address line one:</h6>
                                    <p>{address.addressLineOne}</p>
                                </Col>
                            </Row>
                            {address.addressLineTwo && (
                                <>
                                    <Row>
                                        <Col sm={12} md={12} lg={12}>
                                            <h6>Address line two:</h6>
                                            <p>{address.addressLineTwo}</p>
                                        </Col>
                                    </Row>
                                </>
                            )}
                        </>
                    )}
                </ModalBody>
                <ModalFooter>
                    <Button onClick={closeUserModalHandler} color={'success'}>
                        Close
                    </Button>
                </ModalFooter>
            </Modal>
        </>
    );
};

export default UserModal;
