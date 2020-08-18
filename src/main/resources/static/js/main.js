import Vue from 'vue'
import  vuetify  from 'plugin/vuetify.js'
import '@babel/polyfill'
import 'plugin/resource.js'
import store from 'store/store.js'

import App from 'page/app.vue'
import { connect } from "utils/ws";


if (frontendData.profile) {
    connect();
}

new Vue({
    el:'#app',
    vuetify,
    store,
    render: a=>a(App)
})