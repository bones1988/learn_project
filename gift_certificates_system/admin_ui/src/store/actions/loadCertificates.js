export const LOAD_CERTIFICATES = 'LOAD_CERTIFICATES';

export function loadCertificates(certificates) {    
    return {
        type: LOAD_CERTIFICATES,
        certificates:certificates
    }
}