<#import "common.ftlh" as c>

<@c.page>
    <#assign postIndex = c.findBy(posts, "id", post_id)/>
    <#if postIndex != -1>
        <#assign post = posts[postIndex]>
    </#if>
    <#if post?has_content>
        <@c.post post=post users=users>
            ${post.text}
        </@c.post>
    <#else>
        <h1>Post not found</h1>
    </#if>
</@c.page>