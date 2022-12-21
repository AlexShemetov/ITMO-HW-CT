<#-- @ftlvariable name="user_id" type="java.lang.Integer" -->
<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#import "./common.ftlh" as c>

<@c.page>
    <div class="user-info">
        <#assign  userIndex = -1/>
        <#if user_id??>
            <#assign userIndex = c.findBy(users, "id", user_id)!/>
        </#if>
        <#if handle??>
            <#assign userIndex = c.findBy(users, "handle", handle)!/>
        </#if>

        <#if userIndex != -1>
            <#assign u = users[userIndex]!/>
        </#if>

        <#if u?has_content>
            <div class="user-name">
                <#if logged_user_id?? && logged_user_id == u.id>
                    <u>${u.name}</u>
                <#else>
                    <div style="color: ${u.color}">${u.name}</div>
                </#if>
            </div>

            <img src="/img/no-title.jpg" alt="user_img">

            <ul>
                <#assign info = {
                'id': u.id,
                'handle': u.handle,
                'name': u.name
                }>
                <#list info as infoType, infoValue>
                    <li>${infoType}: ${infoValue}</li>
                </#list>
                <li><a href="/posts?user_id=${u.id}">total posts: ${u.totalPosts}</a></li>
            </ul>

            <div class="pagination-arrows">
                <#if users[userIndex - 1]??>
                    <@c.userlink user=users[userIndex - 1] message='←' nameOnly=true/>
                <#else>
                    ←
                </#if>

                <#if users[userIndex + 1]??>
                    <@c.userlink user=users[userIndex + 1] message='→' nameOnly=true/>
                <#else>
                    →
                </#if>
            </div>
        <#else>
            No such user
        </#if>
    </div>
</@c.page>