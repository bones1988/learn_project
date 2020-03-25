import { showError } from './showError';
import { loadCertificates } from './loadCertificates';
import getCertificates from '../../components/content/certificates/getCertificates';
import { setTotalElements } from './setTotalElements';

export const getCertificatesFromApi = (page, pageSize, searchName, tagName, sort) => {
    return dispatch => {
        getCertificates(page, pageSize, searchName, tagName, sort)
            .then(certificates => {
                if (certificates.status === 200) {
                    dispatch(setTotalElements(certificates.headers.get('Total')))
                    certificates.json()
                        .then(json => dispatch(loadCertificates(json)))
                } else {
                    certificates.json()
                        .then(json => dispatch(showError(json.message)))
                }
            })
            .catch(error => dispatch(showError('Service unavailable now')))
    }
}
