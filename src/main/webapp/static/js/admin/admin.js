$(document).ready(function () {
    //管理员登陆
    $("#admin-submit-button").click(function () {
        var name = $("#admin-number-input").val();
        var password = $("#admin-password-input").val();
        var validate = true;
        if (name == "" || name == null) {
            $("#admin-number-input").addClass("has-error");
            validate = false;
        } else {
            $("#admin-number-input").removeClass("has-error");
        }
        if (password == "" || password == null) {
            $("#admin-password-input").addClass("has-error");
            validate = false;
        } else {
            $("#admin-password-input").removeClass("has-error");
        }
        if (validate) {
            AdminManager.login(name, password, function (success) {
                if (success) {
                    location.href = "list.html";
                } else {
                    $("#admin-number-input").addClass("has-error");
                    $("#admin-password-input").addClass("has-error");
                }
            });
        }
    });

    $("body").keydown(function () {
        if (event.keyCode == 13)
            $("#admin-submit-button").click();
    });
});