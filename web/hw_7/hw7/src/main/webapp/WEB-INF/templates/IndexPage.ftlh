<#import "commons.ftlh" as c>

<@c.page>
    <div class="articles">
        <ul class="articles-list">
            <template>
                <li class="user-article">
                    <article>
                        <div class="title"></div>
                        <div class="text"></div>
                        <ul class="attachment">
                            <li>Announcement of <a href="#">Codeforces Round #510 (Div. 1)</a></li>
                            <li>Announcement of <a href="#">Codeforces Round #510 (Div. 2)</a></li>
                        </ul>
                        <div class="footer">
                            <div class="left">
                                <img src="/img/voteup.png" title="Vote Up" alt="Vote Up"/>
                                <span class="positive-score">+173</span>
                                <img src="/img/votedown.png" title="Vote Down" alt="Vote Down"/>
                            </div>
                            <div class="right">
                                <img src="/img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                                2 days ago
                                <img src="/img/comments_16x16.png" title="Comments" alt="Comments"/>
                                <a href="#">68</a>
                            </div>
                        </div>
                    </article>
                </li>
            </template>
        </ul>
        <ul class="noData">
            <li>
                No data
            </li>
        </ul>
    </div>

    <script>
        $(function () {
            const noData = $(".articles .noData")
            noData.find("li").text("Loading...")

            ajaxDefault({
                    action: "findAll"
                },
                function (response) {
                    response["articles"].forEach(function (article) {
                        noData.hide()
                        const li = $($(".articles template").prop("content")).clone()
                        li.find(".title").text(article["title"])
                        li.find(".text").text(article["text"])
                        li.find("li").attr("data-articleId", article["id"])
                        $(".articles .articles-list").append(li)
                    })
                })
        })
    </script>
</@c.page>
