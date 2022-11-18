// @flow
import * as React from 'react';
import Navbar from './navbar/Navbar';

type Props = {};

function Header(props: Props) {
    return (
        <>
            <header className={'bg-dark text-light'}>
                <div className={'p-sm-2'}>
                    <h3>Student Management</h3>
                </div>
                <Navbar />
            </header>
        </>
    );
}

export default Header;
