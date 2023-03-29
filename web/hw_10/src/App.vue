<template>
    <div id="app">
        <Header :userId="userId" :users="users"/>
        <Middle :posts="posts" :users="users" :comments="comments"/>
        <Footer :totalPosts="totalPosts" :totalUsers="totalUsers"/>
    </div>
</template>

<script>
import Header from "./components/Header";
import Middle from "./components/Middle";
import Footer from "./components/Footer";

export default {
    name: 'App',
    components: {
        Footer,
        Middle,
        Header
    },
    data: function () {
        return this.$root.$data;
    },
    beforeCreate() {
        this.totalPosts = Object.values(this.$root.posts).length
        this.totalUsers = Object.values(this.$root.users).length

        this.$root.$on("onEnter", (login, password) => {
            console.log(this.users)
            if (password === "") {
                this.$root.$emit("onEnterValidationError", "Password is required");
                return;
            }

            const users = Object.values(this.users).filter(u => u.login === login);
            if (users.length === 0) {
                this.$root.$emit("onEnterValidationError", "No such user");
            } else {
                this.userId = users[0].id;
                this.$root.$emit("onChangePage", "Index");
            }
        });

        this.$root.$on("onLogout", () => this.userId = null);

        this.$root.$on("onWritePost", (title, text) => {
            if (this.userId) {
                if (!title || title.length < 5) {
                    this.$root.$emit("onWritePostValidationError", "Title is too short");
                } else if (!/[a-zA-Z]+/.test(title)) {
                    this.$root.$emit("onWritePostValidationError", "Title must has one letter or more");
                } else if (!text || text.length < 10) {
                    this.$root.$emit("onWritePostValidationError", "Text is too short");
                } else if (!/[a-zA-Z]+/.test(text)) {
                    this.$root.$emit("onWritePostValidationError", "Text must has one letter or more");
                } else {
                    const id = Math.max(...Object.keys(this.posts)) + 1;
                    this.$root.$set(this.posts, id, {
                        id, title, text, userId: this.userId
                    });
                }
            } else {
                this.$root.$emit("onWritePostValidationError", "No access");
            }
        });

        this.$root.$on("onRegister", (name, login) => {
            if (!name || name.length > 32) {
                this.$root.$emit("onRegisterValidationError", "Invalid name")
            } else if (name.split(' ').filter(c => c.length > 0).length === 0) {
                this.$root.$emit("onRegisterValidationError", "Name must has one letter or more")
            } else if (!login || login.length < 3 || login.length > 16 || !/^[a-z]+$/.test(login)) {
                this.$root.$emit("onRegisterValidationError", "Invalid login")
            } else if (Object.values(this.users).filter(u => u.login === login).length !== 0) {
                this.$root.$emit("onRegisterValidationError", "Login already exist")
            } else {
                const id = Math.max(...Object.keys(this.users)) + 1
                this.$root.$set(this.users, id, {
                    id, login, name, admin: false
                })
                console.log(this.users)
                this.$root.$emit("onChangePage", "Index");
            }
        })

        this.$root.$on("onEditPost", (id, text) => {
            if (this.userId) {
                if (!id) {
                    this.$root.$emit("onEditPostValidationError", "ID is invalid");
                } else if (!text || text.length < 10) {
                    this.$root.$emit("onEditPostValidationError", "Text is too short");
                } else if (!/[a-zA-Z]+/.test(text)) {
                    this.$root.$emit("onEditPostValidationError", "Text must has one letter or more");
                } else {
                    let posts = Object.values(this.posts).filter(p => p.id === parseInt(id));
                    if (posts.length) {
                        posts.forEach((item) => {
                            item.text = text;
                        });
                    } else {
                        this.$root.$emit("onEditPostValidationError", "No such post");
                    }
                }
            } else {
                this.$root.$emit("onEditPostValidationError", "No access");
            }
        });

        this.$root.$on("onWriteComment", (text, post) => {
            if (this.userId) {
                if (!text || text.length < 4) {
                    this.$root.$emit("onWriteCommentValidationError", "Text is too short");
                } else if (!/[a-zA-Z]+/.test(text)) {
                    this.$root.$emit("onWriteCommentValidationError", "Text must has one letter or more");
                } else {
                    const id = Math.max(...Object.keys(this.comments)) + 1;
                    this.$root.$set(this.comments, id, {
                        id, userId: this.userId, postId: post.id, text
                    });
                }
            } else {
                this.$root.$emit("onWriteCommentValidationError", "No access");
            }
        })
    }
}
</script>

<style>
#app {

}
</style>
