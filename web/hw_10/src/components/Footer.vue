<template>
    <footer>
        <a href="/">Codeforces</a> &copy; 2022 by Mike Mirzayanov
        | users: {{ totalUsers }} | posts: {{ totalPosts }}
    </footer>
</template>

<script>
export default {
    name: "Footer",
    props: ["totalPosts", "totalUsers"]
}
</script>

<style scoped>

</style>