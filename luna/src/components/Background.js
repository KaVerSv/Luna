import React from 'react'
import "../css/style.css"

const background = (props) => {
  return (
      <div className="shop-container">
          {props.children}
      </div>
  )
}

export default background
