import React from 'react';
import Form from 'react-bootstrap/Form'
import { Button } from 'react-bootstrap';
import './SearchBar.css'
import { withRouter } from 'react-router-dom';
import queryString from 'query-string';

class SearchBar extends React.Component {
    constructor(props) {
        super(props)
        this.state = ({
            search: ''
        })
    }

    goSearch = () => {
        let page = queryString.parse(this.props.location.search);
        let searchName = this.parseSearchString(this.state.search).searchName
        let searchTag = this.parseSearchString(this.state.search).tagName
        delete page.searchName
        delete page.tagName
        if (searchName) {
            page.searchName = searchName
            delete page.page
            this.setState({
                search: ''
            })
        }
        if (searchTag) {
            page.tagName = searchTag
            delete page.page

            this.setState({
                search: ''
            })
        }
        this.props.history.push(this.props.location.pathname + '?' + queryString.stringify(page))
    }

    parseSearchString = (string) => {
        let params = string.split(' ')
        let name = [];
        let tagName = [];
        params.forEach(param => {
            if (/^\w+$/.test(param)) {
                name.push(param);
            } else if (/^#\(\w+\)$/.test(param)) {
                tagName.push(param);
            }
        })
        let nameString = '';
        let tagNameString = '';
        nameString = nameString + name.map(element => element.toString());
        tagNameString = tagNameString + tagName.map(element => element.replace(/[( ) #]/g, "").toString())
        return ({
            searchName: nameString,
            tagName: tagNameString
        })
    }

    enterSearch = (e) => {
        this.setState({
            search: e.target.value
        })
    }
    render() {
        return (
            <div className='searchBar' >
                <div className='seachText'>
                    <Form.Control onChange={this.enterSearch} type="text" placeholder="Search" value={this.state.search} />
                </div>
                <div className='searchButton'>
                    <Button onClick={this.goSearch} variant="primary" block>GO!</Button>
                </div>
            </div>
        )
    }
}

export default withRouter(SearchBar);
