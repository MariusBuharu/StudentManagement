import * as React from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { RefObject, useEffect, useRef, useState } from 'react';
import ReactDatePicker from 'react-datepicker';
import moment from 'moment';
import 'react-datepicker/dist/react-datepicker.min.css';
import IInstitution from '../../../models/Institution';
import { editInstitution, saveInstitution, institutionById, parseDate} from '../../../services/InstitutionService';
import { faUser, faUsers } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export interface IInstitutionFormProps {}

const InstitutionForm: React.FunctionComponent<IInstitutionFormProps> = (props: IInstitutionFormProps) => {
    const navigate = useNavigate();
    const params = useParams();
    const institutionId: number = params['institutionId'] ? parseInt(params['institutionId']) : 0;
    const [institution, setInstitution] = useState<IInstitution>();
    const [getFoundedDate, setFoundedDate] = useState<Date>();

    const instForm: RefObject<HTMLFormElement> = useRef<HTMLFormElement>(null);
    const instName: RefObject<HTMLInputElement> = useRef<HTMLInputElement>(null);
    const insDesc: RefObject<HTMLTextAreaElement> = useRef<HTMLTextAreaElement>(null);
    const foundedDate: RefObject<ReactDatePicker> = useRef<ReactDatePicker>(null);
    const countryRef: RefObject<HTMLInputElement> = useRef<HTMLInputElement>(null);
    const cityRef: RefObject<HTMLInputElement> = useRef<HTMLInputElement>(null);
    const addressLineOne: RefObject<HTMLTextAreaElement> = useRef<HTMLTextAreaElement>(null);
    const addressLineTwo: RefObject<HTMLTextAreaElement> = useRef<HTMLTextAreaElement>(null);

    const configForm = function (ins: IInstitution) {
        setFoundedDate(parseDate(ins.foundedDate));
        setElementValue(instName, ins.institutionName);
        setElementValue(insDesc, ins.description);
        setElementValue(cityRef, ins.address.city);
        setElementValue(countryRef, ins.address.country);
        setElementValue(addressLineOne, ins.address.addressLineOne);
        setElementValue(addressLineTwo, ins.address.addressLineTwo ?? '');
    };
    useEffect(function () {
        if (institutionId) {
            institutionById(institutionId)
                .then(function (ins: IInstitution) {
                    setInstitution(ins);
                    configForm(ins);
                })
                .catch((err) => console.error(`Cannot fetch institution with id ${institutionId}`, err));
        } else setFoundedDate(new Date());
    }, []);

    const resetHandler = function (e: any) {
        if (!window.confirm(`Are you sure to reset your changes? This action cannot be undone`)) {
            e.preventDefault();
            return;
        }
        if (institution) {
            e.preventDefault();
            configForm(institution);
        } else {
            setFoundedDate(undefined);
        }
    };
    const setElementValue = function (refObject: RefObject<any>, value: string | number) {
        if (refObject) refObject.current.value = value;
    };

    const institutionMemberHandler = () => navigate('members');

    const formSubmitHandler = async function (e: any) {
        e.preventDefault();
        let ins: IInstitution = institution ?? ({ address: {} } as IInstitution);

        if (instName.current) ins.institutionName = instName.current?.value.trim();
        if (insDesc.current) ins.description = insDesc.current.value.trim();
        if (foundedDate.current) ins.foundedDate = moment(foundedDate.current?.props.selected).format('DD-MM-yyyy');
        if (countryRef.current) ins.address.country = countryRef.current.value;
        if (cityRef.current) ins.address.city = cityRef.current.value;
        if (addressLineOne.current) ins.address.addressLineOne = addressLineOne.current?.value;
        if (addressLineTwo.current) ins.address.addressLineTwo = addressLineTwo.current?.value;

        if (institution) {
            await editInstitution(ins).then(() => navigate('/'));
        } else {
            await saveInstitution(ins).then(() => navigate('/'));
        }
    };

    return (
        <>
            <div>
                <h1>
                    {institutionId ? (
                        <>
                            <span className={'text-warning'}>Edit</span> {institution?.institutionName}
                        </>
                    ) : (
                        'Add new institution'
                    )}
                </h1>
                <hr />
                <form ref={instForm} onSubmit={formSubmitHandler} className={'rounded bg-gradient shadow-lg p-3'}>
                    <div className={'mb-sm-3'}>
                        <label className={'form-label'} htmlFor='institutionName'>
                            * Institution name:
                        </label>
                        <input type='text' id={'institutionName'} className={'form-control'} ref={instName} autoComplete={'off'} required minLength={3} maxLength={45} />
                    </div>
                    <div className={'mb-sm-3'}>
                        <label htmlFor='insDescription'>Institution description:</label>
                        <textarea id='insDescription' className={'form-control'} cols={20} rows={3} ref={insDesc} maxLength={500}></textarea>
                    </div>

                    <div className={'row'}>
                        <div className={'col-sm-6'}>
                            <div className={'mb-sm-3'}>
                                <label className={'form-label'} htmlFor='country'>
                                    * Country:
                                </label>
                                <input type='text' id={'country'} className={'form-control'} ref={countryRef} autoComplete={'off'} required minLength={3} maxLength={45} />
                            </div>
                        </div>
                        <div className={'col-sm-6'}>
                            <div className={'mb-sm-3'}>
                                <label className={'form-label'} htmlFor='city'>
                                    * City:
                                </label>
                                <input type='text' id={'city'} className={'form-control'} ref={cityRef} autoComplete={'off'} required minLength={3} maxLength={45} />
                            </div>
                        </div>
                    </div>
                    <div className={'row'}>
                        <div className={'col-sm-6'}>
                            <div className={'mb-sm-3'}>
                                <label className={'form-label'} htmlFor='addressLineOne'>
                                    * Address line one:
                                </label>
                                <textarea id='addressLineOne' className={'form-control'} cols={20} rows={2} maxLength={500} ref={addressLineOne} required></textarea>
                            </div>
                        </div>
                        <div className={'col-sm-6'}>
                            <div className={'mb-sm-3'}>
                                <label className={'form-label'} htmlFor='addressLineTwo'>
                                    Address line two:
                                </label>
                                <textarea id='addressLineTwo' className={'form-control'} cols={20} rows={2} maxLength={500} ref={addressLineTwo}></textarea>
                            </div>
                        </div>
                    </div>

                    <div className={'row'}>
                        <div className={'col-sm-4'}>
                            <div>
                                <span>Founded date:</span>
                                <ReactDatePicker
                                    ref={foundedDate}
                                    className={'form-control'}
                                    id={'foundedDate'}
                                    placeholderText={'dd-MM-yyyy'}
                                    dropdownMode={'select'}
                                    showMonthDropdown={true}
                                    showYearDropdown={true}
                                    showWeekNumbers={true}
                                    isClearable={true}
                                    disabledKeyboardNavigation={true}
                                    withPortal={true}
                                    portalId={'foundedDatePortal'}
                                    maxDate={new Date()}
                                    showFourColumnMonthYearPicker={true}
                                    selected={getFoundedDate}
                                    dateFormat={'dd-MM-yyyy'}
                                    onChange={(date: Date) => setFoundedDate(date)}
                                    autoComplete={'off'}
                                    onKeyDown={(e) => e.preventDefault()}
                                />
                            </div>
                        </div>
                        {institution && (
                            <>
                                <div className={'col-sm-4'}>
                                    <div>
                                        <span>Date added:</span>
                                        <ReactDatePicker
                                            ref={foundedDate}
                                            className={'form-control'}
                                            id={'foundedDate'}
                                            selected={parseDate(institution?.dateAdded)}
                                            dateFormat={'dd-MM-yyyy'}
                                            onChange={(date: Date) => setFoundedDate(date)}
                                            autoComplete={'off'}
                                            onKeyDown={(e) => e.preventDefault()}
                                            readOnly={true}
                                            disabled={true}
                                        />
                                    </div>
                                </div>
                                <div className={'col-sm-4 align-self-end'}>
                                    <div>
                                        <button onClick={institutionMemberHandler} title={`View institution members`} type={'button'} style={{ display: 'inline' }} className={'btn btn-primary'}>
                                            {institution.appUsers.length ? (
                                                <>
                                                    <FontAwesomeIcon icon={institution.appUsers.length > 1 ? faUsers : faUser} /> {institution.appUsers.length} Members{' '}
                                                </>
                                            ) : (
                                                'No members'
                                            )}
                                        </button>
                                    </div>
                                </div>
                            </>
                        )}
                    </div>
                    <hr />
                    <div className={'btn-toolbar d-flex justify-content-between'}>
                        <div className={'btn-group'}>
                            <button type={'button'} onClick={() => navigate('/')} className={'btn btn-success me-sm-2'}>
                                Back
                            </button>
                        </div>
                        <div className={'btn-group'}>
                            <button type={'reset'} onClick={resetHandler} className={'btn btn-primary'}>
                                Reset
                            </button>
                            <button type={'submit'} className={'btn btn-primary'}>
                                Submit
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </>
    );
};

export default InstitutionForm;
