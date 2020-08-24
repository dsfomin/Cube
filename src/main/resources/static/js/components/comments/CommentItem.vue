<template>
    <v-list-item>
        <user-link
                :user = "comment.author"
                size="36"
        ></user-link>
        <v-list-item-content>
            <v-list-item-title>{{comment.text}}</v-list-item-title>
        </v-list-item-content>
    </v-list-item>
</template>

<script>
    import UserLink from "../UserLink.vue";
    export default {
        name: 'CommentItem',
        components: {UserLink},
        props: ['comment']
    }
</script>

<style scoped>
</style>