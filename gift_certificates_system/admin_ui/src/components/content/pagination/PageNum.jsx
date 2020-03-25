import React from 'react';
import Pagination from 'react-bootstrap/Pagination';
import { withRouter } from 'react-router-dom';
import queryString from 'query-string';
import { connect } from 'react-redux';

const FIRST_PAGE = 1;

const mapStateToProps = state =>
    ({ total: state.total })

let countMaxPage = (elements, size) => {
    return Math.ceil(elements / size)
}
class PageNum extends React.Component {
    constructor(props) {
        super(props)
        this.state = ({
            portion: 0,
            next: false
        })
    }
    pageNum = queryString.parse(this.props.location.search).page;

    componentDidMount() {
        let pageNum = queryString.parse(this.props.location.search).page;
        if (pageNum) {
            let current = Math.ceil(pageNum / 10)
            if (this.state.portion !== current)
                this.setState({ portion: current - 1 })
        }
    }

    changePage = (e) => {
        let pageNum = e.target.id
        let parameters = queryString.parse(this.props.location.search);
        parameters.page = pageNum;
        this.setState({ num: pageNum });
        this.props.history.push(this.props.location.pathname + '?' + queryString.stringify(parameters))
    }

    prevOrder = () => {
        let order = this.state.portion;
        if (order > 0) {
            this.setState({
                portion: order - 1
            })
        }
    }

    nextOrder = () => {
        let order = this.state.portion;
        this.setState({
            portion: order + 1
        })
    }

    render() {
        let active = queryString.parse(this.props.location.search).page;
        if (!active) {
            active = 1
        }
        let order = this.state.portion;
        let items = [];
        let next = false;
        let pageSize = queryString.parse(this.props.location.search).pageSize;
        if (!pageSize || pageSize > 50) {
            pageSize = 5;
        }
        let maxPage = countMaxPage(this.props.total, pageSize);
        items.push(
            <Pagination.Item variant="outline-dark" key={maxPage + 1} as="button" onClick={this.changePage} id={FIRST_PAGE}>{FIRST_PAGE}</Pagination.Item>);
        items.push(
            <Pagination.Prev key={maxPage + 2} as="button" onClick={this.prevOrder} />);
        for (let number = 1; number <= 10; number++) {
            items.push(
                <Pagination.Item active={number + (10 * order) === +active} key={number + (10 * order)} as="button" onClick={this.changePage} id={number + (10 * order)} >
                    {number + (10 * order)}
                </Pagination.Item>,
            );
            if (number + (10 * order) >= maxPage) {
                next = true;
                break;
            }
        }
        items.push(
            <Pagination.Next disabled={next} key={maxPage + 3} as="button" onClick={this.nextOrder} />);
        items.push(
            <Pagination.Item key={maxPage + 4} as="button" id={maxPage} onClick={this.changePage}>{maxPage}</Pagination.Item>);
        if (this.props.total) {
            return (
                <div className="pageNum">
                    <Pagination className="d-flex justify-content-center">
                        {items}
                    </Pagination>
                </div>
            )
        } else {
            return null
        }
    }
}

export default connect(mapStateToProps)(withRouter(PageNum));
