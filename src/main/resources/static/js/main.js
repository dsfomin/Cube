import Vue from 'vue'
import VueResource from 'vue-resource'
import App from 'page/app.vue'
import { connect } from "./utils/ws";

if (frontendData.profile) {
    connect();
}

Vue.use(VueResource)

new Vue({
    el: '#app',
    render: a => a(App)
})