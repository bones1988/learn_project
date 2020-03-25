import validateToken from '../../token/validateToken'

function saveCertificate(data) {
    validateToken();
    return (
        fetch('http://localhost:8080/certificates', {
            method: 'POST',
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('Bearer'),
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify(data),
        })
    );
}

export default saveCertificate;
