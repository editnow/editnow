import Vue from 'vue'
import Router from 'vue-router'
import WorkspaceView from './view/WorkspaceView'

Vue.use(Router);

const router = new Router({
    mode: 'history',
    routes: [
        { path: '/', component: WorkspaceView },
        { path: '*', redirect: '/' }
    ]
});

export default router;