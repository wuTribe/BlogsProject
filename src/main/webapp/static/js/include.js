// 导航栏
$(function () {
    $.get("nav.html", null, function (data) {
        $("#nav").html(data);
    });
});

// 时钟效果
$(function () {
    let timeout = setInterval(function () {
        let time = new Date();
        $("#clock").html(getTime(time));
    }, 1000);
});

// 获取网站信息
$(function () {
    $.get("webInfo.html", null, function (data) {
        $('#webInfo').html(data);
    });
});