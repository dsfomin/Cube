<template>
    <v-layout row>
        <v-text-field
                label="New message"
                placeholder="Write something"
                v-model="text"
                @keyup.enter="save"
        ></v-text-field>
        <div class="ml-2 pt-2">
            <v-btn @click="save">
                Save
            </v-btn>
        </div>
    </v-layout>
</template>

<script>
    import { mapActions } from 'vuex'
    export default {
        props: ['messageAttr'],
        data() {
            return {
                text: null,
                id: null
            }
        },
        watch: {
            messageAttr(newVal) {
                this.text = newVal.text
                this.id = newVal.id
            }
        },
        methods: {
            ...mapActions(['addMessageAction', 'updateMessageAction']),
            save() {
                const message = {
                    id: this.id,
                    text: this.text
                }
                if (this.id) {
                    this.updateMessageAction(message)
                } else {
                    this.addMessageAction(message)
                }
                this.text = null
                this.id = null
            }
        }
    }
</script>

<style scoped>

</style>