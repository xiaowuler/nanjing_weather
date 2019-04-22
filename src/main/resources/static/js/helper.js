String.format = function (format) {
    var args = Array.prototype.slice.call(arguments, 1);
    return format.replace(/{(\d+)}/g, function (match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};

String.prototype.format = function () {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function (match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};

String.prototype.myReplace=function(f,e){//吧f替换成e
    var reg=new RegExp(f,"\g"); //创建正则RegExp对象
    return this.replace(reg,e);
}

String.prototype.PadLeft = function (len, charStr) {
    var s = this + '';
    return new Array(len - s.length + 1).join(charStr,  '') + s;
}
String.prototype.PadRight = function (len, charStr) {
    var s = this + '';
    return s + new Array(len - s.length + 1).join(charStr,  '');
}

Date.prototype.addHours = function (h) {
    this.setTime(this.getTime() + (h * 60 * 60 * 1000));
    return this;
}

Date.prototype.addDays = function(days) {
    var dat = new Date(this.valueOf())
    dat.setDate(dat.getDate() + days);
    return dat;
}
