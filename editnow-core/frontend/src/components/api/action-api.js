import axios from 'axios'

const AXIOS = axios.create({
  baseURL: `/api/action`
});


export default {
  sendActionRequest(actionRequest) {
    return AXIOS.post('', actionRequest);
  },
  getOutputImageByActionId(actionId) {
    return AXIOS.get(`/${actionId}/output`);
  },
  newActionChain() {
    return AXIOS.post('/chain/new');
  },
  generateCode() {
    return AXIOS.get('/code/generate');
  }
}
