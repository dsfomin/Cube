import Vue from 'vue'
import VueRouter from 'vue-router'
import MessagesList from 'page/MessagesList.vue'
import Auth from "../page/Auth.vue";
import Profile from "../page/Profile.vue";

Vue.use(VueRouter)

const routes = [
    { path: '/', component: MessagesList },
    { path: '/auth', component: Auth },
    { path: '/profile', component: Profile },
    { path: '*', component: MessagesList },
]

export default new VueRouter({
    mode: 'history',
    routes
})