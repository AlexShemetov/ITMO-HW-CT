<template>
    <div class="posts">
        <Post v-for="post in posts" :key="post.id" :post="post" :comments="comments"/>
    </div>
</template>

<script>
import Post from "@/components/Post.vue";

export default {
    name: "Index",
    props: ["posts", "comments"],
    components: {Post},
    computed: {
        reversedPosts: function () {
            return Object.values(this.posts).reverse()
        }
    }
}
</script>

<style scoped>

</style>
