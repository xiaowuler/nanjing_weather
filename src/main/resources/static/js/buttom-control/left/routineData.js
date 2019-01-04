var RoutineData = function () {

    this.typeCodes = [];

    this.init = function (data) {
        $(data).each(function (index,dataState) {
            var typeCode = new TypeCode(dataState.productTypeCode);
            typeCode.init(dataState.dataArrivalLitter);
            this.typeCodes.push(typeCode);
        }.bind(this))
    }
    
    this.writeHtml = function () {
        var size = 100;
        $(this.typeCodes).each(function (index,typeCode) {
            if(size > typeCode.getSize()){
                size = typeCode.getSize();
            }
        }.bind(this))

        var textHtml = '<div class="slide-time-txt"><span>常规数据采集</span></div>'

        for(var i=0;i<this.typeCodes.length;i++){
            if(i%2 == 0)
                textHtml += '<div class="slide-txt"><span>{0}</span></div>'.format(this.typeCodes[i].name);
            else
                textHtml += '<div class="slide-txt slide-txt-blue"><span>{0}</span></div>'.format(this.typeCodes[i].name);
        }

        $("#routine-little").html(textHtml);

        textHtml = '';
        for(var j=0;j<size;j++){

            for(var i=0;i<this.typeCodes.length;i++){
                if(i==0)
                    textHtml += this.typeCodes[i].writeHtml(j,'start');
                else if(i == this.typeCodes.length-1)
                    textHtml += this.typeCodes[i].writeHtml(j,'end');
                else
                    textHtml += this.typeCodes[i].writeHtml(j,'center');
            }

        }

        $("#routine-data").html(textHtml);

        this.Slide();
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