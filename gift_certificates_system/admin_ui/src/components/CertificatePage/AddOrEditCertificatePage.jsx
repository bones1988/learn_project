import React from 'react'
import Modal from 'react-bootstrap/Modal';
import { Button, Form, Row, Col } from 'react-bootstrap';
import Tag from '../tags/Tag'
import saveCertificate from './saveCertificate';
import editCertificate from './editCertificate';
import { connect } from 'react-redux';
import { showError } from '../../store/actions/showError';

const TITLE_REGEXP = /^\w{6,30}$/;
const DESCRIPTION_REGEXP = /^[A-Za-z0-9\s.,!]{12,1000}$/;
const DURATION_REGEXP = /^[0-9\b]+$/;
const PRICE_REGEXP = /^[0-9]+([,.][0-9]{1,2})?$/;
const TAG_NAME_REGEXP = /^\w{6,30}$/;
const EMPTY_CERTIFICATE = ({
    title: '',
    description: '',
    duration: '',
    price: '',
    tagName: '',
    show: false,
    tags: [],
    titleError: '',
    descriptionError: '',
    durationError: '',
    priceError: '',
    tagNameError: '',
    titleValid: false,
    descriptionValid: false,
    durationValid: false,
    priceValid: false,
    tagNameValid: false,
    formValid: false
})

class AddOrEditCertificatePage extends React.Component {
    constructor(props) {
        super(props)
        if (this.props.certificate) {
            this.state = ({
                title: this.props.certificate.name,
                description: this.props.certificate.description,
                duration: this.props.certificate.duration,
                price: this.props.certificate.price,
                show: false,
                certificate: this.props.certificate,
                tags: this.props.certificate.tags,
                tagName: '',
                titleError: '',
                descriptionError: '',
                durationError: '',
                priceError: '',
                tagNameError: '',
                titleValid: TITLE_REGEXP.test(this.props.certificate.name),
                descriptionValid: DESCRIPTION_REGEXP.test(this.props.certificate.description),
                durationValid: DURATION_REGEXP.test(this.props.certificate.duration),
                priceValid: PRICE_REGEXP.test(this.props.certificate.price),
                tagNameValid: false,
                formValid: TITLE_REGEXP.test(this.props.certificate.name) && DESCRIPTION_REGEXP.test(this.props.certificate.description) &&
                    DURATION_REGEXP.test(this.props.certificate.duration) && PRICE_REGEXP.test(this.props.certificate.price)
            })
        } else {
            this.state = (EMPTY_CERTIFICATE)
        }
    }

    validateField = (fieldName, value) => {
        let titleError = this.state.titleError;
        let descriptionError = this.state.descriptionError;
        let durationError = this.state.durationError;
        let priceError = this.state.priceError;
        let tagNameError = this.state.tagNameError;
        let titleValid = this.state.titleValid;
        let descriptionValid = this.state.descriptionValid;
        let durationValid = this.state.durationValid;
        let priceValid = this.state.priceValid;
        let tagNameValid = this.state.tagNameValid;
        switch (fieldName) {
            case 'title':
                titleValid = TITLE_REGEXP.test(value);
                titleError = titleValid ? '' : 'Incorrect';
                break;
            case 'description':
                descriptionValid = DESCRIPTION_REGEXP.test(value);
                descriptionError = descriptionValid ? '' : 'Incorrect';
                break;
            case 'duration':
                durationValid = DURATION_REGEXP.test(value);
                if (value > 365) {
                    durationValid = false;
                }
                durationError = durationValid ? '' : 'Incorrect';
                break;
            case 'price':
                priceValid = PRICE_REGEXP.test(value);
                priceError = priceValid ? '' : 'Incorrect';
                break;
            case 'tagName':
                tagNameValid = TAG_NAME_REGEXP.test(value);
                tagNameError = tagNameValid ? '' : 'Incorrect';
                break;
            default:
                break;
        }
        this.setState({
            titleError: titleError,
            descriptionError: descriptionError,
            durationError: durationError,
            priceError: priceError,
            tagNameError: tagNameError,
            titleValid: titleValid,
            descriptionValid: descriptionValid,
            durationValid: durationValid,
            priceValid: priceValid,
            tagNameValid: tagNameValid
        }, this.validateForm);
    }




    validateForm = () => {
        this.setState({
            formValid: this.state.titleValid &&
                this.state.descriptionValid && this.state.durationValid && this.state.priceValid
        });
    }

    showWindow = () => {
        this.setState({
            show: true
        })
    }

    cancelEdit = () => {
        if (this.props.certificate) {
            this.setState({
                title: this.props.certificate.name,
                description: this.props.certificate.description,
                duration: this.props.certificate.duration,
                price: this.props.certificate.price,
                show: false,
                certificate: this.props.certificate,
                tags: this.props.certificate.tags,
                tagName: ''
            })
        } else {
            this.setState(EMPTY_CERTIFICATE)
        }
    }

    handleCloseTag = (index) => {
        let copy = this.state.tags.slice(0);
        copy.splice(index, 1)
        this.setState({
            tags: copy
        })

    }

    onChange = (e) => {
        const name = e.target.id;
        const value = e.target.value;
        this.setState({ [name]: value },
            () => { this.validateField(name, value) });
    }

    addTag = () => {
        let copy = this.state.tags.slice(0);
        let haveName = false;
        let name = this.state.tagName;
        copy.map(tag => {
            if (tag.name === name) {
                haveName = true;
            }
            return null;
        })
        if (!haveName) {
            copy.push({
                id: Math.random(),
                name: this.state.tagName
            })
            this.setState({
                tags: copy
            })
        }
        this.setState({
            tagName: '',
            tagNameError: '',
            tagNameValid: false
        })
    }

