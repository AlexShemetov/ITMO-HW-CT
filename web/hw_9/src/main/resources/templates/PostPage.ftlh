<#import "commons.ftlh" as c>
<#import "/spring.ftl" as spring>

<@c.page>
    <div class="post">
        <#if post??>
            <@c.post post=post/>
            <#if user??>
                <div class="form">
                    <div class="header">Write Comment</div>
                    <div class="body">
                        <form method="post" action="">
                            <input type="hidden" name="action" value="writeComment"/>
                            <div class="field">
                                <div class="name">
                                    <label for="text">Text</label>
                                </div>
                                <div class="value">
                                    <textarea id="text" name="text">${comment.text!}</textarea>
                                </div>
                                <@c.error "comment.text"/>
                            </div>
                            <div class="button-field">
                                <input type="submit" value="Write">
                            </div>
                        </form>
                    </div>
                </div>
            </#if>
            <#list post.comments as comment>
                <@c.comment comment=comment/>
            </#list>
        <#else>
            <div class="error">${postError!}</div>
        </#if>
    </div>
</@c.page>