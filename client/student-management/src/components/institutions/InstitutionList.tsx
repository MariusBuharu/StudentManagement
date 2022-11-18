import * as React from 'react';
import { useState, useEffect, useRef, RefObject } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPlus, faTrash, faEdit, faExclamationTriangle, faUser, faUsers } from '@fortawesome/free-solid-svg-icons';
import { useNavigate } from 'react-router-dom';
import IInstitution from '../../models/Institution';
import { deleteInstitutionById, getInstitutions, deleteAllInstitutions } from '../../services/InstitutionService';

export interface IInstitutionProps {}

const InstitutionList: React.FunctionComponent<IInstitutionProps> = (props: IInstitutionProps) => {
    const navigate = useNavigate();
    const actionsHead: RefObject<HTMLTableHeaderCellElement> = useRef<HTMLTableHeaderCellElement>(null);
    const actionsBody: RefObject<HTMLTableSectionElement> = useRef<HTMLTableSectionElement>(null);
    const btnHideElements: RefObject<HTMLButtonElement> = useRef<HTMLButtonElement>(null);
    const [institutions, setInstitutions] = useState([]);

    useEffect(function () {
        getInstitutions()
            .then((institutions) => setInstitutions(institutions))
            .catch((err) => console.error('Failed to setInstitutions', err));
    }, []);

    const removeUserHandler = async function (this: any, institution: IInstitution) {
        if (!window.confirm(`Are you sure to remove ${institution.institutionName}?`)) {
            return;
        }
        const resp = await deleteInstitutionById(institution.id);
        setInstitutions(resp.institutions);
    };

    const institutionFormNavigate = (id?: number) => navigate(id ? `${id}` : 'add');

    const removeAllInstitutionsHandler = async function () {
        if (!window.confirm('Warning! This action will remove all institutions!')) {
            return;
        }
        await deleteAllInstitutions().catch((err) => console.error(`Unexpected error occurred trying to remove all institutions`, err));
        navigate('/');
    };

    const toggleActionsHandler = function () {
        const headElement = actionsHead.current;
        const bodyElement = actionsBody.current;
        const btn = btnHideElements.current;

        if (headElement?.classList.contains('d-none')) {
            headElement?.classList.remove('d-none');
            if (btn) btn.innerText = 'Hide actions';
        } else {
            headElement?.classList.add('d-none');
            if (btn) btn.innerText = 'Show actions';
        }

        bodyElement?.querySelectorAll('tr').forEach(function (row: HTMLTableRowElement) {
            const actions = row.querySelector('td.actions');
            actions?.classList.contains('d-none') ? actions?.classList.remove('d-none') : actions?.classList.add('d-none');
        });
    };
    const initTableBodyRows = function (institutions: IInstitution[]) {
        return (
            <>
                {institutions.map((value: IInstitution, index: number) => (
                    <tr key={value.id}>
                        <td>{index + 1}</td>
                        <td title={value.description}>{value.institutionName}</td>
                        <td>
                            {value.appUsers.length > 0 ? (
                                <>
                                    <FontAwesomeIcon icon={value.appUsers.length > 1 ? faUsers : faUser} /> {value.appUsers.length}
                                </>
                            ) : (
                                <>0</>
                            )}
                        </td>
                        <td>{value.foundedDate}</td>
                        <td>{value.dateAdded}</td>
                        <td className={'actions'}>
                            <div className={'btn-toolbar'}>
                                <button type={'button'} title={`Edit ${value.institutionName}`} className={'btn btn-primary me-sm-1'} onClick={() => institutionFormNavigate(value.id)}>
                                    <FontAwesomeIcon icon={faEdit} />
                                </button>
                                <button type={'button'} title={`Remove ${value.institutionName}`} className={'btn btn-danger'} onClick={() => removeUserHandler(value)}>
                                    <FontAwesomeIcon icon={faTrash} />
                                </button>
                            </div>
                        </td>
                    </tr>
                ))}
            </>
        );
    };

    return (
        <>
            <div className={'btn-toolbar mb-sm-2 d-flex justify-content-between'}>
                <div className={'btn-group'}>
                    <button type={'button'} title={'Add new institution-list'} className={'btn btn-primary'} onClick={() => institutionFormNavigate()}>
                        <FontAwesomeIcon icon={faPlus} />
                    </button>
                </div>

                <div className={'btn-group'}>
                    <button type={'button'} onClick={removeAllInstitutionsHandler} className={'btn btn-danger'} title={'Remove all institutions'}>
                        <FontAwesomeIcon icon={faExclamationTriangle} />
                    </button>
                    <button ref={btnHideElements} type={'button'} className={'btn btn-primary'} onClick={toggleActionsHandler}>
                        Hide actions
                    </button>
                </div>
            </div>
            <table className={'table table-stripped table-bordered table-hover'}>
                <thead className={'table-dark'}>
                    <tr>
                        <th>#</th>
                        <th>Institution name</th>
                        <th>Members</th>
                        <th>Founded date</th>
                        <th>Date added</th>
                        <th ref={actionsHead}>Actions</th>
                    </tr>
                </thead>
                <tbody className={'table-primary'} ref={actionsBody}>
                    {initTableBodyRows(institutions)}
                </tbody>
            </table>
        </>
    );
};

export default InstitutionList;
