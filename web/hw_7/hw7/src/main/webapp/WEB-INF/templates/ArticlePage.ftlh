<#import "commons.ftlh" as c>

<@c.page>
    <div class="creat-article">
        <form method="POST" action="">
            <input type="hidden" name="action" value="create">
            <div class="title">
                <div class="name">
                    <label for="title">Title</label>
                </div>
                <div class="value">
                    <input id="title" name="title">
                </div>
            </div>
            <div class="text">
                <div class="name">
                    <label for="text">Text</label>
                </div>
                <div class="value">
                <textarea id="text" name="text"><#--
             --></textarea>
                </div>
            </div>
            <div class="error" style="color: red"></div>
            <div class="button-field">
                <input type="submit" value="Create">
            </div>
        </form>
    </div>

    <script>
        $(function () {
            $(".creat-article").submit(function () {
                const title = $(this).find("input[name='title']").val()
                const text = $(this).find("textarea[name='text']").val()
                const error = $(this).find(".error")
                ajaxDefault({
                        action: "create",
                        title, text
                    },
                    function (response) {
                        if (response["error"]) {
                            error.text(response["error"]);
                        } else {
                            location.href = response["redirect"];
                        }
                    })

                return false
            })
        })
    </script>
</@c.page>