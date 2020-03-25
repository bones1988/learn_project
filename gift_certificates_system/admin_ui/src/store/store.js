import { combineReducers, createStore, applyMiddleware } from 'redux'
import { errorReducer } from './reducers/errorReducer'
import { certificateReducer } from './reducers/certificateReducer'
import thunk from 'redux-thunk';
import { totalElementsReducer } from './reducers/totalElementsReducer';

const initialState = ({
    error: '',
    certificates: [],
    total: 0
})

const shopReducer = combineReducers({
    error: errorReducer,
    certificates: certificateReducer,
    total: totalElementsReducer
})

export const store = createStore(shopReducer, initialState, applyMiddleware(thunk))
