<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#import "common.ftlh" as c>

<@c.page>
    <div class="user-posts">
        <#list posts as post>
            <#if post.user_id == user_id>
                <#assign havePost = true>
                <@c.post post=post users=users>
                    ${post.text}
                </@c.post>
            </#if>
        </#list>

        <#if !havePost??>
              <h1>This user hasn't any post</h1>
        </#if>
    </div>
</@c.page>