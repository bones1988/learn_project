import { SET_TOTAL } from '../actions/setTotalElements'

export function totalElementsReducer(state = { total: 0 }, action) {
    switch (action.type) {
        case SET_TOTAL:
            return action.total;
        default:
            return state;
    }
}
