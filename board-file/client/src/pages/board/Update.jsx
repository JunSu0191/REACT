import React from 'react'
import UpdateContainer from '../../containers/UpdateContainer'
import { useParams } from 'react-router-dom'

const Update = () => {
  const { no } = useParams()
  return (
    <>
        <UpdateContainer no= {no}/>
        {/* <UpdateContainer /> */}
    </>
  )
}

export default Update