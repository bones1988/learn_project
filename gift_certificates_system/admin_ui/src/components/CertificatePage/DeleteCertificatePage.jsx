import React from 'react'
import { Button } from 'react-bootstrap';
import Modal from 'react-bootstrap/Modal';
import deleteCertificate from './deleteCertificate';
import './DeleteCertificatePage.css';
import { connect } from 'react-redux'
import { showError } from '../../store/actions/showError';

class DeleteCertificatePage extends React.Component {
    constructor(props) {
        super(props)
        this.state = ({
            show: false
        })
    }

    showWindow = () => {
        this.setState({
            show: true
        })
    }

    cancelDelete = () => {
        this.setState({
            show: false
        })
    }

    onDeleteCertificate = (id) => {
        deleteCertificate(id)
            .then(response => {
                if (response.status === 204) {
                    this.props.getCertificates();
                    this.setState({
                        show: false
                    })
                } else {
                    response.json()
                        .then(json => {
                            this.props.dispatch(showError(json.message))
                            this.setState({
                                show: false
                            })
                        })
                }
            })
            .catch(error => {
                this.setState({
                    show: false
                })
                this.props.dispatch(showError('Service unavailable now'))
            })
    }

    render() {
        return (
            <div>
                <Button onClick={this.showWindow} variant="danger">Delete</Button>
                <Modal onHide={()=>null} show={this.state.show} centered>
                    <Modal.Header className='deleteWindowHeader' >
                        <Modal.Title>Delete confirmation</Modal.Title>
                    </Modal.Header>
                    <Modal.Body >
                        <p>Do you really want to delete certificate with id = {this.props.id} ?</p>
                        <div className="d-flex justify-content-center">
                            <Button className='deleteButton' onClick={() => this.onDeleteCertificate(this.props.id)} variant="danger">Yes</Button>
                            <Button className='cancellButton' onClick={this.cancelDelete} variant="outline-dark">Cancel</Button>
                        </div>
                    </Modal.Body>
                </Modal>
            </div>
        )
    }
}

export default connect()(DeleteCertificatePage);
