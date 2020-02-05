import axios from 'axios'

const AXIOS = axios.create({
  baseURL: `/api/`
});


export default {
  closeCompletedActionEmitter() {
    return AXIOS.delete('sse/completed-action/close');
  }
}
