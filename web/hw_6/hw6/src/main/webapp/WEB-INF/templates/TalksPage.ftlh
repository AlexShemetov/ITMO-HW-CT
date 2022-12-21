<#-- @ftlvariable name="talks" type="ru.itmo.wp.model.domain.Message[]" -->
<#-- @ftlvariable name="user" type="ru.itmo.wp.model.domain.User" -->
<#-- @ftlvariable name="targetUser" type="java.lang.Number" -->
<#-- @ftlvariable name="selected" type="java.lang.Boolean" -->
<#-- @ftlvariable name="message" type="java.lang.String" -->
<#-- @ftlvariable name="talk" type="java.lang.String" -->
<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="talks-field">
        <div class="writer">
            <form method="post" action="">
                <input type="hidden" name="action" value="send"/>
                <div class="field">
                    <textarea id="text" name="text" placeholder="Input your message">${message!}</textarea>
                    <select name="targetUser" value="${targetUser!}">
                        <option disabled>Choose addressee</option>
                        <#list users as u>
                            <#if u.id != user.id>
                                <option id="select_${u.login}" name="targetUser" value="${u.id}">
                                    ${u.login}
                                </option>
                            </#if>
                        </#list>
                    </select>
                </div>
                <div class="button-field">
                    <input type="submit" value="Send">
                </div>
            </form>
        </div>
        <div class="talks">
            <div class="datatable">
                <div class="caption">Talk</div>
                <table>
                    <thead>
                    <tr>
                        <th>Source</th>
                        <th>Target</th>
                        <th>Text</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list talks as talk>
                        <tr>
                            <td>${findBy(users, "id", talk.sourceUserId).login}</td>
                            <td>${findBy(users, "id", talk.targetUserId).login}</td>
                            <td>${talk.text}</td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</@c.page>

<#function findBy items key value>
    <#list items as item>
        <#if item[key] == value>
            <#return item/>
        </#if>
    </#list>
</#function>