    edit = (id) => {
        let certificateToEdit = ({
            id: id,
            name: this.state.title,
            description: this.state.description,
            duration: this.state.duration,
            price: this.state.price,
            tags: this.state.tags
        })
        editCertificate(certificateToEdit)
            .then(res => {
                if (res.status === 200) {
                    this.props.getCertificates();
                    this.setState({
                        show: false
                    })
                } else {
                    res.json()
                        .then(json => {
                            this.cancelEdit();
                            this.props.dispatch(showError(json.message));
                            this.setState({
                                show: false
                            })
                        })
                }
            })
            .catch(err => {
                this.setState({
                    show: false
                })
                this.cancelEdit();
                this.props.dispatch(showError('Service unavailable now'));
            });
    }

    save = () => {
        let certificateToSave = ({
            name: this.state.title,
            description: this.state.description,
            duration: this.state.duration,
            price: this.state.price,
            tags: this.state.tags
        })
        saveCertificate(certificateToSave)
            .then(res => {
                if (res.status === 200) {
                    this.props.handleAddCertificate(certificateToSave);
                    window.location.reload()
                    this.setState({
                        show: false
                    })
                } else {
                    res.json()
                        .then(json => {
                            this.props.dispatch(showError(json.message));
                            this.setState({
                                show: false
                            })
                        })
                }
                this.cancelEdit();
            })
            .catch(err => {
                this.setState({
                    show: false
                })
                this.cancelEdit();
                this.props.dispatch(showError('Service unavailable now'));
            });
    }

    render() {
        let tagList = []
        let withId = ''
        let title = '';
        let buttonVariant = 'primary'
        if (this.props.title === 'add') {
            title = 'Add new'
        }
        if (this.props.title === 'edit') {
            title = 'Edit'
            withId = ' with id = ' + this.props.certificate.id
            buttonVariant = 'warning'
        }
        let index = 0;
        tagList = this.state.tags.map(tag => {
            let tags = []
            tags.push(<Tag handleCloseTag={this.handleCloseTag} arrayIndex={index} key={tag.id} name={tag.name} />)
            index += 1;
            return tags;
        });
        return (
            <div>
                <Button onClick={this.showWindow} variant={buttonVariant}>{title}</Button>
                <Modal onHide={()=>null} show={this.state.show} centered>
                    <Modal.Header className="d-flex justify-content-center" >
                        <Modal.Title>{title} certificate {withId} </Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Form.Group as={Row}>
                            <Form.Label column sm="3">
                                Title :
                                </Form.Label>
                            <Col sm="9">
                                <Form.Control isValid={this.state.titleValid} isInvalid={!this.state.titleValid} id='title' onChange={this.onChange} placeholder='Enter title' value={this.state.title} type='text' />
                                <Form.Text className="text-danger">{this.state.titleError}</Form.Text>
                            </Col>
                        </Form.Group>
                        <Form.Group as={Row}>
                            <Form.Label column sm="3">
                                Description:
                                </Form.Label>
                            <Col sm="9">
                                <Form.Control isValid={this.state.descriptionValid} isInvalid={!this.state.descriptionValid} id='description' onChange={this.onChange} placeholder='Enter description' value={this.state.description} type='text' />
                                <Form.Text className="text-danger">{this.state.descriptionError}</Form.Text>
                            </Col>
                        </Form.Group>
                        <Form.Group as={Row}>
                            <Form.Label column sm="3">
                                Duration:
                                </Form.Label>
                            <Col sm="9">
                                <Form.Control isValid={this.state.durationValid} isInvalid={!this.state.durationValid} onChange={this.onChange} placeholder='Enter duration' id='duration' value={this.state.duration} type='text' />
                                <Form.Text className="text-danger">{this.state.durationError}</Form.Text>
                            </Col>
                        </Form.Group>
                        <Form.Group as={Row}>
                            <Form.Label column sm="3">
                                Price:
                                </Form.Label>
                            <Col sm="9">
                                <Form.Control isValid={this.state.priceValid} isInvalid={!this.state.priceValid} id='price' onChange={this.onChange} placeholder='Enter price' value={this.state.price} type='text' />
                                <Form.Text className="text-danger">{this.state.priceError}</Form.Text>
                            </Col>
                        </Form.Group>
                        <Form.Group as={Row}>
                            <Form.Label column sm="3">
                                Tags
                            </Form.Label>
                            <Col sm="7">
                                <Form.Control isValid={this.state.tagNameValid} isInvalid={!this.state.tagNameValid} id='tagName' placeholder='Enter tag to add' onChange={this.onChange} value={this.state.tagName} type='text' />
                                <Form.Text className="text-danger">{this.state.tagNameError}</Form.Text>
                            </Col>
                            <Col sm="2">
                                <Button disabled={!this.state.tagNameValid} onClick={this.addTag} variant="primary">Add</Button>
                            </Col>
                        </Form.Group>
                        <div className='tagsBlock'>
                            {tagList}
                        </div>
                    </Modal.Body>
                    <Modal.Footer className="d-flex justify-content-center">
                        {this.props.title === 'edit' ?
                            <Button disabled={!this.state.formValid} onClick={() => this.edit(this.props.certificate.id)} variant="primary">Save</Button> :
                            <Button disabled={!this.state.formValid} onClick={this.save} variant="primary">Save</Button>
                        }
                        <Button onClick={this.cancelEdit} variant="outline-dark">Cancel</Button>
                    </Modal.Footer>
                </Modal>
            </div>)
    }
}

export default connect()(AddOrEditCertificatePage);
