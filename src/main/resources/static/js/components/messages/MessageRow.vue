<template>
    <v-card class="my-1">
        <v-card-title>
            <i class="mr-2">{{ message.id }}</i>
            {{ message.text }}
        </v-card-title>
        <media v-if="message.link" :message="message"></media>
        <v-row>
            <v-spacer></v-spacer>
            <v-card-actions class="mr-3">
                    <span>
                        <v-btn @click="edit" text rounded>Edit</v-btn>
                        <v-btn icon @click="del">
                            <v-icon>delete</v-icon>
                        </v-btn>
                    </span>
            </v-card-actions>
        </v-row>
        <comment-list
            :comments="message.comments"
            :message-id="message.id"
        >

        </comment-list>
    </v-card>
</template>

<script>
    import { mapActions } from 'vuex'
    import Media from "../media/Media.vue";
    import CommentList from "../comments/CommentList.vue";

    export default {
        props: ['message', 'editMessage'],
        components: {CommentList, Media},
        methods: {
            ...mapActions(['removeMessageAction']),
            edit() {
                this.editMessage(this.message)
            },
            del() {
                this.removeMessageAction(this.message)
            }
        }
    }
</script>

<style scoped>

</style>