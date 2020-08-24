<template>
    <span></span>
</template>

<script>
    import { mapActions } from 'vuex'
    export default {
        name: 'Loader',
        methods: mapActions(['loadPageAction']),
        data() {
            return {
                scrolling: false
            }
        },
        mounted() {
            window.onscroll = () => {
                const el = document.documentElement
                const isBottomOfScreen = el.scrollTop + window.innerHeight > el.offsetHeight - (0.1 * window.innerHeight)
                if (isBottomOfScreen) {
                    this.loadPageAction()
                }
            }
        },
        beforeDestroy() {
            window.onscroll = null
        }
    }
</script>

<style scoped>
</style>