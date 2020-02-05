import axios from 'axios'

const AXIOS = axios.create({
  baseURL: `/api/user`
});


export default {
  generateUUID() {
    return AXIOS.get('/uuid')
  }
}
