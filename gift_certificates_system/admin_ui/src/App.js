import React from 'react';
import Header from './components/header/Header';
import Footer from './components/footer/Footer';
import LoginForm from './components/content/loginform/LoginForm'
import CertificatesPage from './components/certificatesPage/CertificatesPage'
import { Route, Switch } from 'react-router-dom';
import ErrorPage from './components/errorPage/ErrorPage'
import jwtDecode from 'jwt-decode';
import validateToken from './token/validateToken';

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      logged: false,
      userName: '',
      certificates: []
    }
  }

handleAddCertificate = (certificate) => {
  let temp = this.state.certificates.slice(0)
  temp.push(certificate)
  this.setState({ certificates: temp })
}

handleLogin = (userName) => {
  this.setState({
    logged: true
  })
  this.setState({
    userName: userName
  })
}

handleLogout = () => {
  this.setState({
    logged: false
  })
}

render() {
  let logged = localStorage.getItem('Bearer');
  let role = '';
  if (logged) {
    validateToken();
    role = jwtDecode(logged).role
  }
  logged = localStorage.getItem('Bearer')
  if (!logged || role !== 'ROLE_ADMINISTRATOR') {
    return (
      <div className='mainWrapper'>
        <Header handleLogout={this.handleLogout} />
        <LoginForm handleLogin={this.handleLogin} />
        <Footer />
      </div>
    )
  }
  return (
    <div className='mainWrapper'>
      <Header handleAddCertificate={this.handleAddCertificate} handleError={this.handleError} logged={this.state.logged} handleLogout={this.handleLogout} userName={this.state.userName} />
      <Switch>
        <Route path='/certificates'>
          <CertificatesPage certificates={this.state.certificates} />
        </Route>
        <Route path='/login'>
          <ErrorPage error='You have to logout first!' />
        </Route>
        <Route>
          <ErrorPage error='No such page!' />
        </Route>
      </Switch>
      <Footer />
    </div>
  );
}
}

export default App;
