<template>
    <v-app>
        <v-app-bar app color="indigo" dark>
            <v-app-bar-nav-icon></v-app-bar-nav-icon>
            <v-toolbar-title>Cube</v-toolbar-title>
            <v-btn text class="ml-2"
                   v-if="profile"
                   :disabled="$route.path === '/'"
                   @click="showMessages"
            >
                Messages
            </v-btn>
            <v-spacer></v-spacer>
            <v-btn v-if="profile"
                   text
                   :disabled="$route.path === '/profile'"
                   @click="showProfile"
            >
                {{profile.username}}
            </v-btn>
            <v-btn v-if="profile" icon href="/logout">
                <v-icon >exit_to_app</v-icon>
            </v-btn>
        </v-app-bar>
        <v-main>
            <router-view></router-view>
        </v-main>
    </v-app>
</template>

<script>
    import { mapState, mapMutations } from 'vuex'
    import { addHandler } from 'utils/ws.js'
    export default {
        computed: mapState(['profile']),
        methods: {
            ...mapMutations([
                'addMessageMutation',
                'updateMessageMutation',
                'removeMessageMutation',
                'addCommentMutation'
            ]),
            showMessages() {
                this.$router.push('/')
            },
            showProfile() {
                this.$router.push('/profile')
            }
        },
        created() {
            addHandler(data => {
                if (data.objectType === 'MESSAGE') {
                    switch (data.eventType) {
                        case 'CREATE':
                            this.addMessageMutation(data.body)
                            break
                        case 'UPDATE':
                            this.updateMessageMutation(data.body)
                            break
                        case 'REMOVE':
                            this.removeMessageMutation(data.body)
                            break
                        default:
                            console.error(`Looks like the event type if unknown "${data.eventType}"`)
                    }
                } else if (data.objectType === 'COMMENT') {
                    switch (data.eventType) {
                        case 'CREATE':
                            this.addCommentMutation(data.body)
                            break
                        default:
                            console.error(`Looks like the event type if unknown "${data.eventType}"`)
                    }
                } else {
                    console.error(`Looks like the object type if unknown "${data.objectType}"`)
                }
            })
        },
        beforeMount() {
            if (!this.profile) {
                this.$router.replace('/auth')
            }
        }
    }
</script>

<style scoped>

</style>