<template>
    <div>
        <div v-if="!profile">Необходимо авторизоваться
            <a href="/login">Google</a></div>
        <div v-else>
            <div>{{profile.name}}&nbsp;<a href="/logout">Выйти</a></div>
            <messages-list :messages="messages" />
        </div>
    </div>
</template>

<script>
    import MessagesList from "../components/messages/MessagesList.vue";
    import {addHandler} from "../utils/ws";
    import {getIndex} from "../utils/collections";

    export default {
        components: {MessagesList},
        data() {
            return {
                messages: frontendData.messages,
                profile: frontendData.profile
            }
        },
        created() {
            addHandler(data => {
                let index = this.messages.findIndex(message => message.id === data.id)

                if (index > -1) {
                    this.messages.splice(index, 1, data)
                } else {
                    this.messages.push(data)
                }
            })
        }
    }
</script>

<style scoped>

</style>