import React from 'react';
import { Navbar } from 'react-bootstrap';
import './Header.css';
import { withRouter } from 'react-router-dom';
import jwt from 'jwt-decode'
import AddOrEditCertificatePage from './../certificatePage/AddOrEditCertificatePage'
import LocaleButton from './LocaleButton'


class Header extends React.Component {
    logout = (e) => {
        e.preventDefault();
        localStorage.clear();
        this.props.handleLogout();
        this.props.history.push('/login');
    };

    render() {
        let haveToken = localStorage.getItem('Bearer')
        let userName;
        if (haveToken) {
            userName = jwt(haveToken).sub
        }

        return (
            <Navbar bg="dark" variant="dark">
                <Navbar.Brand >Admin UI</Navbar.Brand>
                {this.props.logged || haveToken ? <AddOrEditCertificatePage handleAddCertificate={this.props.handleAddCertificate} title='add' /> : ''}
                {this.props.logged || haveToken ? <Navbar.Collapse className="justify-content-end">
                    <Navbar.Text className='loginIcon'>
                        <div className='loginText'>
                            {this.props.userName || userName}
                        </div>
                    </Navbar.Text>
                    <Navbar.Text className='logoutLink'>
                        <a href='logout' onClick={this.logout}>Logout</a>
                    </Navbar.Text>                 
                    <Navbar.Text className='logoutLink'>
                        <LocaleButton/>
                    </Navbar.Text>
                </Navbar.Collapse> : 
                <Navbar.Collapse className="justify-content-end">
                     <LocaleButton/>
                    </Navbar.Collapse>}
            </Navbar>
        );
    }
}

export default withRouter(Header);
