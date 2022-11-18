import * as React from 'react';
import { useEffect, useState } from 'react';
import jsLogo from './logo_js.png';
import {userById } from '../../services/UserService';
import { useNavigate, useParams } from 'react-router-dom';
import { Button, ButtonToolbar, Card, CardBody, CardSubtitle, CardText, CardTitle, Col, ListGroup, ListGroupItem, Row } from 'reactstrap';
import { editInstitution, institutionById } from '../../services/InstitutionService';
import IUser from '../../models/User';
import IInstitution from '../../models/Institution';
import UserModal from './UserModal/UserModal';

export interface IUserListProps {}

const UserList: React.FunctionComponent<IUserListProps> = (props: IUserListProps) => {
    const navigate = useNavigate();
    const params = useParams();
    const [userList, setUserList] = useState<IUser[]>();
    const [user, setUser] = useState<IUser>();
    const [institution, setInstitution] = useState<IInstitution>();
    const [modalOpen, setModalOpen] = useState<boolean>(false);

    const institutionId: number = params['institutionId'] ? parseInt(params['institutionId']) : 0;
    useEffect(function () {
        institutionById(institutionId)
            .then(function (institution: IInstitution) {
                setInstitution(institution);
                setUserList(institution.appUsers);
            })
            .catch(function (err) {
                console.error('Cannot fetch institution by id', institutionId, err);
                navigate('/error');
            });
    }, []);

    const openUserModalHandler = async function (e: React.MouseEvent<HTMLButtonElement>) {
        const btn: HTMLButtonElement = e.target as HTMLButtonElement;
        const userId: string | null = btn.getAttribute('data-user-id');
        if (!userId) return;

        const user: IUser = await userById(userId);
        setUser(user);
        setModalOpen(true);
    };
    const closeUserModalHandler = function () {
        setModalOpen(false);
    };
    const removeUserHandler = function (e: React.MouseEvent<HTMLButtonElement>) {
        const btn: HTMLButtonElement = e.target as HTMLButtonElement;
        const userId: string | null = btn.getAttribute('data-user-id');
        if (!userId || !institution || !userList) return;
        const actualUser: IUser | undefined = userList.find((u: IUser) => u.id === parseInt(userId));
        const confirmMsg: string = `Are you sure to remove ${actualUser?.firstName} ${actualUser?.lastName} from ${institution.institutionName}?`;
        if (!window.confirm(confirmMsg)) return;
        institution.appUsers = userList?.filter((user: IUser) => user !== actualUser);

        editInstitution(institution).then((institution: any) => setUserList(institution.data.appUsers));
    };
    return (
        <>
            <div className={'mt-sm-5'}>
                <div className={'d-flex justify-content-between'}>
                    <div>
                        <h3>
                            <strong>{institution?.institutionName}</strong> members
                        </h3>
                    </div>
                    <div>
                        <Button color={'primary'} onClick={() => navigate(`/institutions/${institutionId}`)}>
                            Back
                        </Button>
                    </div>
                </div>
                <hr />
                <Row className={'mt-2'}>
                    {userList?.map((user: IUser, index: number) => (
                        <Col key={index} xs={12} md={6} lg={4} className={'mb-5'}>
                            <Card style={{ width: '18rem' }}>
                                <img alt={'placeholder img'} height={200} src={jsLogo} />
                                <CardBody className={'text-dark'}>
                                    <CardTitle tag={'h4'}>
                                        #{index + 1} {user.firstName} {user.lastName}
                                    </CardTitle>
                                    <CardSubtitle className={'mb-2 text-muted'} tag={'h5'}>
                                        {user.role.roleName.substring(0, 1)}
                                        {user.role.roleName.substring(1).toLowerCase()}
                                    </CardSubtitle>
                                    <hr />
                                    <ListGroup flush>
                                        <ListGroupItem>
                                            <CardText>
                                                {user.address ? (
                                                    <span>
                                                        {user.address.country}, {user.address.city}
                                                    </span>
                                                ) : (
                                                    <span>Address not defined!</span>
                                                )}
                                            </CardText>
                                        </ListGroupItem>
                                        <ListGroupItem>
                                            <CardText>Age: {user.age}</CardText>
                                        </ListGroupItem>
                                        <ListGroupItem>
                                            <CardText>Email: {user.email}</CardText>
                                        </ListGroupItem>
                                        <ListGroupItem>
                                            <CardText>Phone: {user.phoneNumber ?? 'Not defined'}</CardText>
                                        </ListGroupItem>
                                    </ListGroup>

                                    <hr />
                                    <ButtonToolbar className={'d-flex justify-content-between'}>
                                        <Button data-user-id={user.id} onClick={openUserModalHandler} color={'success'}>
                                            View
                                        </Button>
                                        <Button data-user-id={user.id} onClick={removeUserHandler} color={'danger'}>
                                            Remove
                                        </Button>
                                    </ButtonToolbar>
                                </CardBody>
                            </Card>
                        </Col>
                    ))}
                </Row>
                <UserModal user={user} modalOpen={modalOpen} closeHandler={closeUserModalHandler} />
            </div>
        </>
    );
};

export default UserList;
