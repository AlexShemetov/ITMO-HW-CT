<template>
    <article>
        <div class="title"><a href="#" @click.prevent="viewPost()">{{ post.title }}</a></div>
        <div class="information">By {{ post.user.login }}</div>
        <div class="body">{{ post.text }}</div>
        <div class="footer">
            <div class="left">
                <img src="../assets/img/voteup.png" title="Vote Up" alt="Vote Up"/>
                <span class="positive-score">+173</span>
                <img src="../assets/img/votedown.png" title="Vote Down" alt="Vote Down"/>
            </div>
            <div class="right">
                <img src="../assets/img/comments_16x16.png" title="Comments" alt="Comments"/>
                <!-- <a href="#"> {{ totalComments() }} </a> -->
                <a href="#">0</a>
            </div>
        </div>
    </article>
</template>

<script>
export default {
    name: "Post",
    props: ["post", "comments"],
    methods: {
        totalComments: function () {
            return Object.values(this.comments).filter(c => c.postId === this.post.id).length
        },
        viewPost: function () {
            this.$root.$emit("onViewPost", this.post);
        }
    }
}
</script>

<style scoped>

</style>