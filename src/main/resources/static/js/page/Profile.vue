<template>
    <v-container>
        <v-layout column>
            <v-flex>Profile user: {{profile.username}}</v-flex>
            <v-flex>User's email: {{profile.email}}</v-flex>
            <v-flex>User's last visit: {{profile.lastVisit}}</v-flex>
            <!--<v-flex><v-img :src="profile.userpic"></v-img></v-flex>-->
        </v-layout>
    </v-container>
</template>

<script>
    import { mapState } from 'vuex'
    export default {
        computed: mapState(['profile']),
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