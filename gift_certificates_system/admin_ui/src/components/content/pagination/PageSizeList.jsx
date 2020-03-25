import React from 'react';
import { Dropdown, DropdownButton } from 'react-bootstrap';
import { withRouter } from 'react-router-dom';
import queryString from 'query-string';

class PageSizeList extends React.Component {
  constructor(props) {
    super(props)
    this.state = ({
      size: queryString.parse(this.props.location.search).pageSize
    })
  }

  changeSize = (e) => {
    let pageSize = e.target.value;
    let parameters = queryString.parse(this.props.location.search);
    parameters.pageSize = pageSize;
    parameters.page = 1;
    this.setState({ size: pageSize });
    this.props.history.push(this.props.location.pathname + '?' + queryString.stringify(parameters))
  }

  render() {
    let pageSize = this.state.size
    if (!pageSize || pageSize > 50) {
      pageSize = 5
    }

    return (
      <div className="pageSizeList">
        <DropdownButton drop='up' variant="outline-dark" className="pageSizeButton" title={pageSize}>
          <Dropdown.Item as="button" value="10" onClick={this.changeSize}>10</Dropdown.Item>
          <Dropdown.Item as="button" value="20" onClick={this.changeSize}>20</Dropdown.Item>
          <Dropdown.Item as="button" value="50" onClick={this.changeSize}>50</Dropdown.Item>
        </DropdownButton>
      </div>
    );
  }
}

export default withRouter(PageSizeList);
