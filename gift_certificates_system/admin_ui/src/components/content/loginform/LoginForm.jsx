import React from 'react';
import { Form } from 'react-bootstrap';
import { Button } from 'react-bootstrap';
import loginRequest from './loginRequest'
import './LoginForm.css'
import { withRouter } from 'react-router-dom';
import jwt from 'jwt-decode';
import { localized } from '../../localization'
import queryString from 'query-string';

class LoginForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      login: '',
      password: '',
      message: '',
      loginError: '',
      passwordError: '',
      loginValid: false,
      passwordValid: false,
      formValid: false
    }
  }

  componentDidUpdate(prevProps, prevState, snapshot) {    
    if (queryString.parse(prevProps.location.search).locale !== queryString.parse(this.props.location.search).locale) {
      console.log('2')
      localized.setLanguage(queryString.parse(this.props.location.search).locale);
      this.setState({});
    }
  }

  componentDidMount = () => {
    this.setState({
      loginError: '',
      passwordError: '',
    })
  }

  onChange = (e) => {
    const name = e.target.name;
    const value = e.target.value;
    this.setState({ [name]: value },
      () => { this.validateField(name, value) });
  }

  login = async (e) => {
    e.preventDefault();
    const data = { login: this.state.login, password: this.state.password };
    try {
      const response = await loginRequest(data)
      if (response.status === 200) {
        const token = await response.json();
        localStorage.setItem('Bearer', token.bearer);
        localStorage.setItem('Refresh', token.refresh)
        this.props.handleLogin(jwt(token.bearer).sub);
        let role = jwt(token.bearer).role;
        if (role === 'ROLE_ADMINISTRATOR') {
          this.props.history.push('/certificates');
        } else {
          this.setState({ message: localized.onlyAdmins })
        }
      } else {
        const error = await response.json();
        this.setState({ message: error.message })
      }
    } catch (err) {
      this.setState({ message: localized.serviceUnavalable })
    }
  }


  validateField = (fieldName, value) => {
    let loginError = this.state.loginError;
    let passwordError = this.state.passwordError;
    let loginValid = this.state.loginValid;
    let passwordValid = this.state.passwordValid;
    switch (fieldName) {
      case 'login':
        loginValid = /^\w{3,30}$/.test(value);
        loginError = loginValid ? '' : localized.tooShort;
        break;
      case 'password':
        passwordValid = /^\w{4,30}$/.test(value);
        passwordError = passwordValid ? '' : localized.tooShort;
        break;
      default:
        break;
    }
    this.setState({
      loginError: loginError,
      passwordError: passwordError,
      loginValid: loginValid,
      passwordValid: passwordValid
    }, this.validateForm);
  }
  validateForm = () => {
    this.setState({
      formValid: this.state.loginValid &&
        this.state.passwordValid
    });
  }


  render() {   
    return (
      <div className='loginWrapper'>
        <div className='loginForm'>
          <div className='loginTitle'>
            <p className='loginText'>Login</p>
          </div>
          <Form className='loginInput' onSubmit={this.login}>
            <Form.Group  >
              <Form.Control isValid={!this.state.loginError} isInvalid={this.state.loginError} type="text" maxLength='30' name="login" value={this.state.login} onChange={this.onChange} placeholder="Username" />
              <Form.Text className="text-danger">{this.state.loginError}</Form.Text>
            </Form.Group>
            <Form.Group controlId="formBasicPassword">
              <Form.Control isValid={!this.state.passwordError} isInvalid={this.state.passwordError} type="password" maxLength='30' name="password" value={this.state.password} onChange={this.onChange} placeholder="Password" />
              <Form.Text className="text-danger">{this.state.passwordError}
              </Form.Text>
            </Form.Group>
            <Form.Group >
              <Form.Text className="text-danger">
                {this.state.message}
              </Form.Text>
            </Form.Group>
            <Form.Group>
              <div className="button">
                <Button className='loginButton' variant="primary" type="submit" disabled={!this.state.formValid}>
                  Login
            </Button>
              </div>
            </Form.Group>
          </Form>
        </div>
      </div>
    );
  }
}

export default withRouter(LoginForm);
