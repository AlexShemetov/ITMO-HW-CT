<#-- @ftlvariable name="userProfile" type="ru.itmo.wp.domain.User" -->
<#import "commons.ftlh" as c>

<@c.page>
    <#if userProfile??>
        <div class="user-profile">
            <div class="user-id">
                Id: ${userProfile.id!}
            </div>
            <div class="user-login">
                Login: ${userProfile.login!}
            </div>
            <div class="user-creationTime">
                Since: ${userProfile.creationTime!}
            </div>
        </div>
    <#else>
        <div class="error">${error}</div>
    </#if>
</@c.page>