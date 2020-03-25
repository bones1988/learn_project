import jwtDecode from 'jwt-decode';
import refreshToken from './refreshToken'

const validateToken = () => {
    let token = localStorage.getItem('Bearer');
    if (token) {
        let exp = jwtDecode(token).exp;
        let now = Math.ceil(Date.now() / 1000);
        if (now > exp) {
            refreshToken();
        }
    }
}

export default validateToken;
