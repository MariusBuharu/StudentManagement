import React from 'react';
import './App.css';
import 'bootstrap/dist/js/bootstrap';
import Header from './components/header/Header';
import InstitutionsPage from './pages/institutions/InstitutionsPage';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import AboutPage from './pages/about/AboutPage';
import ContactPage from './pages/contact/ContactPage';
import InstitutionForm from './components/institutions/institution-form/InstitutionForm';
import UserList from './components/users/UserList';

function App() {
    return (
        <>
            <BrowserRouter>
                <Header />
                <div className={'container text-light mt-sm-3'}>
                    <Routes>
                        <Route path={'/'} element={<Navigate to='/institutions' replace />} />
                        <Route path={'institutions'}>
                            <Route index element={<InstitutionsPage />} />
                            <Route path={'add'} element={<InstitutionForm />} />
                            <Route path={':institutionId'}>
                                <Route index element={<InstitutionForm />} />
                                <Route path={'members'} element={<UserList />} />
                            </Route>
                        </Route>
                        <Route path={'/about'} element={<AboutPage />} />
                        <Route path={'/contact'} element={<ContactPage />} />
                    </Routes>
                </div>
            </BrowserRouter>
        </>
    );
}

export default App;
