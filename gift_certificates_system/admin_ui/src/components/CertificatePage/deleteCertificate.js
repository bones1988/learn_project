import validateToken from "../../token/validateToken";

function deleteCertificate(id) {
    validateToken();
    return (
        fetch('http://localhost:8080/certificates/' + id, {
            method: 'DELETE',
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('Bearer'),
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            }
        })
    );
}

export default deleteCertificate;