window.notify = function (message) {
    $.notify(message, {
        position: "right bottom",
        className: "success"
    });
}

window.ajaxDefault = function (data, success) {
    $.ajax({
        type: "POST",
        url: "",
        dataType: "json",
        data,
        success
    })
}

window.ajaxEnterOrLogin = function (elem, action) {
    const login = elem.find("input[name='login']").val();
    const password = elem.find("input[name='password']").val();
    const error = elem.find(".error");
    ajaxDefault({
            action, login, password
        },
        function (response) {
            if (response["error"]) {
                error.text(response["error"]);
            } else {
                location.href = response["redirect"];
            }
        })
}

