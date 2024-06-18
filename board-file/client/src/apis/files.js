import axios from 'axios'

// 업로드
export const upload = (formData, headers) => axios.post(`/files`, formData, headers)

