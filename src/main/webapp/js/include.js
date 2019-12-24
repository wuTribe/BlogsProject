// 导航栏
$(function () {
    $.get("nav.html", null, function (data) {
        $("#nav").html(data);
    });
});

// 时钟效果
$(function () {
    var timeout = setInterval(function () {
        var time = new Date();
        $("#clock").html(getTime(time));
    }, 1000);
});