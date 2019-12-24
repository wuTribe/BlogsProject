// 获得良好的格式化时间
function getTime(time) {
    var longTime = new Date(time);

    //  2019-11-27 11:15:19.532
    var year = getTwoTime(longTime.getFullYear());
    var month = getTwoTime(longTime.getMonth() + 1);
    var date = getTwoTime(longTime.getDate());
    var hours = getTwoTime(longTime.getHours());
    var minutes = getTwoTime(longTime.getMinutes());
    var seconds = getTwoTime(longTime.getSeconds());

    return year + "-" + month + "-" + date + " " + hours + ":" + minutes + ":" + seconds;
}

// 将一位数转为两位
function getTwoTime(i) {
    return Math.floor(i / 10) <= 0 ? "0" + i : i;
}