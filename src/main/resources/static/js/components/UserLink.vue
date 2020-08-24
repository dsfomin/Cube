<template>
    <router-link
        :to="`/user/${this.user.id}`"
        class="px-3"
    >
        <v-avatar
                v-if="user && user.userpic"
                :size="`${size}px`"
        >
            <img
                    :src="user.userpic"
                    :alt="user.username"
            >
        </v-avatar>

        <v-avatar
                v-else
                :size="`${size}px`"
                color="indigo"
        >
            <v-icon dark>account_circle</v-icon>
        </v-avatar>
        <span>{{ userName }}</span>
    </router-link>
</template>

<script>
    export default {
        name: "UserLink",
        props: ['size', 'user'],
        computed: {
            userName() {
                return this.user ? this.user.username : 'unknown user'
            }
        },
    }
</script>

<style scoped>

</style>