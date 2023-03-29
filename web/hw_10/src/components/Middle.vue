<template>
    <div class="middle">
        <Sidebar :posts="viewPosts"/>
        <main>
            <Index v-if="page === 'Index'" :users="users" :posts="posts" :comments="comments"/>
            <Enter v-if="page === 'Enter'"/>
            <WritePost v-if="page === 'WritePost'"/>
            <EditPost v-if="page === 'EditPost'"/>
            <Register v-if="page === 'Register'"/>
            <Users v-if="page === 'Users'" :users="users"/>
            <ViewPost v-if="page === 'ViewPost'" :post="post" :users="users" :comments="comments"/>
        </main>
    </div>
</template>

<script>
import Sidebar from "./sidebar/Sidebar";
import Index from "./page/Index.vue";
import Enter from "./page/Enter";
import WritePost from "./page/WritePost";
import EditPost from "./page/EditPost";
import Register from "@/components/page/Register.vue";
import Users from "@/components/page/Users.vue";
import ViewPost from "@/components/page/ViewPost.vue";

export default {
    name: "Middle",
    data: function () {
        return {
            page: "Index",
            post: null
        }
    },
    components: {
        ViewPost,
        WritePost,
        Enter,
        Index,
        Sidebar,
        EditPost,
        Register,
        Users
    },
    props: ["posts", "users", "comments", "postId"],
    computed: {
        viewPosts: function () {
            return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
        }
    }, beforeCreate() {
        this.$root.$on("onChangePage", (page) => this.page = page)
        this.$root.$on("onViewPost", (post) => {
            this.post = post;
            this.page = "ViewPost";
        });
    }
}
</script>

<style scoped>

</style>
