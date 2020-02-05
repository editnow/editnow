import axios from 'axios'

const AXIOS = axios.create({
  baseURL: `/api/app/info`
});


export default {
  getAppBuildInfo() {
    return AXIOS.get('/build')
  }
}
