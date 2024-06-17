import './App.css';
import React from 'react'
// class App extends React.Component {
//   render() {

//     // 1 . React JavaScript로 엘리먼트 생성
//     // const link = React.createElement('a',{
//     //   href : 'http://www.google.com',
//     //   target : '_blank',
//     //   style : {color:'blue'}
//     // }, '구글사이트')

//     // const box = React.cloneElement('div', {
//     //   className:box
//     // }, '컨테이너')

//     // const element = React.createElement('div', null,
//     // [link,box]
//     // )

//     // 2 . JSX 로 엘리먼트 생성
//     // const element2 = (
// <div>
//   <h1>JSX 엘리먼트</h1>
//   <a href="https://www.google.com"
//   target='_blank'
//   style={{color: 'red'}}
//   >구글 사이트</a>np
// </div>
//     // )

//     // return element2


//   }



// }

// 함수형 컴포넌트
function App() {
  return (
    // 프래그먼트
    <>
      <div>
        <h1>JSX 엘리먼트</h1>
        <a href="https://www.google.com"
          target='_blank'
          style={{ color: 'red' }}
        >구글 사이트</a>
      </div>
      <p style={{color: 'blue', fontSize: '90px'}}>
        프래그먼트를 쓰면, 실제로는 상위 태그가,
         랜더링 되지않는다.
      </p>
    </>
  )
}
export default App;
