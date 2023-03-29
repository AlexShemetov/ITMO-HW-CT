<template>
    <div>
        <Post :post="post" :users="users" :comments="comments"/>
        <div v-if="this.$root.userId" class="form">
            <div class="header">Write Comment</div>
            <div class="body">
                <form @submit.prevent="onWriteComment">
                    <div class="field">
                        <div class="name">
                            <label for="text">Text:</label>
                        </div>
                        <div class="value">
                            <textarea id="text" name="text" v-model="text"></textarea>
                        </div>
                    </div>
                    <div class="error">{{ error }}</div>
                    <div class="button-field">
                        <input type="submit" value="Write">
                    </div>
                </form>
            </div>
        </div>
        <Comment v-for="comment in postComments" :key="comment.id" :comment="comment" :users="users" />
    </div>
</template>

<script>
import Post from "@/components/Post.vue";
import Comment from "@/components/Comment.vue";

export default {
    name: "ViewPost",
    components: {Comment, Post},
    props: ["post", "users", "comments"],
    data: function () {
        return {
            text: "",
            error: ""
        }
    },
    computed: {
        postComments: function () {
            return Object.values(this.comments).filter(c => this.post.id === c.postId)
        }
    },
    methods: {
        onWriteComment: function () {
            this.error = ""
            this.$root.$emit("onWriteComment", this.text, this.post)
        }
    },
    beforeMount() {
        this.$root.$on("onWriteCommentValidationError", error => this.error = error);
    }
}
</script>

<style scoped>

</style>