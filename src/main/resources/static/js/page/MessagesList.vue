<template>
    <v-container>
        <v-layout align-content-space-between column justify-start class="mx-3" >
            <messages-form :messageAttr="message"/>
            <message-row v-for="message in sortedMessages"
                         :key="message.id"
                         :message="message"
                         :editMessage="editMessage"
            />
        </v-layout>
    </v-container>
</template>

<script>
    import { mapGetters } from 'vuex'
    import MessageRow from 'components/messages/MessageRow.vue'
    import MessagesForm from 'components/messages/MessagesForm.vue'
    export default {
        components: {
            MessageRow,
            MessagesForm
        },
        data() {
            return {
                message: null
            }
        },
        computed: mapGetters(['sortedMessages']),
        methods: {
            editMessage(message) {
                this.message = message
            }
        }
    }
</script>

<style scoped>

</style>