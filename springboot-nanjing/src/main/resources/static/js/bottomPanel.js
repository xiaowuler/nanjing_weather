var BottomPanel = function () {

    this.requestPath = '';

    this.Startup = function () {
        this.selectState();
    }

    this.selectState = function () {
        //定义文本变量，存入值
        var routineData ='';
        var notRoutineData = '';
        setTimeout(this.selectState,1000*60*5);
        $.post(this.requestPath+"dataArrivals/findState", function (data) {
            var routineLitter ='<div class="slide-time-txt">时间</div><div class="slide-txt slide-line">常规数据采集名称</div>';
            routineLitter += this.getRoutineLitter(data[0],null);
            routineData += this.getRoutineData(data[0],null);
            var notRoutineLitter ='<div class="slide-time-txt">时间</div><div class="slide-txt slide-line">非常规数据采集名称</div>';
            notRoutineLitter += this.getRoutineLitter(data[1],data[2]);
            notRoutineData +=this.getRoutineData(data[1],data[2]);
            $("#routine-little").html(routineLitter)
            $("#routine-data").html(routineData);
            $("#notRoutine-little").html(notRoutineLitter)
            $("#notRoutine-data").html(notRoutineData);
            this.Slide();
        }.bind(this))
    }.bind(this)

    this.getRoutineLitter = function (data,data1) {
        var routineLitter = '';
        for(var i=0;i<data.length;i++){
            if(i%2==0){
                routineLitter += '<div class="slide-txt slide-txt-blue">'+data[i].productTypeCode+'</div>'
            }else{
                routineLitter += '<div class="slide-txt">'+data[i].productTypeCode+'</div>'
            }
        }
        if(data1 != null){
            routineLitter += '<div class="slide-time-txt timeHeight">时间</div>'
            routineLitter += '<div class="slide-txt slide-txt-blue dataHeight">'+data1[0].productTypeCode+'</div>'
        }
        return routineLitter;
    }

    this.getRoutineData = function (data,data1) {
        var routineData = '';
        var num = 100;
        for(var i=0;i<data.length;i++){
            if(num > data[i].dataArrivalLitter.length){
                num = data[i].dataArrivalLitter.length;
            }
        }
        for(var j=0;j<num;j++){
            routineData += '<div class="swiper-slide">' +
                '<div class="slide-time">'+data[0].dataArrivalLitter[j].time+'</div>'+
                '<div class="slide-state">完成状态</div>'
            for(var i=0;i<data.length;i++){
                if(data[i].dataArrivalLitter.length<8){
                    routineData +='<div class="slide-des"><span class="color red"></span></div>';
                }else {
                    routineData +='<div class="slide-des"><span class="color '+
                        this.getState(data[i].dataArrivalLitter[j].centerDataArrival[data[i].dataArrivalLitter[j].centerDataArrival.length-1].timeliessCode)+'"></span></div>';
                }
            }
            if(data1 != null){
                routineData += '<div class="swiper-slide timeHeight">' +
                    '<div class="slide-time">'+data1[0].dataArrivalLitter[data1[0].dataArrivalLitter.length-(j+2)].time+'</div></div>'
                routineData += '<div class="slide-des dataHeight"><span class="color '+
                    this.getState(data1[0].dataArrivalLitter[data1[0].dataArrivalLitter.length-(j+2)].centerDataArrival[data1[0].dataArrivalLitter[data1[0].dataArrivalLitter.length-(j+2)].centerDataArrival.length-1].timeliessCode)+'"></span></div>'
            }
            routineData +='</div>'
        }
        return routineData;
    }

    this.getState = function (timeCodeState) {
        if(timeCodeState=="parts"){
            return "orange";
        }else if(timeCodeState=="all"){
            return "green";
        }else if(timeCodeState=="missing"){
            return "red";
        }else if(timeCodeState=="error"){
            return "red";
        }else if(timeCodeState=="lost"){
            return "gray";
        }
    }

    this.Slide = function () {
        var swiper = new Swiper('.swiper-left', {
            slidesPerView: 6,
            loop: true,
            nextButton: '.swiper-next-left',
            prevButton: '.swiper-prev-left'
        });
        var swiper = new Swiper('.swiper-right', {
            slidesPerView: 6,
            loop: true,
            nextButton: '.swiper-next-right',
            prevButton: '.swiper-prev-right'
        });
    };
}