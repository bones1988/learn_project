import React from 'react';
import CertificatesList from '../content/certificates/CertificatesList'
import PageNum from '../content/pagination/PageNum'
import PageSizeList from '../content/pagination/PageSizeList'
import ErrorBar from '../content/errorbar/ErrorBar';
import SearchBar from '../content/searchBar/SearchBar'
import '../certificatesPage/CertificatesPage.css'
import { withRouter } from 'react-router-dom';

class CertificatesPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            elements: ''
        }
    }

    setElementsNum = (num) => this.setState({ elements: num });

    render() {
        return (
            <div className='certificatesPageWrapper'>
                <ErrorBar />
                <SearchBar />
                <CertificatesList setElements={this.setElementsNum} />
                <PageNum elements={this.state.elements} />
                <PageSizeList />
            </div>
        )
    }
}

export default withRouter(CertificatesPage);
