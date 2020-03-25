import React from 'react';
import './Tag.css';

const Tag = (props) => {
    return (
        <div className='tagItem'>
            {props.name}
            <button onClick={() => props.handleCloseTag(props.arrayIndex)} className='closeTagButton'>X</button>
        </div>
    )
}

export default Tag;
