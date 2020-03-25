import validateToken from "../../token/validateToken";

function editCertificate(data) {
    validateToken();
    return (
        fetch('http://localhost:8080/certificates/' + data.id, {
            method: 'PUT',
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('Bearer'),
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify(data),
        })
    );
}

export default editCertificate;
