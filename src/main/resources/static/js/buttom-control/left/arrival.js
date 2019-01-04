var Arrival = function (time) {
    this.time = time;
    this.lastDatas = [];
    
    this.init = function (centerDataArrival) {
        $(centerDataArrival).each(function (index,dataArrival) {
            var lastData = new LastData(dataArrival.timeliessCode,dataArrival.description);
            this.lastDatas.push(lastData)
        }.bind(this))
    }
    
    this.writeHtml = function (flag) {
        if(flag === 'start')
            return '<div class="swiper-slide"><div class="slide-time">{2}</div><div class="slide-des"><span class="color {0}">{1}</span></div>'.format(this.getState(this.lastDatas[this.lastDatas.length-1].statu),this.lastDatas[this.lastDatas.length-1].num,this.time);
        else if(flag === 'end'){
            return '<div class="slide-des"><span class="color {0}">{1}</span></div></div>'.format(this.getState(this.lastDatas[this.lastDatas.length-1].statu),this.lastDatas[this.lastDatas.length-1].num);
        }else
            return '<div class="slide-des"><span class="color {0}">{1}</span></div>'.format(this.getState(this.lastDatas[this.lastDatas.length-1].statu),this.lastDatas[this.lastDatas.length-1].num);
    }

    this.getState = function (timeCodeState) {
        if (timeCodeState == "parts") {
            return "orange";
        } else if (timeCodeState == "all") {
            return "green";
        } else if (timeCodeState == "missing") {
            return "red";
        } else if (timeCodeState == "error") {
            return "red";
        } else if (timeCodeState == "lost") {
            return "gray";
        }
    }
}