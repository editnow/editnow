import axios from 'axios'

const AXIOS = axios.create({
  baseURL: `/api/configuration`
});


export default {
  getActionTools() {
    return AXIOS.get('/action-tools')
  }
}
