<template>
    <v-card class="my-1">
        <v-row class="px-4">
            <v-card-title>
                <i class="mr-2">{{ message.id }}</i>
                {{ message.text }}
            </v-card-title>
            <v-spacer></v-spacer>
            <v-card-actions>
                <span>
                    <v-btn @click="edit" text rounded>Edit</v-btn>
                    <v-btn icon @click="del">
                        <v-icon>delete</v-icon>
                    </v-btn>
                </span>
            </v-card-actions>
        </v-row>
    </v-card>
</template>

<script>
    import { mapActions } from 'vuex'
    export default {
        props: ['message', 'editMessage'],
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