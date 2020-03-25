import React from 'react';
import Alert from 'react-bootstrap/Alert'
import './ErrorBar.css'
import { connect } from 'react-redux'
import { showError } from '../../../store/actions/showError'

const mapStateToProps = state => ({
    error: state.error
})

const closeError = (props) => (
    props.dispatch(showError(''))
)

const ErrorBar = (props) => {
    if (props.error) {
        return (
            <div className='errorBar' >
                <Alert key='error' onClose={() => closeError(props)} dismissible variant='danger'>
                    {props.error}
                </Alert>
            </div>)
    } else {
        return (
            null
        )
    }

}

export default connect(mapStateToProps)(ErrorBar);
