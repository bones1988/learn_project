import React from 'react';
import { withRouter } from 'react-router-dom';
import queryString from 'query-string';

class localeButton extends React.Component {
    changeLanguage = () => {            
        let parameters = queryString.parse(this.props.location.search);        
        let currentlocale = queryString.parse(this.props.location.search).locale;
        if (!currentlocale || currentlocale === 'ru') {
            currentlocale = 'en'
        } else {
            currentlocale = 'ru'
        }
        parameters.locale = currentlocale;
        this.props.history.push(this.props.location.pathname + '?' + queryString.stringify(parameters))
    }

    render() {
        let currentLocale = queryString.parse(this.props.location.search).locale;
        if (!currentLocale||currentLocale==='en') {
            currentLocale = 'ru';
        } else {
            currentLocale = 'en';
        }
        return (
            <button onClick={this.changeLanguage} className='localeButton'>{currentLocale}</button>
        )
    }
}

export default withRouter(localeButton);
