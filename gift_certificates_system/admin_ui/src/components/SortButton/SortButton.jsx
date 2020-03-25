import React from 'react';
import './SortButton.css';
import { withRouter } from 'react-router-dom';
import queryString from 'query-string';

class SortButton extends React.Component {

    changeSortState = (sortState) => {
        let parameters = queryString.parse(this.props.location.search);
        let currentSort = queryString.parse(this.props.location.search).sort;
        parameters.sort = sortState
        if (currentSort === sortState) {
            parameters.sort = sortState + 'desc'
        }
        this.props.history.push(this.props.location.pathname + '?' + queryString.stringify(parameters))
    }

    render() {
        let picture = '';
        let currentSort = queryString.parse(this.props.location.search).sort;
        let sortState = '';
        switch (this.props.title) {
            case 'Datetime':
                sortState = 'datetime';
                break;
            case 'Title':
                sortState = 'title';
                break;
            case 'Description':
                sortState = 'description';
                break;
            case 'Price':
                sortState = 'price';
                break;
            default:
                sortState = 'datetime'
                break;
        }
        if (currentSort === sortState) {
            picture = '▼'
        }
        if (currentSort === sortState + 'desc') {
            picture = '▲'
        }
        if (!currentSort && this.props.title === 'Datetime') {
            picture = '▼'
        }
        return (
            <button onClick={() => this.changeSortState(sortState)} className='sortButton'>{picture}{this.props.title}</button>
        )

    }
}

export default withRouter(SortButton);
