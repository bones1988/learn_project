import 'bootstrap/dist/css/bootstrap.css';
import React from 'react';
import './Footer.css'
import { Navbar } from 'react-bootstrap';

class Footer extends React.Component {
    render() {
        return (
            <Navbar fixed="bottom" className="footer">
                <p className='label'>{new Date().getFullYear()}, EXPEDIA Student</p>
            </Navbar>
        );
    }
}

export default Footer;
