import { LOAD_CERTIFICATES } from '../actions/loadCertificates'

export function certificateReducer(state = { certificates: [] }, action) {
    switch (action.type) {
        case LOAD_CERTIFICATES:
            return action.certificates;
        default:
            return state;
    }
}
