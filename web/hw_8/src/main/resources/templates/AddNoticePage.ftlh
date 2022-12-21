<#import "commons.ftlh" as c>

<@c.page>
    <div class="add form-box">
        <div class="body">
            <form method="post" action="">
                <div class="field">
                    <div class="text-name">
                        <label for="content">Content</label>
                    </div>
                    <div class="text-value">
                        <textarea id="content" name="content" rows="20">${notice.content!}</textarea>
                    </div>
                    <@c.error 'notice.content'/>
                </div>
                <div class="button-field">
                    <input type="submit" value="Add">
                </div>
            </form>
        </div>
    </div>
</@c.page>