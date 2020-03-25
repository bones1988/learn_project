import validateToken from "../../../token/validateToken";

function getCertificates(page, pageSize, searchName, tagName, sort) {
    validateToken();
    let url = 'http://localhost:8080/certificates?page=' + page + '&pageSize=' + pageSize;
    if (searchName) {
        url = url + '&name=' + searchName;
    }
    if (tagName) {
        url = url + '&tag=' + tagName;
    }
    let sortState = '';
    switch (sort) {
        case 'datetimedesc':
            sortState = 'createdesc';
            break;
        case 'title':
            sortState = 'name';
            break;
        case 'titledesc':
            sortState = 'namedesc';
            break;
        case 'description':
            sortState = 'description';
            break;
        case 'descriptiondesc':
            sortState = 'descriptiondesc';
            break;
        case 'price':
            sortState = 'price';
            break;
        case 'pricedesc':
            sortState = 'pricedesc';
            break;
        default: sortState = 'create';
            break;
    }
    url = url + '&sort=' + sortState
    return (fetch(url, {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('Bearer'),
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    })
    )
}

export default getCertificates;
