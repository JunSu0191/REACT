// 👨‍💻axios 모듈 설치
// npm install axios -> axios 폴더에서만 사용 가능

// axios 라이브러리 가져오기
const axios = require('axios');

// GET 요청 보내기
axios.get('https://httpbin.org/get')
  .then(response => {
    // 성공적인 응답 처리
    console.log(response.data);
  })
  .catch(error => {
    // 오류 처리
    console.error('Request failed:', error);
  });