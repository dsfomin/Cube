import Vue from 'vue'

const profile = Vue.resource('/profile{/id}')

export default {
    get: id => profile.get({id}),
    updateSubscription: channelId => Vue.http.post(`/profile/subscription/${channelId}`)
}
