import React from 'react';
import { Table } from 'react-bootstrap';
import { withRouter } from 'react-router-dom';
import queryString from 'query-string';
import './CertificatesList.css';
import ViewCertificatePage from '../../certificatePage/ViewCertificatePage';
import AddOrEditCertificatePage from './../../certificatePage/AddOrEditCertificatePage';
import DeleteCertificatePage from '../../certificatePage/DeleteCertificatePage';
import SortButton from '../../sortButton/SortButton';
import { connect } from 'react-redux';
import { getCertificatesFromApi } from '../../../store/actions/getCertificatesFromApi';


const mapStateToProps = state =>
  ({ certificates: state.certificates })

class CertificatesList extends React.Component {

  componentDidMount() {
    this.gettingCertificates();
  }

  componentDidUpdate(prevProps, prevState, snapshot) {
    if (prevProps.location.search !== this.props.location.search || prevProps.certificates.length !== this.props.certificates.length) {
      this.gettingCertificates()
    }
  }

  gettingCertificates = () => {
    let page = queryString.parse(this.props.location.search).page;
    if (!page) {
      page = 1;
    }
    let pageSize = queryString.parse(this.props.location.search).pageSize;
    if (!pageSize || pageSize > 50) {
      pageSize = 5;
    }
    let searchName = queryString.parse(this.props.location.search).searchName;
    let tagName = queryString.parse(this.props.location.search).tagName
    let sort = queryString.parse(this.props.location.search).sort;
    this.props.dispatch(getCertificatesFromApi(page, pageSize, searchName, tagName, sort));
  }

  render() {
    return (
      <div className="certificatesTable">
        <Table className='cerificatesData' striped bordered hover size="sm">
          <thead>
            <tr>
              <th><SortButton title='Datetime' /></th>
              <th><SortButton title='Title' /></th>
              <th>Tags</th>
              <th><SortButton title='Description' /></th>
              <th><SortButton title='Price' /></th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {this.props.certificates.map(c => (
              <tr key={c.id}>
                <td>{c.createDate}</td>
                <td>{c.name}</td>
                <td>{c.tags.map(t => (t.name + ' '))}</td>
                <td>{c.description}</td>
                <td>{c.price}</td>
                <td>
                  <div className="d-flex justify-content-center">
                    <ViewCertificatePage certificate={c} />
                    <AddOrEditCertificatePage getCertificates={this.gettingCertificates} certificate={c} title='edit' />
                    <DeleteCertificatePage getCertificates={this.gettingCertificates} id={c.id} />
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </Table>
      </div>
    );
  }
}

export default connect(mapStateToProps)(withRouter(CertificatesList));
