// 获得良好的格式化时间
function getTime(time) {
    let longTime = new Date(time);

    //  2019-11-27 11:15:19.532
    let year = getTwoTime(longTime.getFullYear());
    let month = getTwoTime(longTime.getMonth() + 1);
    let date = getTwoTime(longTime.getDate());
    let hours = getTwoTime(longTime.getHours());
    let minutes = getTwoTime(longTime.getMinutes());
    let seconds = getTwoTime(longTime.getSeconds());

    return year + "-" + month + "-" + date + " " + hours + ":" + minutes + ":" + seconds;
}

// 将一位数转为两位
function getTwoTime(i) {
    return Math.floor(i / 10) <= 0 ? "0" + i : i;
}