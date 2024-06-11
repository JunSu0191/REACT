// axios 는 라이브러리로, 포함시켜야한다.
// npm install axios
// 모듈이 설치되어 있어야한다.
const axios = require('axios')

const url = 'https://httpbin.org/post'
const data = {
    name : '김조은',
    age  : 25
}
const headers = {
    'Content-type' : 'application/json'
}

// axios POST 요청
axios.post(url, data, headers)
    .then(response => {
        console.log(`data : ${response.data}`);
        console.dir(response.data)
    })
    .catch(error => {
        console.log(`error : ${error}`);
    })