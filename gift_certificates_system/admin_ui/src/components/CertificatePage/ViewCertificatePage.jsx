import React from 'react';
import Modal from 'react-bootstrap/Modal';
import { Button } from 'react-bootstrap';
import Tag from '../tags/Tag'

class ViewCertificatePage extends React.Component {
  constructor(props) {
    super(props)
    this.state = ({
      show: false
    })
  }

  showCertificate = () => {
    this.setState({
      show: true
    })
  }

  hideCertificate = () => {
    this.setState({
      show: false
    })
  }

  render() {
    const tags = []
    this.props.certificate.tags.map(tag => tags.push(<Tag handleCloseTag={() => { }} key={tag.id} name={tag.name} />))
    return (
      <div>
        <Button onClick={this.showCertificate} variant="primary"> View  </Button>
        <Modal onHide={()=>null} show={this.state.show} centered>
          <Modal.Header className="d-flex justify-content-center" >
            <Modal.Title>{this.props.certificate.name}</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <p>{this.props.certificate.description}</p>
            <p>created : {this.props.certificate.createDate}</p>
            <p>duration: {this.props.certificate.duration} days</p>
            <p>price: {this.props.certificate.price}</p>
            <div className='tagsBlock'>
              <p>Tags:</p>
              {tags}
            </div>
          </Modal.Body>
          <Modal.Footer className="d-flex justify-content-center">
            <Button onClick={this.hideCertificate} variant="primary">Close</Button>
          </Modal.Footer>
        </Modal>
      </div>)
  }
}

export default ViewCertificatePage;
