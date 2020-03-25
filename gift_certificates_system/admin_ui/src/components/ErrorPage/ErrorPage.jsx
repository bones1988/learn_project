import React from 'react'
import Alert from 'react-bootstrap/Alert'

class ErrorPage extends React.Component {
    render() {       
        return (
            <div className='certificatesPageWrapper'>
               <div className='errorBar' >
                <Alert key='error' variant='danger'>
                    {this.props.error}
                </Alert>
            </div>)
            </div>
        )
    }
}

export default ErrorPage;
