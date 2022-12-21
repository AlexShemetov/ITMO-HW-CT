<#import "./common.ftlh" as c>
<@c.page>
    <#list posts as post>
        <@c.post post=post users=users>
            ${post.shortText}
        </@c.post>
    </#list>
</@c.page>
