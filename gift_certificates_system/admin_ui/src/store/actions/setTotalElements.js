export const SET_TOTAL = 'SET_TOTAL';

export function setTotalElements(elements) {
    return {
        type: SET_TOTAL,
        total: elements
    }
}
