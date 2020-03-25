export const SHOW_ERROR = 'SHOW_ERROR';

export function showError(errorMessage) {    
    return {
        type: SHOW_ERROR,
        errorMessage:errorMessage
    }
}