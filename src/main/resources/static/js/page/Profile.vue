<template>
    <v-container>
        <v-layout column>
            <v-flex>Profile user: {{profile.username}}</v-flex>
            <v-flex>User's email: {{profile.email}}</v-flex>
            <v-flex>User's last visit: {{profile.lastVisit}}</v-flex>
            <!--<v-flex><v-img :src="profile.userpic"></v-img></v-flex>-->
            <v-flex>User's subscribers: {{profile.subscribers && profile.subscribers.length}}</v-flex>
            <v-flex>User's subscriptions: {{profile.subscriptions && profile.subscriptions.length}}</v-flex>
        </v-layout>
        <v-btn v-if="!isMyProfile" @click="updateSubscription">
            {{isImSubscriber ? 'Unsubscribe' : 'Subscribe'}}
        </v-btn>
    </v-container>
</template>

<script>
    import profileAPI from "../plugin/profile.js";

    export default {
        data() {
            return {
                profile: {}
            }
        },
        computed: {
            isImSubscriber() {
                return this.profile.subscribers &&
                    this.profile.subscribers.find(subscription => {
                        return subscription.id === this.$store.state.profile.id
                    })
            },
            isMyProfile() {
                return !this.$route.params.id ||
                    this.$route.params.id === this.$store.state.profile.id
            }
        },
        watch: {
            '$route'() {
                this.updateProfile()
            }
        },
        methods: {
            async updateSubscription() {
                const data = await profileAPI.updateSubscription(this.profile.id)

                this.profile = await data.json()
            },
            async updateProfile() {
                const id = this.$route.params.id || this.$store.state.profile.id

                const data = await profileAPI.get(id)
                this.profile = await data.json()

                this.$forceUpdate()
            }
        },
        beforeMount() {
            this.updateProfile()
        }

    }
</script>

<style scoped>
    img {
        max-width: 100%;
        height: auto;
    }
    .v-image {
        max-width: 100%;
        height: auto;
    }
</style>