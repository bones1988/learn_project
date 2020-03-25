import { SHOW_ERROR } from '../actions/showError'

export function errorReducer(state = { error: '' }, action) {
    switch (action.type) {
        case SHOW_ERROR:
            return action.errorMessage;
        default:
            return state;
    }
}
