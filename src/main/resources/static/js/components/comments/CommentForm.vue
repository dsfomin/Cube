<template>
    <v-layout row >
        <v-text-field
                label="Add comment"
                placeholder="Write something"
                v-model="text"
                @keyup.enter="save"
        />
        <div class="ml-2 pt-2">
            <v-btn @click="save">
                Add
            </v-btn>
        </div>
    </v-layout>
</template>

<script>
    import { mapActions } from 'vuex'
    export default {
        name: 'CommentForm',
        props: ['messageId'],
        data() {
            return {
                text: ''
            }
        },
        methods: {
            ...mapActions(['addCommentAction']),
            async save() {
                await this.addCommentAction({
                    text: this.text,
                    message: {
                        id: this.messageId
                    }
                })
                this.text = ''
            }
        }
    }
</script>

<style scoped>
</style>