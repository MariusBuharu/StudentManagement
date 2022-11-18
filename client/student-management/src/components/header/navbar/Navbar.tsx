import * as React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faBars } from '@fortawesome/free-solid-svg-icons';
import { Link } from 'react-router-dom';

export interface INavbarProps {}

const Navbar: React.FunctionComponent<INavbarProps> = (props: INavbarProps) => {
    return (
        <>
            <nav className={'navbar navbar-expand-lg bg-light shadow-lg'}>
                <div className={'container-fluid'}>
                    {/*<a className={'navbar-brand'} href='#'>*/}
                    {/*    <h3>Student</h3>*/}
                    {/*</a>*/}
                    <button
                        type={'button'}
                        data-bs-toggle={'collapse'}
                        data-bs-target={'#navbarSupportedContent'}
                        className={'btn btn-primary navbar-toggler'}
                        aria-controls='navbarSupportedContent'
                        aria-expanded='false'
                        aria-label='Toggle navigation'
                    >
                        <FontAwesomeIcon icon={faBars} />
                    </button>
                    <div className={'collapse navbar-collapse'} id={'navbarSupportedContent'}>
                        <ul className={'navbar-nav me-auto mb-2 mb-lg-0'}>
                            <li className={'nav-item'}>
                                <Link to={'/'} className={'nav-link active'} aria-current={'page'}>
                                    Institutions
                                </Link>
                            </li>
                            <li className={'nav-item'}>
                                <Link to={'/about'} className={'nav-link active'} aria-current={'page'}>
                                    About us
                                </Link>
                            </li>
                            <li className={'nav-item'}>
                                <Link to={'/contact'} className={'nav-link active'} aria-current={'page'}>
                                    Contact us
                                </Link>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </>
    );
};

export default Navbar;
