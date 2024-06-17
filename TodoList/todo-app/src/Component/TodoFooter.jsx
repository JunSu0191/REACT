import React from 'react'

const TodoFooter = ({onallDelete, onCompleteAll}) => {
  return (
    <div className='footer'>
      <div className="item">
        <button className='btn' onClick={onallDelete}>전체삭제</button>
      </div>
      <div className="item">
        <button className='btn' onClick={onCompleteAll}>전체완료</button>
      </div>
    </div>
  )
}

export default TodoFooter